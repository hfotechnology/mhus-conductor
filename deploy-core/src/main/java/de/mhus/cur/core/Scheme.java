package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;

import de.mhus.lib.core.util.MUri;

public interface Scheme {

	File load(Conductor cur, MUri uri) throws IOException;

}
