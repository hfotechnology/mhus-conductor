package de.mhus.cur.core;

import java.util.LinkedList;

import de.mhus.cur.api.Cli;
import de.mhus.cur.api.MainOption;
import de.mhus.cur.api.MainOptionHandler;
import de.mhus.deploy.api.meta.Version;

@MainOption(alias="--version")
public class MainOptionVersion implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {

		System.out.println("Condctor Version: " + Version.VERSION);
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
