package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;

@AOption(alias="-")
public class MainOptionDummy implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {
	}

	@Override
	public String getUsage(String cmd) {
		return null;
	}

	@Override
	public String getDescription(String cmd) {
		return "A dummy option to separate lifeccle executions";
	}

}
