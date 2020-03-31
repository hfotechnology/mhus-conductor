package de.mhus.deploy.core;

import java.net.URI;

import de.mhus.lib.core.MString;

public class ConfigTypesImpl extends XCollection<ConfigType> implements ConfigTypes {

	@Override
	public ConfigType get(URI uri) {
		String path = uri.getPath();
		String ext = MString.afterLastIndex(path, '.').toLowerCase();
		return get(ext);
	}

}
