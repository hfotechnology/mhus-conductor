package de.mhus.cur.core;

import java.util.LinkedList;

import de.mhus.cur.api.Cli;
import de.mhus.cur.api.MainOption;
import de.mhus.cur.api.MainOptionHandler;

@MainOption(alias="-c")
public class MainOptionConfigurationFile implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {
		((MainCli)cli).configFile = queue.removeFirst();
		((MainCli)cli).resetCur();
	}

	@Override
	public String getDescription(String cmd) {
		return "Set configuration file, default is file:conductor.yml or as fallback the conductor default.yml";
	}

	@Override
	public String getUsage(String cmd) {
		return "<uri>";
	}

}
