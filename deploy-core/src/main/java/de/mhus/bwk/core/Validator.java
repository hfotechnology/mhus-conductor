package de.mhus.bwk.core;

import de.mhus.lib.errors.MException;

public interface Validator {

    void validate(Bwk bwk) throws MException;

}
