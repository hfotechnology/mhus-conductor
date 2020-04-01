package de.mhus.bwk.core;

import de.mhus.lib.core.util.MUri;

public interface Scheme {

	String load(Bwk bwk, MUri uri);

}
