package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.Scheme;
import de.mhus.lib.core.util.MUri;

public class FileScheme implements Scheme {

    @Override
    public File load(Conductor cur, MUri uri) throws IOException {

        String path = uri.getPath();
        File f = new File(path);
        if (!f.isAbsolute())
            f = new File(cur.getRoot(), path).getAbsoluteFile(); // different root

        return f;
    }

}
