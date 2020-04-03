package de.mhus.cur.core;

import org.yaml.snakeyaml.Yaml;

public class YmlConfigType implements ConfigType {

    @Override
    public YMap create(Conductor cur, String content) {
        Yaml yaml = new Yaml();
        Object obj = yaml.load(content);
        return new YMap(obj);
    }

}
