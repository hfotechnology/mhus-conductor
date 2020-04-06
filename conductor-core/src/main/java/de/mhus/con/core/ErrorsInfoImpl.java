package de.mhus.con.core;

import de.mhus.con.api.Context;
import de.mhus.con.api.ErrorInfo;

public class ErrorsInfoImpl implements ErrorInfo {

	private ContextImpl context;
    private Throwable error;

    public ErrorsInfoImpl(ContextImpl context, Throwable t) {
	    this.context = context;
	    this.error = t;
	}

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Throwable getError() {
        return error;
    }

}
