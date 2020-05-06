package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;
import de.mhus.conductor.api.meta.Version;

@AOption(alias="-version")
public class MainOptionVersion implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {

		System.out.println("Conductor Version: " + Version.VERSION);
	}

	@Override
	public String getUsage(String cmd) {
		return null;
	}

	@Override
	public String getDescription(String cmd) {
		return "Print version";
	}

}
