package de.mhus.con.api;

import de.mhus.lib.core.yaml.YMap;

public interface ConfigType {

	YMap create(Conductor con, String content);

}
