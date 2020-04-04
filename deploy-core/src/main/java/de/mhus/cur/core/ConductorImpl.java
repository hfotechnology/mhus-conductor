package de.mhus.cur.core;

import java.io.File;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.Lifecycles;
import de.mhus.cur.api.Plugins;
import de.mhus.cur.api.Projects;
import de.mhus.cur.api.Schemes;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;

public class ConductorImpl implements Conductor {

    protected PluginsImpl plugins = new PluginsImpl();
    protected LifecyclesImpl lifecycles = new LifecyclesImpl();
    protected ProjectsImpl projects = new ProjectsImpl();
    protected MProperties properties = new MProperties();
    protected File root;
    protected Schemes schemes = new SchemesImpl();
    
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

}
