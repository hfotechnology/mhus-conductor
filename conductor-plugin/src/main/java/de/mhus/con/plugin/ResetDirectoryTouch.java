package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;

@AMojo(name="resetDirectoryTouch")
public class ResetDirectoryTouch extends AbstractMavenExecute {

    @Override
    public boolean execute2(File dir, String moduleName, Context context) throws Exception {
        dir.setLastModified(0);
        return true;
    }

}
