package de.mhus.cur.core;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.Plugin;

public class PluginImpl implements Plugin {

	protected String target;
	protected String uri;
	protected String mojo;
    protected Conductor cur;
    protected SCOPE scope = SCOPE.PROJECT;

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getMojo() {
        return mojo;
    }

    public void init(Conductor cur) {
        this.cur = cur;
    }

    @Override
    public String toString() {
        return target;
    }

	@Override
	public SCOPE getScope() {
		return scope;
	}

}
