package de.mhus.cur.api;

import java.io.File;

import de.mhus.lib.core.IReadProperties;

public interface Conductor {

    Plugins getPlugins();

    Lifecycles getLifecycles();

    Projects getProjects();

    IReadProperties getProperties();

    File getRoot();

	Schemes getSchemes();

	ClassLoader getClassLoader();
	
}
