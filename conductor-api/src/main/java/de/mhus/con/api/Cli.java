package de.mhus.con.api;

import java.util.Map;

public interface Cli {

	Map<String, MainOptionHandler> getOptions();
	
}
