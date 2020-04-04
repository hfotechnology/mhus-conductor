package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.ConfigType;
import de.mhus.cur.api.ConfigTypes;
import de.mhus.cur.api.Configurator;
import de.mhus.cur.api.Lifecycle;
import de.mhus.cur.api.Plugin;
import de.mhus.cur.api.Project;
import de.mhus.cur.api.Scheme;
import de.mhus.cur.api.Schemes;
import de.mhus.cur.api.Step;
import de.mhus.cur.api.Validator;
import de.mhus.cur.api.YList;
import de.mhus.cur.api.YMap;
import de.mhus.cur.api.Plugin.SCOPE;
import de.mhus.lib.core.IProperties;
import de.mhus.lib.core.MCast;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MValidator;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.MRuntimeException;
import de.mhus.lib.errors.NotFoundException;

public class ConfiguratorDefault extends MLog implements Configurator {

	protected Schemes schemes = new SchemesImpl();
	protected ConfigTypes types = new ConfigTypesImpl();
	protected Conductor cur;
	protected Set<String> loadedUris = new HashSet<>();
	protected List<Validator> validators = new LinkedList<>();
	
	@Override
	public void configure(URI uri, Conductor cur, IProperties properties) throws MException {
		this.cur = cur;
		overwrite(MUri.toUri(uri.toString()));
		initEntries();
		((ConductorImpl)cur).schemes = schemes;
		if (properties != null)
			((ConductorImpl)cur).properties.putReadProperties(properties);
		validate();
	}

	protected void initEntries() {
	    
	    ((ProjectsImpl)cur.getProjects()).init(cur);
        for (Project entry : cur.getProjects())
            ((ProjectImpl)entry).init(cur);
        
        ((PluginsImpl)cur.getPlugins()).init(cur);
        for (Plugin entry : cur.getPlugins())
            ((PluginImpl)entry).init(cur);
        
        
        for (Lifecycle lifecycle : cur.getLifecycles()) {
            ((LifecycleImpl)lifecycle).init(cur);
            int cnt = 0;
            for (Step entry : lifecycle.getSteps()) {
                ((StepImpl)entry).init(cur, lifecycle.getName() + ":" + cnt + ":" + entry.getTarget());
                cnt++;
            }
        }
            
    }

    protected void validate() throws MException {
        for (Validator validator : validators) {
            validator.validate(cur);
        }
    }

    protected void overwrite(MUri uri) throws MException {
		log().d("load uri",uri);
		loadedUris.add(uri.toString());
		// 1 load resource
		Scheme scheme = schemes.get(uri);
		String content = null;
		try {
		    File cf = scheme.load(cur, uri);
		    if (cf != null)
		        content = MFile.readFile(cf);
		} catch (IOException e) {
		    throw new MException(e);
		}
		if (content == null) throw new NotFoundException("configuration not found", uri);
		ConfigType type = types.get(uri);
		YMap docE = type.create(cur, content);
		if (docE.isEmpty()) {
		    log().w("Content is empty",uri);
		    return;
		}
		YList importE = docE.getList("import");
		
		// load imports FIRST and fill before processing
		loadImports(importE);

		YMap propertiesE = docE.getMap("properties");
		loadProperties(propertiesE);
		
		YList pluginsE = docE.getList("plugins");
	    loadPlugins(pluginsE);
	    
        YList projectsE = docE.getList("projects");
        loadProjects(projectsE);
	    
        YList lifecyclesE = docE.getList("lifecycles");
        loadLifecycles(lifecyclesE);
        
		
	}

    private void loadProperties(YMap propertiesE) {
        if (propertiesE == null) return;
        
        if (propertiesE.getBoolean("_clear"))
            ((MProperties)cur.getProperties()).clear();
        
        for (String key : propertiesE.getKeys()) {
            if (key.equals("_clear")) continue;
            ((MProperties)cur.getProperties()).put(key, propertiesE.getString(key));
        }
        
    }

    private void loadProjectProperties(YMap propertiesE, ProjectImpl project) {
        if (propertiesE == null) return;
        
        if (propertiesE.getBoolean("_clear"))
            ((MProperties)project.getProperties()).clear();
        
        for (String key : propertiesE.getKeys()) {
            if (key.equals("_clear")) continue;
            ((MProperties)project.getProperties()).put(key, propertiesE.getString(key));
        }
        
    }
    
    protected void loadLifecycles(YList lifecyclesE) {
        if (lifecyclesE == null) return;
        for (YMap map : lifecyclesE.toMapList()) {
            loadLifecycle(map);
        }
    }

