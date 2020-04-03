package de.mhus.cur.core;

import java.io.File;

public class ProjectImpl implements Project {

    private String name;
    private String path;
    private Labels labels;
    @SuppressWarnings("unused")
    private Conductor cur;
    private File rootDir;

    public ProjectImpl(YMap map, Project merge) {
        name = map.getString("name");
        path = map.getString("path", merge == null ? null : merge.getPath());
        YMap l = map.getMap("labels");
        if (l == null && merge == null)
            labels = new LabelsImpl();
        else if (l == null && merge != null)
            labels = merge.getLabels();
        else
            labels = new LabelsImpl(l);
    }

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
    
}
