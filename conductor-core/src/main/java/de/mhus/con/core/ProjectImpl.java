package de.mhus.con.core;

import java.io.File;

import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Labels;
import de.mhus.con.api.Project;
import de.mhus.lib.core.IProperties;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MSystem;

public class ProjectImpl implements Project {

    protected String name;
    protected String path;
    protected Labels labels;
    protected Conductor con;
    protected File rootDir;
    protected MProperties properties = new MProperties();
    private STATUS status;

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

    public void init(Conductor con) {
        this.con = con;
        rootDir = ConUtil.getFile(con.getRoot(), getPath());
    }

    @Override
    public File getRootDir() {
        return rootDir;
    }


	@Override
	public IProperties getProperties() {
		return properties;
	}
    
    @Override
	public String toString() {
		return MSystem.toString(this, name);
	}

    public void setStatus(STATUS status) {
        this.status = status;
    }
    
    @Override
    public STATUS getStatus() {
        return status;
    }
    
}
