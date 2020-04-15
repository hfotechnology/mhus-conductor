package de.mhus.con.core;

import de.mhus.con.api.AConfigType;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.ConfigType;
import de.mhus.lib.core.yaml.MYaml;
import de.mhus.lib.core.yaml.YMap;

@AConfigType(name= {"yml","yaml"})
public class ConfigTypeYaml implements ConfigType {

	@Override
	public YMap create(Conductor con, String content) {
	    return MYaml.loadFromString(content);
	}

}
