package de.mhus.lib.versioning;

public class Util {

    public static boolean isSnapshot(String version) {
        return version == null || version.contains("SNAPSHOT");
    }

}
