package de.mhus.cur.core;

import de.mhus.deploy.api.ConfigType;
import de.mhus.deploy.api.ConfigTypes;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.util.MUri;

public class ConfigTypesImpl extends XCollection<ConfigType> implements ConfigTypes {

	@Override
	public ConfigType get(MUri uri) {
		String path = uri.getPath();
		String ext = MString.afterLastIndex(path, '.').toLowerCase();
		return get(ext);
	}

}
