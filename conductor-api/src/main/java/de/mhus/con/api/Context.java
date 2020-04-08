package de.mhus.con.api;

import java.util.List;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.errors.MException;

public interface Context {

    String make(String in) throws MException;

    IReadProperties getProperties();

	Plugin getPlugin();

	Project getProject();

	Step getStep();
	
	Conductor getConductor();

    Executor getExecutor();

    /**
     * Return a list of affected projects for the current step. Could be null if step scope is not PROJECTS
     * @return List or null
     */
    List<Project> getProjects();
    
}
