package de.mhus.deploy.api;

import java.io.File;

import de.mhus.lib.core.IReadProperties;

public interface Project {

	Labels getLabels();

    String getName();

    String getPath();

    File getRootDir();
	
    IReadProperties getProperties();
    
}
