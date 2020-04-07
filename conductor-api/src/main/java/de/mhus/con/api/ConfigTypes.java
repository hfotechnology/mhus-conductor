package de.mhus.con.api;

import java.io.File;

import de.mhus.lib.core.MString;

public interface ConfigTypes extends ICollection<ConfigType> {

//    default ConfigType get(MUri uri) {
//        String path = uri.getPath();
//        String ext = MString.afterLastIndex(path, '.').toLowerCase();
//        return get(ext);
//    }

    default ConfigType get(File cf) {
        String name = cf.getName();
        String ext = MString.afterLastIndex(name, '.').toLowerCase();
        return get(ext);
    }

}
