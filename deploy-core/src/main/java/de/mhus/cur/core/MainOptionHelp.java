package de.mhus.cur.core;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import de.mhus.cur.api.Cli;
import de.mhus.cur.api.MainOption;
import de.mhus.cur.api.MainOptionHandler;

@MainOption(alias="--help")
public class MainOptionHelp implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {
		System.out.println("Arguments:");
		System.out.println(" <lifecycle> [property] ...");
		System.out.println(" Cascade [options] [arguments] [options] [arguments] ... to execute multiple lifecycles, use th empty Option '-' to separate.");
		System.out.println("Property:");
		System.out.println(" key=value  - Set the value of the key");
		System.out.println(" key        - Set the key to true");
		System.out.println("Options:");
		for (Entry<String, MainOptionHandler> handler : new TreeMap<>( cli.getOptions() ).entrySet()) {
			System.out.println(" " + handler.getKey());
			String desc = handler.getValue().getDescription(handler.getKey());
			if (desc != null) {
				desc = desc.replaceAll("(\\r\\n|\\n)", "\n     ");
				System.out.println("     " + desc);
			}
		}
	}

	@Override
	public String getDescription(String cmd) {
		return "Print help";
	}

}
