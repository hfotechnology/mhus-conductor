package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;
import de.mhus.lib.core.MApi;
import de.mhus.lib.core.logging.Log;

@AOption(alias= {"-v","-vv","-vvv"})
public class MainOptionLogDebug implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {
        MApi.setDirtyTrace(cmd.equals("-vv"));
        MApi.get().getLogFactory().setDefaultLevel(cmd.equals("-vvv") ? Log.LEVEL.TRACE : Log.LEVEL.DEBUG);
	}

	@Override
	public String getDescription(String cmd) {
		return "Enable Log DEBUG/TRACE level.";
	}

	@Override
	public String getUsage(String cmd) {
		return null;
	}

}
