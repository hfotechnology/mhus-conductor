package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.MojoException;
import de.mhus.lib.core.MLog;

@AMojo(name="git.clone")
public class GitCloneMojo extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        
        File dir = context.getProject().getRootDir();
        String gitUrl = context.getProject().getProperties().getString("gitUrl", null);
        String gitBranch = context.getProject().getProperties().getString("gitBranch", null);
        if (gitUrl == null) {
            log().d("gitUrl not set, skip");
            return false;
        }
        if (dir.exists() && dir.isDirectory()) {
            log().d("dir exists, nothing to do");
            return true;
        }
        
        // create directory
        dir.mkdirs();
        
        String gitPath = ConUtil.cmdLocation(context.getConductor(), "git");
        String cmd = gitPath + " clone " + gitUrl + " ." + (gitBranch != null ? " -b " + gitBranch : "");
        String[] res = ConUtil.execute(context.getStep().getTitle() + " " + context.getProject().getName(), dir, cmd, true);
        if (!res[2].equals("0"))
            throw new MojoException(context, "not successful",cmd,res[1],res[2]);
        return true;
    }

}
