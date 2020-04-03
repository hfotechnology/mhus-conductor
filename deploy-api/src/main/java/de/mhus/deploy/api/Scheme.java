package de.mhus.deploy.api;

import java.io.File;
import java.io.IOException;

import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

public interface Scheme {

	File load(Conductor cur, MUri uri) throws IOException, NotFoundException;

}
