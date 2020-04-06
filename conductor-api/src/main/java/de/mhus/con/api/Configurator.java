package de.mhus.con.api;

import java.net.URI;

import de.mhus.lib.core.IProperties;
import de.mhus.lib.errors.MException;

public interface Configurator {

	void configure(URI uri, Conductor con, IProperties properties) throws MException;
	
}
