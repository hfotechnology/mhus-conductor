package de.mhus.bwk.core;

import java.io.File;

import de.mhus.lib.core.MFile;
import de.mhus.lib.core.util.MUri;

public class FileScheme implements Scheme {

    @Override
    public String load(Bwk bwk, MUri uri) {

        String path = uri.getPath();
        File f = new File(path);
        if (!f.isAbsolute())
            f = new File(bwk.getRoot(), path).getAbsoluteFile(); // different root

        return MFile.readFile(f);
    }

}
