package de.mhus.cur.core;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

@MainOption(alias="--help")
public class MainOptionHelp implements MainOptionHandler {

	@Override
	public void execute(MainCli cli, String cmd, LinkedList<String> queue) {
		for (Entry<String, MainOptionHandler> handler : new TreeMap<>(cli.optionHandlers).entrySet()) {
			System.out.println(handler.getKey());
			String desc = handler.getValue().getDescription();
			if (desc != null) {
				desc = desc.replaceAll("(\\r\\n|\\n)", "\n     ");
				System.out.println("     " + handler.getValue().getDescription());
			}
		}
	}

	@Override
	public String getDescription() {
		return "Print help";
	}

}
