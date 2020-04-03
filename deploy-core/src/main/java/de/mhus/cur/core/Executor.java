package de.mhus.cur.core;

public interface Executor {

    void execute(Conductor cur, String lifecycle) throws Exception;

}
