package de.mhus.bwk.core;

import de.mhus.lib.errors.MException;

public interface Context {

    String make(String in) throws MException;

}
