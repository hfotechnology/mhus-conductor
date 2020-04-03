package de.mhus.cur.core;

import java.net.URI;

import de.mhus.lib.errors.MException;

public interface Configurator {

	void configure(URI uri, Conductor cur) throws MException;
	
}
