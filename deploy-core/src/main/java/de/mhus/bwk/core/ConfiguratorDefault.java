package de.mhus.bwk.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.mhus.lib.core.MCast;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MValidator;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.NotFoundException;

public class ConfiguratorDefault extends MLog implements Configurator {

	protected Schemes schemes = new SchemesImpl();
	protected ConfigTypes types = new ConfigTypesImpl();
	protected Bwk bwk;
	protected Set<String> loadedUris = new HashSet<>();
	protected List<Validator> validators = new LinkedList<>();
	
	@Override
	public void configure(URI uri, Bwk bwk) throws MException {
		this.bwk = bwk;
		overwrite(MUri.toUri(uri.toString()));
		initEntries();
		validate();
		((BwkImpl)bwk).schemes = schemes;
	}

	protected void initEntries() {
	    
	    ((ProjectsImpl)bwk.getProjects()).init(bwk);
        for (Project entry : bwk.getProjects())
            ((ProjectImpl)entry).init(bwk);
        
        ((PluginsImpl)bwk.getPlugins()).init(bwk);
        for (Plugin entry : bwk.getPlugins())
            ((PluginImpl)entry).init(bwk);
        
        
        for (Lifecycle lifecycle : bwk.getLifecycles()) {
            ((LifecycleImpl)lifecycle).init(bwk);
            for (Step entry : lifecycle.getSteps())
                ((StepImpl)entry).init(bwk);
        }
            
    }

    protected void validate() throws MException {
        for (Validator validator : validators) {
            validator.validate(bwk);
        }
    }

    protected void overwrite(MUri uri) throws MException {
		log().d("load uri",uri);
		loadedUris.add(uri.toString());
		// 1 load resource
		Scheme scheme = schemes.get(uri);
		String content = null;
		try {
		    File cf = scheme.load(bwk, uri);
		    if (cf != null)
		        content = MFile.readFile(cf);
		} catch (IOException e) {
		    throw new MException(e);
		}
		if (content == null) throw new NotFoundException("configuration not found", uri);
		ConfigType type = types.get(uri);
		YMap docE = type.create(bwk, content);
		if (docE.isEmpty()) {
		    log().w("Content is empty",uri);
		    return;
		}
		YList importE = docE.getList("import");
		
		// load imports FIRST and fill before processing
		loadImports(importE);

		YMap parametersE = docE.getMap("parameters");
		loadParameters(parametersE);
		
		YList pluginsE = docE.getList("plugins");
	    loadPlugins(pluginsE);
	    
        YList projectsE = docE.getList("projects");
        loadProjects(projectsE);
	    
        YList lifecyclesE = docE.getList("lifecycles");
        loadLifecycles(lifecyclesE);
        
		
	}

    private void loadParameters(YMap parametersE) {
        if (parametersE == null) return;
        
        if (parametersE.getBoolean("_clear"))
            ((MProperties)bwk.getParameters()).clear();
        
        for (String key : parametersE.getKeys()) {
            if (key.equals("_clear")) continue;
            ((MProperties)bwk.getParameters()).put(key, parametersE.getString(key));
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
            LifecycleImpl parent = (LifecycleImpl) bwk.getLifecycles().getOrNull(name);
            if (parent != null)
                steps = (StepsImpl) parent.getSteps();
        }

        loadSteps(stepsE, steps);
        
        ((LifecyclesImpl)bwk.getLifecycles()).put(name, new LifecycleImpl(name, steps));
        
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
        Step step = new StepImpl(map);
        return step;
    }

    protected void loadProjects(YList projectsE) {
        if (projectsE == null) return;
        for (YMap map : projectsE.toMapList()) {
            loadProject(map);
        }
    }

    protected void loadProject(YMap map) {
        Project project = new ProjectImpl(map, map.getBoolean("_merge") ? bwk.getProjects().getOrNull(map.getString("target")) : null );
        ((ProjectsImpl)bwk.getProjects()).put(project.getName(), project);
    }

    protected void loadPlugins(YList pluginsE) {
        if (pluginsE == null) return;
        for (YMap map : pluginsE.toMapList()) {
            loadPlugin(map);
        }
    }

    protected void loadPlugin(YMap map) {
        Plugin plugin = new PluginImpl(map, map.getBoolean("_merge") ? bwk.getPlugins().getOrNull(map.getString("target")) : null );
        ((PluginsImpl)bwk.getPlugins()).put(plugin.getTarget(), plugin);
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

    public Bwk getBwk() {
        return bwk;
    }

    public Set<String> getLoadedUris() {
        return loadedUris;
    }

    public List<Validator> getValidators() {
        return validators;
    }

}
