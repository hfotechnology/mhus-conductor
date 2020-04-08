package de.mhus.con.api;

import java.io.File;

import de.mhus.lib.core.IReadProperties;

public interface Project {

    enum STATUS {SKIPPED,FAILURE,SUCCESS}
	
    Labels getLabels();

    String getName();

    String getPath();

    File getRootDir();
	
    IReadProperties getProperties();
    
    STATUS getStatus();
    
}
