package de.mhus.cur.api;

public interface Executor {

    void execute(Conductor cur, String lifecycle) throws Exception;

}
