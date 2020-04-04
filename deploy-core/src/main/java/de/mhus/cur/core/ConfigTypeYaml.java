package de.mhus.cur.core;

import org.yaml.snakeyaml.Yaml;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.ConfigType;
import de.mhus.cur.api.YMap;

public class ConfigTypeYaml implements ConfigType {

	@Override
	public YMap create(Conductor cur, String content) {
		Yaml yaml = new Yaml();
		YMap docE = new YMap(yaml.load(content));
		return docE;
	}

}
