package de.mhus.cur.core;

import de.mhus.deploy.api.Scheme;
import de.mhus.deploy.api.Schemes;
import de.mhus.lib.core.util.MUri;

public class SchemesImpl extends XCollection<Scheme> implements Schemes {

    @Override
    public Scheme get(MUri uri) {
        return get(uri.getScheme());
    }

}
