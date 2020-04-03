package de.mhus.cur.core;

import java.io.File;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.Labels;
import de.mhus.deploy.api.Project;
import de.mhus.lib.core.IProperties;
import de.mhus.lib.core.MProperties;

public class ProjectImpl implements Project {

    protected String name;
    protected String path;
    protected Labels labels;
    protected Conductor cur;
    protected File rootDir;
    protected MProperties properties = new MProperties();

    @Override
    public Labels getLabels() {
        return labels;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void init(Conductor cur) {
        this.cur = cur;
        
        rootDir = new File(getPath());
        if (!rootDir.isAbsolute())
            rootDir = new File(cur.getRoot(),getPath());
    }

    @Override
    public File getRootDir() {
        return rootDir;
    }

    @Override
    public String toString() {
        return name;
    }

	@Override
	public IProperties getProperties() {
		return properties;
	}
    
}
