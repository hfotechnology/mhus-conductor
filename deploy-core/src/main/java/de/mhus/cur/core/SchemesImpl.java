package de.mhus.cur.core;

import de.mhus.lib.core.util.MUri;

public class SchemesImpl extends XCollection<Scheme> implements Schemes {

    @Override
    public Scheme get(MUri uri) {
        return get(uri.getScheme());
    }

}
