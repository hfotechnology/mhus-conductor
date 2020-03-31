package de.mhus.deploy.core;

import java.io.InputStream;
import java.net.URI;

public interface Scheme {

	String load(URI uri);

}
