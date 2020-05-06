package de.mhus.con.core;

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
import java.util.NoSuchElementException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.ConductorPlugin;
import de.mhus.con.api.DirectLoadScheme;
import de.mhus.con.api.ErrorInfo;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.ExecutionInterceptorPlugin;
import de.mhus.con.api.Executor;
import de.mhus.con.api.Labels;
import de.mhus.con.api.Lifecycle;
import de.mhus.con.api.Plugin;
import de.mhus.con.api.Plugin.SCOPE;
import de.mhus.con.api.Project;
import de.mhus.con.api.Project.STATUS;
import de.mhus.con.api.Scheme;
import de.mhus.con.api.Step;
import de.mhus.con.api.Steps;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.core.util.Value;
import de.mhus.lib.errors.MRuntimeException;
import de.mhus.lib.errors.NotFoundException;

public class ExecutorDefault extends MLog implements Executor {

    private Conductor con;
    private LinkedList<ErrorInfo> errors = new LinkedList<>();
    private Map<String,ConductorPlugin> mojos = new HashMap<>();
    private Map<String, Object[]> pluginClassLoaders = new HashMap<>();
    private LinkedList<ExecutionInterceptorPlugin> interceptors = new LinkedList<>();
    private Lifecycle currentLifecycle;
    private int currentStepCount;
    
    public ExecutorDefault() {
        interceptors.add(new ExecutionInterceptorDefault()); // TODO dynamic
    }
    
    @Override
    public void execute(Conductor con, String lifecycle) {
        this.con = con;
        ((ConductorImpl)con).properties.put(ConUtil.PROPERTY_LIFECYCLE, lifecycle);
        
        
        currentLifecycle = con.getLifecycles().get(lifecycle);
        try {
        	log().d("executeLifecycle",currentLifecycle);
	        Steps steps = currentLifecycle.getSteps();
	        execute(lifecycle, steps);
        } catch (Throwable t) {
        	log().d(currentLifecycle,t);
        	throw new MRuntimeException(currentLifecycle,t);
        } finally  {
            errors.clear();
        }
    }

    protected void execute(String lifecycle, Steps steps) {
        con.getProjects().forEach(p -> ((ProjectImpl)p).setStatus(Project.STATUS.SKIPPED) );
        interceptors.forEach(i -> i.executeBegin(con, lifecycle, steps));
        try {
            currentStepCount = 0;
            for (Step step : steps)
                execute(step);
        } finally {
            interceptors.forEach(i -> i.executeEnd(con, lifecycle, steps, errors));
        }
    }

    protected void execute(Step step) {
        currentStepCount++;
    	log().d("executeStep",step);
    	
    	try {
	        // load plugin
	        String target = step.getTarget();
	        Plugin plugin = con.getPlugins().get(target);
	
	    	// check for scope
	    	
	        if (plugin.getScope() == SCOPE.STEP) {
	        	// scope: step
	        	execute(step, (Project)null, plugin, null);
	        	return;
	        }
	    	
	        // scope: project
	    	
	        // select projects
	        Labels selector = step.getSelector();
	        LinkedList<Project> projects = null;
	        if (selector != null) {
	            projects = new LinkedList<>(con.getProjects().select(selector));
	        } else {
	            projects = new LinkedList<>(con.getProjects().getAll());
	        }
	        
	        // order
	        String order = step.getSortBy();
	        if (MString.isSet(order)) {
	            ConUtil.orderProjects(projects, order, step.isOrderAsc());
	        }
	
	        execute(step, projects, plugin);
    	} catch (Throwable t) {
    		throw new MRuntimeException(step,t);
    	}
    }

    protected void execute(Step step, LinkedList<Project> projects, Plugin plugin) {
    	if (projects == null || projects.size() == 0)
    		log().w("no projects selected",step);
    	
    	if (con.getProperties().getBoolean(ConUtil.PROPERTY_PARALLEL, false) && step.getProperties().getInt(ConUtil.PROPERTY_THREADS, 0) > 0) {
    		LinkedList<Project> queue = new LinkedList<>(projects);
    		Thread[] threads = new Thread[step.getProperties().getInt(ConUtil.PROPERTY_THREADS, 0)];
    		log().d("Parallel",threads.length);
    		for (int i = 0; i < threads.length; i++) {
    			threads[i] = new Thread(new Runnable() {
					
					@Override
					public void run() {
						log().d(Thread.currentThread().getId(),"Started");
						while (queue.size() > 0) {
							Project task = null;
							synchronized (queue) {
								try {
									task = queue.getFirst();
								} catch (NoSuchElementException e) {
									break;
								}
							}
							if (task == null) break; // paranoia
							log().d(Thread.currentThread().getId(),"Task",task);
							execute(step, task, plugin, projects);
						}
						log().d(Thread.currentThread().getId(),"Finished");
					}
				});
    			threads[i].start();    			
    		}
    		
    		boolean done = false;
    		int cnt = 0;
    		while (!done) {
    			done = true;
    			for (Thread thread : threads) {
    				if (thread.isAlive()) {
    					done = false;
    				}
    				if (!done)
						try {
							Thread.sleep(1000);
							cnt++;
							if (cnt % 60 == 0)
								synchronized (ConUtil.consoleLock) {
									System.out.println("Wait for tasks to finish");
								}
						} catch (InterruptedException e) {
							new MRuntimeException(e);
						}
    			}
    		}
    		
    	} else {
	        for (Project project : projects)
	            execute(step, project, plugin, projects);
    	}
    }

