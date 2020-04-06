package de.mhus.con.api;

public interface Executor {

    void execute(Conductor con, String lifecycle) throws Exception;

}
