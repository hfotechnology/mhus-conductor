package de.mhus.con.api;

import de.mhus.lib.errors.MException;

public interface Validator {

    void validate(Conductor con) throws MException;

    void validate(Context context) throws MException;

}
