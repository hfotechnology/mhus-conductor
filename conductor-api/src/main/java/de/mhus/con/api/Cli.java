package de.mhus.con.api;

import java.util.Map;

public interface Cli {

	Map<String, MainOptionHandler> getOptions();

    Map<String, Scheme> getSchemes();

    Map<String, ConfigType> getConfigTypes();

    Map<String, Validator> getValidators();

    Conductor getConductor();
	
}
