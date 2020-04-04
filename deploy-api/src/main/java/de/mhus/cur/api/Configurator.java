package de.mhus.cur.api;

import java.net.URI;

import de.mhus.lib.core.IProperties;
import de.mhus.lib.errors.MException;

public interface Configurator {

	void configure(URI uri, Conductor cur, IProperties properties) throws MException;
	
}
