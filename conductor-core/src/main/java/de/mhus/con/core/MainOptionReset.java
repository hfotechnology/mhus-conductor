package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;

@AOption(alias="--")
public class MainOptionReset implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {
		((MainCli)cli).resetCon();
	}

	@Override
	public String getUsage(String cmd) {
		return null;
	}

	@Override
	public String getDescription(String cmd) {
		return "Reset the Conductor engine, this will cause to reload the configuration and all properties";
	}

}
