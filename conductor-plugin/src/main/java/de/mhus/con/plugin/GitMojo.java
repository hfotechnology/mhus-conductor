package de.mhus.con.plugin;

import java.io.File;

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
	    File dir = context.getProject().getRootDir();
        String gitPath = ConUtil.cmdLocation(context.getConductor(), "git");
        for (String arg : context.getStep().getArguments()) {
        	String cmd = gitPath + " " + arg;
            if (context.getConductor().getProperties().getBoolean(ConUtil.PROPERTY_TRY, false)) {
                log().i("Would Execute",cmd,dir);
                continue;
            }
        	String[] res = ConUtil.execute(context.getStep().getTitle() + " " + context.getProject().getName(), dir, cmd, true);
        	if (!res[2].equals("0") && !context.getProperties().getBoolean(ConUtil.PROPERTY_STEP_IGNORE_RETURN_CODE, false))
        		throw new MojoException(context, "not successful",cmd,res[1],res[2]);
        }
        return true;
	}

}
