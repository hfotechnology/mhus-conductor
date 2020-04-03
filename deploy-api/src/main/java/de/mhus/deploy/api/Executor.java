package de.mhus.deploy.api;

public interface Executor {

    void execute(Conductor cur, String lifecycle) throws Exception;

}
