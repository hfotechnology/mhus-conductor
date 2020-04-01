package de.mhus.bwk.core;

import java.net.URI;

import de.mhus.lib.errors.MException;

public interface Configurator {

	void configure(URI uri, Bwk bwk) throws MException;
	
}
