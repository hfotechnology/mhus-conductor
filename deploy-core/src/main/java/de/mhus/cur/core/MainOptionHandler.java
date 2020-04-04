package de.mhus.cur.core;

import java.util.LinkedList;

public interface MainOptionHandler {

	void execute(MainCli cli, String cmd, LinkedList<String> queue);
	
	String getDescription();

}
