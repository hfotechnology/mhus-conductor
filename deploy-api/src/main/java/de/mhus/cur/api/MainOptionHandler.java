package de.mhus.cur.api;

import java.util.LinkedList;

public interface MainOptionHandler {

	void execute(Cli cli, String cmd, LinkedList<String> queue);
	
	String getDescription(String cmd);

}
