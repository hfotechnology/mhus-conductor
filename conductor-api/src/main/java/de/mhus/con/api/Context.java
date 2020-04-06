package de.mhus.con.api;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.errors.MException;

public interface Context {

    String make(String in) throws MException;

    IReadProperties getProperties();

	Plugin getPlugin();

	Project getProject();

	Step getStep();
	
	Conductor getConductor();
    
}
