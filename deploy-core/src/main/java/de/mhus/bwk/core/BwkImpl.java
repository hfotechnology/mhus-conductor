package de.mhus.bwk.core;

import java.io.File;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;

public class BwkImpl implements Bwk {

    protected PluginsImpl plugins = new PluginsImpl();
    protected LifecyclesImpl lifecycles = new LifecyclesImpl();
    protected ProjectsImpl projects = new ProjectsImpl();
    protected MProperties parameters = new MProperties();
    protected File root;
    
    public BwkImpl(File rootDir) {
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
    public IReadProperties getParameters() {
        return parameters;
    }

    @Override
    public File getRoot() {
        return root;
    }

}
