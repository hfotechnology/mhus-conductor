package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.MojoException;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MString;

@AMojo(name="docker")
public class DockerMojo extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        String dockerPath = ConUtil.cmdLocation(context.getConductor(), "docker");
        String namespace = context.getRecursiveProperty("dockerNamespace", null);

        String cmd = dockerPath + " " + (namespace == null ? "" : "-ns " + namespace + " ") + MString.join(context.getStep().getArguments(), " ");

        String path = context.getProject().getProperties().getString("dockerPath", null);
        if (path == null) {
            log().d("dockerPath not found in project");
            return false;
        }

        File dir = new File(context.getProject().getRootDir(), path);
        if (!dir.exists() || !dir.isDirectory()) {
            log().d("dockerPath not exists",path,dir.getAbsolutePath());
            return false;
        }

        String[] res = ConUtil.execute("docker " + context.getProject().getName() + "/" + path, dir, cmd, true);
        if (!res[2].equals("0"))
            throw new MojoException(context, "not successful",cmd,res[1],res[2]);
        return true;
    }

}
