package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.ConductorPlugin;
import de.mhus.deploy.api.ErrorInfo;
import de.mhus.deploy.api.ExecutePlugin;
import de.mhus.deploy.api.Executor;
import de.mhus.deploy.api.Labels;
import de.mhus.deploy.api.Lifecycle;
import de.mhus.deploy.api.Mojo;
import de.mhus.deploy.api.Plugin;
import de.mhus.deploy.api.Plugin.SCOPE;
import de.mhus.deploy.api.Project;
import de.mhus.deploy.api.Scheme;
import de.mhus.deploy.api.Step;
import de.mhus.deploy.api.Steps;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.lang.Value;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.MRuntimeException;
import de.mhus.lib.errors.NotFoundException;

public class ExecutorDefault extends MLog implements Executor {

    private Conductor cur;
    private LinkedList<ErrorInfo> errors = new LinkedList<>();
    private Map<String,ConductorPlugin> mojos = new HashMap<>();
    private Map<String, Object[]> pluginClassLoaders = new HashMap<>();
    
    @Override
    public void execute(Conductor cur, String lifecycle) {
        this.cur = cur;
        
        Lifecycle lf = cur.getLifecycles().get(lifecycle);

        Steps steps = lf.getSteps();
        execute(steps);
        
    }

    protected void execute(Steps steps) {
        for (Step step : steps)
            execute(step);
    }

    protected void execute(Step step) {
        
        // load plugin
        String target = step.getTarget();
        Plugin plugin = cur.getPlugins().get(target);

    	// check for scope
    	
        if (plugin.getScope() == SCOPE.STEP) {
        	// scope: step
        	execute(step, (Project)null, plugin);
        	return;
        }
    	
        // scope: project
    	
        // select projects
        Labels selector = step.getSelector();
        LinkedList<Project> projects = null;
        if (selector != null) {
            projects = new LinkedList<>(cur.getProjects().select(selector));
        } else {
            projects = new LinkedList<>(cur.getProjects().getAll());
        }
        
        // order
        String order = step.getOrder();
        if (MString.isSet(order)) {
            CurUtil.orderProjects(projects, order, step.isOrderAsc());
        }

        execute(step, projects, plugin);
        
    }

    protected void execute(Step step, LinkedList<Project> projects, Plugin plugin) {
        for (Project project : projects)
            execute(step, project, plugin);
    }

    protected void execute(Step step, Project project, Plugin plugin) {
        
        ContextImpl context = new ContextImpl(cur, project == null ? null : project.getProperties() );
                
        context.init(project, plugin, step);
        
        if (!step.matchCondition(context)) {
        	log().d("condition not successful",step);
        	return;
        }
        
        ConductorPlugin impl = loadMojo(context);
        try {
        	((ExecutePlugin)impl).execute(context);
        } catch (Throwable t) {
        	errors.add(new ErrorsInfoImpl(context, t));
        	if (cur.getProperties().getBoolean(CurUtil.PROPERTY_FAE, false)) {
        		CurUtil.log.e(context,t);
        	} else
        		throw new MRuntimeException(t);
        }
    }

    public ConductorPlugin loadMojo(ContextImpl context) {
		ConductorPlugin impl = mojos.get(context.getPlugin().getTarget());
		if (impl != null) return impl;
        try {
        	impl = createMojo(cur, context.getPlugin());
        } catch (Throwable t) {
        	throw new MRuntimeException(t);
        }
        mojos.put(context.getPlugin().getTarget(), impl);
        return impl;
	}

	public ConductorPlugin createMojo(Conductor cur, Plugin plugin) throws IOException, NotFoundException {

		
		String mojoName = plugin.getMojo();
		
		Object[] entry = pluginClassLoaders.get(plugin.getUri());
		
		if (entry == null) {
			MUri uri = MUri.toUri(plugin.getUri());
			Scheme scheme = cur.getSchemes().get(uri);
			File pFile = scheme.load(cur, uri);
			
			Value<JarEntry> metaInfEntry = new Value<>();
			ArrayList<String> classes=new ArrayList<>();
			LinkedList<URL> urls = new LinkedList<>();
			urls.add(pFile.toURI().toURL());

			try (JarFile jar = new JarFile(pFile)) {
		        jar.stream().forEach(jarEntry -> {
		            if(jarEntry.getName().endsWith(".class"))
		            {
		                classes.add(jarEntry.getName().replaceAll("/",".").replace(".class",""));
		            } else if (jarEntry.getName().equals("META-INF/dependencies.txt")) {
		            	metaInfEntry.value = jarEntry;
		            }
		        });
		        
		        // load dependencies information out of META-INF/dependencies.txt
		        if (metaInfEntry.value != null) {
		        	InputStream is = jar.getInputStream(metaInfEntry.value);
		        	for (String line : MFile.readLines(is, true)) {
		        		line = line.trim();
		        		if (MString.isSet(line) && !line.startsWith("#")) {
		        			MUri uriDep = MUri.toUri(plugin.getUri());

		        			if (!uri.getScheme().equals("file") && uri.getScheme().equals(uriDep.getScheme()) )
		        				throw new MRuntimeException("scheme access denied",uri.getScheme(),uriDep.getScheme());

		        			Scheme schemeDep = cur.getSchemes().get(uriDep);
		        			File depFile = schemeDep.load(cur, uriDep);
		        			urls.add(depFile.toURI().toURL());
		        		}
		        	}
		        }
			}
						
			URLClassLoader cl = new URLClassLoader(urls.toArray(new URL[0]), cur.getClassLoader());
			entry = new Object[] {classes, cl };
			pluginClassLoaders.put(plugin.getUri(), entry);
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<String> classes = (ArrayList<String>) entry[0];
		URLClassLoader cl = (URLClassLoader) entry[1];
		
		// scan classes
		for (String className : classes) {
			try {
				Class<?> clazz = cl.loadClass(className);
				Mojo mojoDef = clazz.getAnnotation(Mojo.class);
				if (mojoDef != null) {
					if (mojoDef.name().equals(mojoName)) {
						ConductorPlugin inst = (ConductorPlugin)clazz.getConstructor().newInstance();
						return inst;
					}
				}
			} catch (ClassNotFoundException cnfe) {
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ie) {
				CurUtil.log.w(className, ie);
			}
		}
		
		throw new NotFoundException("Plugin not found",plugin);
	}

}
