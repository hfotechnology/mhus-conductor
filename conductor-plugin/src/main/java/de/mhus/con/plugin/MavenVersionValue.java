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
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.logging.Log;
import de.mhus.lib.errors.NotFoundException;

@AMojo(name = "maven.version",target = "maven.version")
public class MavenVersionValue extends MLog implements ValuePlugin {

    private static MProperties versions;

    @Override
    public String getValue(Context context, String value, Map<String, Object> attributes) throws Exception {
        initMavenVersion(context,log());
        if (MString.isEmpty(value)) value = "maven.version";
        return versions.getString(value);
    }

    public static synchronized void initMavenVersion(Context context, Log log) throws NotFoundException, IOException, MojoException {
        if (versions != null) return;
        String mvnPath = ConUtil.cmdLocation(context.getConductor(), "mvn");
        String cmd = mvnPath + " --version";
        String[] res = ConUtil.execute("maven version", new File("."), cmd, false);
        if (!res[2].equals("0"))
            throw new MojoException(context, "not successful", cmd, res[1], res[2]);
        
        versions = new MProperties();
        
        try {
            String[] lines = res[0].split("\n");
            versions.setString("maven.version", lines[0].split(" ")[2]);
            versions.setString("os.name", lines[4].split("\"")[1]);
            versions.setString("os.version", lines[4].split("\"")[3]);
            versions.setString("os.arch", lines[4].split("\"")[5]);
            versions.setString("os.family", lines[4].split("\"")[7]);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
