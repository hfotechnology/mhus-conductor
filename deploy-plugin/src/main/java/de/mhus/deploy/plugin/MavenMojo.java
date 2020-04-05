package de.mhus.deploy.plugin;

import de.mhus.cur.api.Context;
import de.mhus.cur.api.CurUtil;
import de.mhus.cur.api.ExecutePlugin;
import de.mhus.cur.api.AMojo;
import de.mhus.lib.core.MString;

@AMojo(name="test")
public class MavenMojo implements ExecutePlugin {

	@Override
	public void execute(Context context) throws Exception {
        String mvnPath = CurUtil.cmdLocation(context.getConductor(), "mvn");
		String cmd = mvnPath + " " + MString.join(context.getStep().getArguments(), " "); //TODO add quotes and or escapes
		CurUtil.execute(context.getStep().getTitle(), context.getProject().getRootDir(), cmd);
	}

}
