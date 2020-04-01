package de.mhus.bwk.core.test;

import de.mhus.lib.core.util.MUri;

public class TestUtil {

    public static String getPluginVersion(String uriStr) {
        MUri uri = MUri.toUri(uriStr);
        String[] parts = uri.getPath().split("/");
        return parts[2];
    }

}
