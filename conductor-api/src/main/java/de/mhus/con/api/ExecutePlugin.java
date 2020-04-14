package de.mhus.con.api;

public interface ExecutePlugin extends ConductorPlugin {

	boolean execute(Context context) throws Exception;

}
