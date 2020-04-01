package de.mhus.bwk.core;

import de.mhus.lib.core.util.MUri;

public interface Plugin {

    String getTarget();

    String getUrl();

    String getMojo();

    MUri getMUri();
    
}
