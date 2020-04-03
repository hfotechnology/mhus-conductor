package de.mhus.cur.core;

import de.mhus.lib.errors.MException;

public interface Validator {

    void validate(Conductor cur) throws MException;

}
