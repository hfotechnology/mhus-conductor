package de.mhus.con.api;

import java.io.Closeable;
import java.io.File;

import de.mhus.lib.core.IReadProperties;

public interface Conductor extends Closeable {

    Plugins getPlugins();

    Lifecycles getLifecycles();

    Projects getProjects();

    IReadProperties getProperties();

    File getRoot();

	Schemes getSchemes();

	ClassLoader getClassLoader();
	
}
