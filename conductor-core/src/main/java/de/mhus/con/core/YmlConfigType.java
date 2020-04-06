package de.mhus.con.core;

import org.yaml.snakeyaml.Yaml;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.ConfigType;
import de.mhus.con.api.YMap;

public class YmlConfigType implements ConfigType {

    @Override
    public YMap create(Conductor con, String content) {
        Yaml yaml = new Yaml();
        Object obj = yaml.load(content);
        return new YMap(obj);
    }

}
