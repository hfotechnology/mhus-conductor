package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.MojoException;
import de.mhus.lib.core.MString;

@AMojo(name="cmd")
public class CmdMojo extends AbstractMavenExecute {

	@Override
	public void execute2(File dir, Context context) throws Exception {
		String cmd = MString.join(context.getStep().getArguments(), " "); //TODO add quotes and or escapes
		String[] res = ConUtil.execute(context.getStep().getTitle() + " " + context.getProject().getName(), dir, cmd, true);
		if (!res[2].equals("0"))
		    throw new MojoException(context, "not successful",cmd,res[1],res[2]);
	}

}