    protected void loadLifecycle(YMap map) {
        String name = map.getString("name");
        boolean merge = map.getBoolean("_merge");
        YMap executeE = map.getMap("execute");
        YList stepsE = executeE.getList("steps");
        
        StepsImpl steps = new StepsImpl();
        if (merge) {
            LifecycleImpl parent = (LifecycleImpl) cur.getLifecycles().getOrNull(name);
            if (parent != null)
                steps = (StepsImpl) parent.getSteps();
        }

        loadSteps(stepsE, steps);
        
        ((LifecyclesImpl)cur.getLifecycles()).put(name, new LifecycleImpl(name, steps));
        
    }

    protected void loadSteps(YList executeE, StepsImpl steps) {
        if (executeE == null) return;
        for (YMap map : executeE.toMapList()) {
            
            Step step = loadStep(map);
            
            String mode = map.getString("_insert");
            if (mode == null || mode.equals("append"))
                steps.add(step);
            else
            if (mode.equals("first"))
                steps.list.addFirst(step);
            else
            if (MValidator.isInteger(mode))
                steps.list.add(MCast.toint(mode, 0), step);
        }
    }

    protected Step loadStep(YMap map) {
        StepImpl step = new StepImpl();
        
        // target:
        step.target = map.getString("target");
        // title
        step.title = map.getString("title", step.target);
        
        try {
            // parameters:
        	step.parameters = new LinkedList<>();
            if (map.isString("parameters")) {
            	step.parameters.add(map.getString("parameters"));
            } else
            if (map.isList("parameters")) {
                YList parametersE = map.getList("parameters");
                if (parametersE != null) {
                    parametersE.toStringList().forEach(v -> step.parameters.add(v));
                }
            }
            
            // selector:
            YMap selectorE = map.getMap("selector");
            step.selector = new LabelsImpl();
            if (selectorE != null) {
        	    for (String key : selectorE.getKeys())
        	    	step.selector.put(key, selectorE.getString(key));
            }
            
            // order:
            step.order = map.getString("order");
            if (step.order != null) {
            	step.order = step.order.trim();
                if (step.order.toLowerCase().endsWith(" asc")) {
                	step.order = step.order.substring(0, step.order.length()-4);
                	step.orderAsc  = true;
                } else if (step.order.toLowerCase().endsWith(" desc")) {
                	step.order = step.order.substring(0, step.order.length()-5);
                	step.orderAsc = false;
                }
            }
            
            step.condition = map.getString("condition");
            
        } catch (Throwable t) {
            throw new MRuntimeException("step",step.target,t);
        }
        
        return step;
    }

    protected void loadProjects(YList projectsE) {
        if (projectsE == null) return;
        for (YMap map : projectsE.toMapList()) {
            loadProject(map);
        }
    }

    protected void loadProject(YMap map) {
        ProjectImpl project = new ProjectImpl();
        
        Project merge = map.getBoolean("_merge") ? cur.getProjects().getOrNull(map.getString("target")) : null;
        project.name = map.getString("name");
        project.path = map.getString("path", merge == null ? null : merge.getPath());
        YMap l = map.getMap("labels");
        if (l == null && merge == null)
        	project.labels = new LabelsImpl();
        else if (l == null && merge != null)
        	project.labels = merge.getLabels();
        else {
        	project.labels = new LabelsImpl();
    	    for (String key : l.getKeys())
    	    	((LabelsImpl)project.labels).put(key, l.getString(key));
        }
		YMap propertiesE = map.getMap("properties");
        loadProjectProperties(propertiesE, project);
        
        ((ProjectsImpl)cur.getProjects()).put(project.getName(), project);
    }

    protected void loadPlugins(YList pluginsE) {
        if (pluginsE == null) return;
        for (YMap map : pluginsE.toMapList()) {
            loadPlugin(map);
        }
    }

    protected void loadPlugin(YMap map) {
    	
    	PluginImpl plugin = new PluginImpl();
    	Plugin merge = map.getBoolean("_merge") ? cur.getPlugins().getOrNull(map.getString("target")) : null;
    	
    	plugin.target = map.getString("target");
    	plugin.uri = map.getString("uri", merge == null ? null : merge.getUri());
    	plugin.mojo = map.getString("mojo", merge == null ? null : merge.getMojo());
    	
    	String scope = map.getString("scope");
    	if (scope != null)
    		plugin.scope = SCOPE.valueOf(scope.toUpperCase().trim());
    	
        ((PluginsImpl)cur.getPlugins()).put(plugin.getTarget(), plugin);
    }

    protected void loadImports(YList importE) throws MException {
	    if (importE == null) return;
		for (String uriStr : importE.toStringList()) {
		    MUri uri = MUri.toUri(uriStr);
			if (loadedUris.contains(uri.toString())) {
				log().d("Ignore, already loaded",uriStr);
			} else {
				overwrite(uri);
			}
		}
	}

    public Schemes getSchemes() {
        return schemes;
    }

    public ConfigTypes getTypes() {
        return types;
    }

    public Conductor getcur() {
        return cur;
    }

    public Set<String> getLoadedUris() {
        return loadedUris;
    }

    public List<Validator> getValidators() {
        return validators;
    }

}
