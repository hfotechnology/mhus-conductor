package de.mhus.cur.core;

public class PluginImpl implements Plugin {

    private String target;
    private String url;
    private String mojo;
    @SuppressWarnings("unused")
    private Conductor cur;

    public PluginImpl(YMap map, Plugin merge) {
        target = map.getString("target");
        url = map.getString("url", merge == null ? null : merge.getUrl());
        mojo = map.getString("mojo", merge == null ? null : merge.getMojo());
    }

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
