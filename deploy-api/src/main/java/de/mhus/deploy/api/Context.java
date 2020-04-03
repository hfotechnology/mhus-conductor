package de.mhus.deploy.api;

import de.mhus.lib.errors.MException;

public interface Context {

    String make(String in) throws MException;

}
