package de.mhus.cur.core;

import java.util.LinkedList;

import de.mhus.lib.core.MApi;
import de.mhus.lib.core.base.SingleBaseStrategy;
import de.mhus.lib.core.logging.Log;

@MainOption(alias="-X")
public class MainOptionLogDebug implements MainOptionHandler {

	@Override
	public void execute(MainCli cli, String cmd, LinkedList<String> queue) {
        MApi.setDirtyTrace(true);
        MApi.get().getLogFactory().setDefaultLevel(Log.LEVEL.DEBUG);
        MApi.get().getBaseControl().setFindStrategy(new SingleBaseStrategy());
	}

	@Override
	public String getDescription() {
		return "Enable Log DEBUG level.";
	}

}
