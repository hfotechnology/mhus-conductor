package de.mhus.con.api;

import de.mhus.lib.core.MString;

public interface ConfigTypes extends ICollection<ConfigType> {

//    default ConfigType get(MUri uri) {
//        String path = uri.getPath();
//        String ext = MString.afterLastIndex(path, '.').toLowerCase();
//        return get(ext);
//    }

    default ConfigType getForPath(String path) {
        String ext = MString.afterLastIndex(path, '.').toLowerCase();
        return get(ext);
    }

}
