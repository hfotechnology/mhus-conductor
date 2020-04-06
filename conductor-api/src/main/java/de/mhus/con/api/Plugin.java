package de.mhus.con.api;

public interface Plugin {

	enum SCOPE {PROJECT, STEP}
	
    String getTarget();

    String getUri();

    String getMojo();
    
    SCOPE getScope();

}
