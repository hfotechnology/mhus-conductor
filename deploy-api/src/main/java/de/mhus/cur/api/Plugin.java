package de.mhus.cur.api;

public interface Plugin {

	enum SCOPE {PROJECT, STEP}
	
    String getTarget();

    String getUri();

    String getMojo();
    
    SCOPE getScope();

}
