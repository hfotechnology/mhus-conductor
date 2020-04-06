package de.mhus.con.core;

import java.io.File;
import java.io.IOException;

import de.mhus.con.api.AScheme;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Scheme;
import de.mhus.lib.core.util.MUri;

@AScheme(name="file")
public class FileScheme implements Scheme {

    @Override
    public File load(Conductor con, MUri uri) throws IOException {

        String path = uri.getPath();
        File f = ConUtil.getFile(con.getRoot(), path);

        return f;
    }

}
