package de.mhus.bwk.core;

import java.io.File;
import java.io.IOException;

import de.mhus.lib.core.util.MUri;

public interface Scheme {

	File load(Bwk bwk, MUri uri) throws IOException;

}
