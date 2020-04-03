package de.mhus.deploy.api;

import de.mhus.lib.errors.MException;

public interface Validator {

    void validate(Conductor cur) throws MException;

}
