package de.mhus.con.plugin;

import java.io.File;
import java.util.Map;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.MojoException;
import de.mhus.con.api.ValuePlugin;
import de.mhus.lib.core.MLog;

@AMojo(name = "maven.version",target = "maven.version")
public class MavenVersionValue extends MLog implements ValuePlugin {

    @Override
    public String getValue(Context context, String value, Map<String, Object> attributes) throws Exception {
        String mvnPath = ConUtil.cmdLocation(context.getConductor(), "mvn");
        String cmd = mvnPath + " --version";
        String[] res = ConUtil.execute(cmd, new File("."), "maven --version", false);
        if (!res[2].equals("0"))
            throw new MojoException(context, "not successful", cmd, res[1], res[2]);
        log().d(cmd,res[0]);
        return res[0];
    }

}
