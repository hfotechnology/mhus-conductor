package de.mhus.deploy.plugin;

import de.mhus.cur.api.Context;
import de.mhus.cur.api.CurUtil;
import de.mhus.cur.api.ExecutePlugin;
import de.mhus.cur.api.Mojo;
import de.mhus.lib.core.MString;

@Mojo(name="test")
public class MavenMojo implements ExecutePlugin {

	@Override
	public void execute(Context context) throws Exception {
		String mvn = CurUtil.cmdLocation("mvn");
		String cmd = mvn + " " + MString.join(context.getStep().getParameters(), " "); //TODO add quotes and or escapes
		CurUtil.execute(context.getStep().getTitle(), context.getProject().getRootDir(), cmd);
	}

}
