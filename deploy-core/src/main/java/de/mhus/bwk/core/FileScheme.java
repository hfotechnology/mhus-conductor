package de.mhus.bwk.core;

import java.io.File;
import java.io.IOException;

import de.mhus.lib.core.util.MUri;

public class FileScheme implements Scheme {

    @Override
    public File load(Bwk bwk, MUri uri) throws IOException {

        String path = uri.getPath();
        File f = new File(path);
        if (!f.isAbsolute())
            f = new File(bwk.getRoot(), path).getAbsoluteFile(); // different root

        return f;
    }

}
