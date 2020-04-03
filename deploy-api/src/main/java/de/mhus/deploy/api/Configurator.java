package de.mhus.deploy.api;

import java.net.URI;

import de.mhus.lib.errors.MException;

public interface Configurator {

	void configure(URI uri, Conductor cur) throws MException;
	
}
