package de.mhus.con.plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.MojoException;
import de.mhus.con.api.ValuePlugin;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.logging.Log;
import de.mhus.lib.errors.NotFoundException;

@AMojo(name = "git.version",target = "git.version")
public class GitVersionValue extends MLog implements ValuePlugin {

    private static String version;
    
    @Override
    public String getValue(Context context, String value, Map<String, Object> attributes) throws Exception {
        initGitVersion(context, log());
        return version;
    }

    private synchronized void initGitVersion(Context context, Log log) throws NotFoundException, IOException, MojoException {
        if (version != null) return;
        String gitPath = ConUtil.cmdLocation(context.getConductor(), "git");
        String cmd = gitPath + " --version";
        String[] res = ConUtil.execute("maven version", new File("."), cmd, false);
        if (!res[2].equals("0"))
            throw new MojoException(context, "not successful", cmd, res[1], res[2]);

        version = res[0].split(" ")[2];
    }

}
