package de.mhus.con.core;

import org.yaml.snakeyaml.Yaml;

import de.mhus.con.api.AConfigType;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.ConfigType;
import de.mhus.con.api.YMap;

@AConfigType(name= {"yml","yaml"})
public class ConfigTypeYaml implements ConfigType {

	@Override
	public YMap create(Conductor con, String content) {
		Yaml yaml = new Yaml();
		YMap docE = new YMap(yaml.load(content));
		return docE;
	}

}
