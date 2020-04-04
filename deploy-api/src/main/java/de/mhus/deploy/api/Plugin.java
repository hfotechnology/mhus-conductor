package de.mhus.deploy.api;

public interface Plugin {

	enum SCOPE {PROJECT, STEP}
	
    String getTarget();

    String getUri();

    String getMojo();
    
    SCOPE getScope();

}
