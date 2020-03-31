package de.mhus.deploy.core;

import java.net.URI;

public interface ConfigTypes extends ICollection<ConfigType> {

	ConfigType get(URI uri);

}
