package de.mhus.cur.api;

import java.util.Map;

public interface Cli {

	Map<String, MainOptionHandler> getOptions();
	
}
