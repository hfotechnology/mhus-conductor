package de.mhus.con.core;

import java.io.File;
import java.util.Map;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Lifecycles;
import de.mhus.con.api.Plugins;
import de.mhus.con.api.Projects;
import de.mhus.con.api.Schemes;
import de.mhus.con.api.Validator;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;

public class ConductorImpl implements Conductor {

    protected PluginsImpl plugins = new PluginsImpl();
    protected LifecyclesImpl lifecycles = new LifecyclesImpl();
    protected ProjectsImpl projects = new ProjectsImpl();
    protected MProperties properties = new MProperties();
    protected File root;
    protected Schemes schemes = new SchemesImpl();
    public Map<String, Validator> validators;
    
    public ConductorImpl(File rootDir) {
        this.root = rootDir.getAbsoluteFile();
    }

    @Override
    public Plugins getPlugins() {
        return plugins;
    }

    @Override
    public Lifecycles getLifecycles() {
        return lifecycles;
    }

    @Override
    public Projects getProjects() {
        return projects;
    }

    @Override
    public IReadProperties getProperties() {
        return properties;
    }

    @Override
    public File getRoot() {
        return root;
    }

    @Override
	public Schemes getSchemes() {
		return schemes;
	}

	@Override
	public ClassLoader getClassLoader() {
		return getClass().getClassLoader();
	}

	@Override
	public void close() {
		
	}

    public Map<String, Validator> getValidators() {
        return validators;
    }

}
