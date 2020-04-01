package de.mhus.bwk.core;

import java.io.File;

import de.mhus.lib.core.IReadProperties;

public interface Bwk {

    Plugins getPlugins();

    Lifecycles getLifecycles();

    Projects getProjects();

    IReadProperties getParameters();

    File getRoot();
	
	
	
}
