package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.MojoException;
import de.mhus.lib.core.MLog;

@AMojo(name="git")
public class GitMojo extends MLog implements ExecutePlugin {

	@Override
	public boolean execute(Context context) throws Exception {
        String gitPath = ConUtil.cmdLocation(context.getConductor(), "git");
        for (String arg : context.getStep().getArguments()) {
        	String cmd = gitPath + " " + arg;
        	String[] res = ConUtil.execute(context.getStep().getTitle() + " " + context.getProject().getName(), context.getProject().getRootDir(), cmd, true);
        	if (!res[2].equals("0"))
        		throw new MojoException(context, "not successful",cmd,res[1],res[2]);
        }
        return true;
	}

}
