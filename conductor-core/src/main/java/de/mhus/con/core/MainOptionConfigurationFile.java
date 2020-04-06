package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;

@AOption(alias="-c")
public class MainOptionConfigurationFile implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {
		((MainCli)cli).configFile = queue.removeFirst();
		((MainCli)cli).resetCon();
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
