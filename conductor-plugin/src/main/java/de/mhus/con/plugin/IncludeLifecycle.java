package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.lib.core.MLog;

@AMojo(name="con.includeLifecycle")
public class IncludeLifecycle extends MLog implements ExecutePlugin {

	@Override
	public boolean execute(Context context) throws Exception {
		for (String arg : context.getStep().getArguments()) {
			System.out.println(">>> Include Lifecycle " + arg);
			context.getExecutor().execute(context.getConductor(), arg);
			System.out.println("<<< End Lifecycle " + arg);
		}
		return true;
	}

}
