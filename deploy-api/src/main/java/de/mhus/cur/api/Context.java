package de.mhus.cur.api;

import de.mhus.lib.core.IProperties;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.errors.MException;

public interface Context {

    String make(String in) throws MException;

    IReadProperties getProperties();

	Plugin getPlugin();

	Project getProject();

	Step getStep();
    
}
