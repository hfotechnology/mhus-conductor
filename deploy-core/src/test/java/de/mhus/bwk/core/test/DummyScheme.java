package de.mhus.bwk.core.test;

import de.mhus.bwk.core.Bwk;
import de.mhus.bwk.core.Scheme;
import de.mhus.lib.core.util.MUri;

public class DummyScheme implements Scheme {

    @Override
    public String load(Bwk bwk, MUri uri) {
        return "";
    }

}
