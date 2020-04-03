package de.mhus.cur.core;

public class PluginImpl implements Plugin {

	protected String target;
	protected String url;
	protected String mojo;
    protected Conductor cur;

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public String getUrl() {
        return url;
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

}
