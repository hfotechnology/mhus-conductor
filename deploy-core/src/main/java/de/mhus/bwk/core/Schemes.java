package de.mhus.bwk.core;

import de.mhus.lib.core.util.MUri;

public interface Schemes extends ICollection<Scheme> {

    Scheme get(MUri uri);

}
