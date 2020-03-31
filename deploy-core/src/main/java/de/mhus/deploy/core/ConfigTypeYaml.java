package de.mhus.deploy.core;

import org.yaml.snakeyaml.Yaml;

public class ConfigTypeYaml implements ConfigType {

	@Override
	public YMap create(String content) {
		Yaml yaml = new Yaml();
		YMap docE = new YMap(yaml.load(content));
		return docE;
	}

}
