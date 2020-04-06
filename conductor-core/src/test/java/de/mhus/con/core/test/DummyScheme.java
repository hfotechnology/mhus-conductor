package de.mhus.con.core.test;

import java.io.File;
import java.io.IOException;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Scheme;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.util.MUri;

public class DummyScheme implements Scheme {

    @Override
    public File load(Conductor con, MUri uri) throws IOException {
        File f = File.createTempFile(DummyScheme.class.getSimpleName(), ".txt");
        MFile.writeFile(f, "");
        return f;
    }

}
