package de.mhus.con.api;

public interface ErrorInfo {

    Context getContext();
    
    Throwable getError();

}
