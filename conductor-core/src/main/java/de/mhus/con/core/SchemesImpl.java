package de.mhus.con.core;

import de.mhus.con.api.Scheme;
import de.mhus.con.api.Schemes;
import de.mhus.lib.core.util.MUri;

public class SchemesImpl extends XCollection<Scheme> implements Schemes {

    @Override
    public Scheme get(MUri uri) {
        return get(uri.getScheme());
    }

}
