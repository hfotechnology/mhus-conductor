package de.mhus.con.api;

import java.util.LinkedList;

public interface MainOptionHandler {

	void execute(Cli cli, String cmd, LinkedList<String> queue);
	
	String getUsage(String cmd);
	
	String getDescription(String cmd);

}
