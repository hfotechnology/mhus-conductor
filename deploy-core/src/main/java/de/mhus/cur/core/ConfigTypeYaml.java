package de.mhus.cur.core;

import org.yaml.snakeyaml.Yaml;

import de.mhus.cur.api.AConfigType;
import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.ConfigType;
import de.mhus.cur.api.YMap;

@AConfigType(name= {"yml","yaml"})
public class ConfigTypeYaml implements ConfigType {

	@Override
	public YMap create(Conductor cur, String content) {
		Yaml yaml = new Yaml();
		YMap docE = new YMap(yaml.load(content));
		return docE;
	}

}
