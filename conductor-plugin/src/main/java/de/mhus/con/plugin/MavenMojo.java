package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.lib.core.MString;

@AMojo(name="test")
public class MavenMojo extends AbstractExecute {

	@Override
	public void execute2(Context context) throws Exception {
        String mvnPath = ConUtil.cmdLocation(context.getConductor(), "mvn");
		String cmd = mvnPath + " " + MString.join(context.getStep().getArguments(), " "); //TODO add quotes and or escapes
		ConUtil.execute(context.getStep().getTitle(), context.getProject().getRootDir(), cmd);
	}

}
