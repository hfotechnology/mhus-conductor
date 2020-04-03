package de.mhus.cur.core;

import java.io.File;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;

public class ConductorImpl implements Conductor {

    protected PluginsImpl plugins = new PluginsImpl();
    protected LifecyclesImpl lifecycles = new LifecyclesImpl();
    protected ProjectsImpl projects = new ProjectsImpl();
    protected MProperties parameters = new MProperties();
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
        return parameters;
    }

    @Override
    public File getRoot() {
        return root;
    }

}
