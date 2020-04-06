package de.mhus.con.core;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Plugin;
import de.mhus.lib.core.MSystem;

public class PluginImpl implements Plugin {

	protected String target;
	protected String uri;
	protected String mojo;
    protected Conductor con;
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

    public void init(Conductor con) {
        this.con = con;
    }

	@Override
	public SCOPE getScope() {
		return scope;
	}

    @Override
	public String toString() {
		return MSystem.toString(this, target);
	}

}
