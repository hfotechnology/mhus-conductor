package de.mhus.cur.api;

import de.mhus.lib.core.util.MUri;

public interface ConfigTypes extends ICollection<ConfigType> {

	ConfigType get(MUri uri);

}
