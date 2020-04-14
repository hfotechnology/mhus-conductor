package de.mhus.con.api;

import java.util.List;

public interface ExecutionInterceptorPlugin extends ConductorPlugin {

    void executeBegin(Context context);

    void executeError(Context context, Throwable t);

    void executeEnd(Context context, boolean done);

    void executeEnd(Conductor con, String lifecycle, Steps steps, List<ErrorInfo> errors);

    void executeBegin(Conductor con, String lifecycle, Steps steps);

}
