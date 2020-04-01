package de.mhus.bwk.core.test;

import java.io.File;
import java.io.IOException;

import de.mhus.bwk.core.Bwk;
import de.mhus.bwk.core.Scheme;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.util.MUri;

public class DummyScheme implements Scheme {

    @Override
    public File load(Bwk bwk, MUri uri) throws IOException {
        File f = File.createTempFile(DummyScheme.class.getSimpleName(), ".txt");
        MFile.writeFile(f, "");
        return f;
    }

}