    protected void execute(Step step, Project project, Plugin plugin, LinkedList<Project> projectList) {
        log().d(">>>",step.getTitle(),project == null ? "-none-" : project.getName());
    	log().t("execute",step,project,plugin);
        try {
	        ContextImpl context = new ContextImpl(con);
	                
	        context.init(this, projectList, project, plugin, step);
	        
	        if (!step.matchCondition(context)) {
	        	log().d("condition not successful",step);
	        	return;
	        }
	        interceptors.forEach(i -> i.executeBegin(context));
	        
	        boolean done = false;
	        ConductorPlugin impl = loadMojo(context);
	        try {
	        	done = ((ExecutePlugin)impl).execute(context);
	        } catch (Throwable t) {
	            if (project != null) ((ProjectImpl)project).setStatus(Project.STATUS.FAILURE);
	            interceptors.forEach(i -> i.executeError(context, t));
	        	errors.add(new ErrorsInfoImpl(context, t));
	        	if (con.getProperties().getBoolean(ConUtil.PROPERTY_FAE, false)) {
	        		log().e(context,t);
	        		return;
	        	} else
	        		throw t;
	        }
	        if (project != null && project.getStatus() != STATUS.FAILURE && done) ((ProjectImpl)project).setStatus(Project.STATUS.SUCCESS);
	        
	        final boolean d = done;
            interceptors.forEach(i -> i.executeEnd(context, d));
        } catch (Throwable t) {
        	throw new MRuntimeException(project,t);
        }
    }

    public ConductorPlugin loadMojo(ContextImpl context) {
		ConductorPlugin impl = mojos.get(context.getPlugin().getTarget());
		if (impl != null) return impl;
        try {
        	impl = createMojo(con, context.getPlugin());
        } catch (Throwable t) {
        	throw new MRuntimeException(context.getPlugin(),t);
        }
        mojos.put(context.getPlugin().getTarget(), impl);
        return impl;
	}

	public ConductorPlugin createMojo(Conductor con, Plugin plugin) throws IOException, NotFoundException {
		log().d("createMojo",plugin.getUri(),plugin.getMojo());
		String mojoName = plugin.getMojo();
		
		Object[] entry = pluginClassLoaders.get(plugin.getUri());
		
		if (entry == null) {
			MUri uri = MUri.toUri(plugin.getUri());
			Scheme scheme = con.getSchemes().get(uri);
			
			if (scheme instanceof DirectLoadScheme) {
				return ((DirectLoadScheme)scheme).loadPlugin(uri, mojoName);
			}
			
			File pFile = scheme.load(con, uri);
			
			Value<JarEntry> metaInfEntry = new Value<>();
			ArrayList<String> classes=new ArrayList<>();
			LinkedList<URL> urls = new LinkedList<>();
			urls.add(pFile.toURI().toURL());
			log().d("Add main JAR",pFile);
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

		        			Scheme schemeDep = con.getSchemes().get(uriDep);
		        			File depFile = schemeDep.load(con, uriDep);
		        			urls.add(depFile.toURI().toURL());
		        			log().d("Add dependency JAR",depFile);
		        		}
		        	}
		        }
			}
						
			URLClassLoader cl = new URLClassLoader(urls.toArray(new URL[0]), con.getClassLoader());
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
				AMojo mojoDef = clazz.getAnnotation(AMojo.class);
				log().t("class",clazz,mojoDef);
				if (mojoDef != null) {
					if (mojoDef.name().equals(mojoName)) {
						ConductorPlugin inst = (ConductorPlugin)clazz.getConstructor().newInstance();
						return inst;
					}
				}
			} catch (ClassNotFoundException cnfe) {
			    log().t(cnfe);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ie) {
				log().w(className, ie);
			}
		}
		
		throw new NotFoundException("Plugin not found",plugin, plugin.getUri(), mojoName );
	}

    @Override
    public Lifecycle getLifecycle() {
        return currentLifecycle;
    }

    @Override
    public int getCurrentStepCount() {
        return currentStepCount;
    }

	public void setConductor(Conductor con) {
		this.con = con;
	}

}
