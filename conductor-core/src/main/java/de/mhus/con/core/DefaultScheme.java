package de.mhus.con.core;

import java.io.File;
import java.io.IOException;

import de.mhus.con.api.AScheme;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Scheme;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

@AScheme(name = "default")
public class DefaultScheme implements Scheme {

    @Override
    public File load(Conductor con, MUri uri) throws IOException, NotFoundException {
        String path = uri.getPath();

        Scheme mvnScheme = con.getSchemes().get("mvn");
        MUri defUri = ConUtil.getDefaultConfiguration(path);
        return mvnScheme.load(con, defUri);
    }

}
