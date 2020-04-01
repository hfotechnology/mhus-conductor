package de.mhus.bwk.core;

import de.mhus.lib.core.util.MUri;

public class PluginImpl implements Plugin {

    private String target;
    private String url;
    private String mojo;
    @SuppressWarnings("unused")
    private Bwk bwk;

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

    public void init(Bwk bwk) {
        this.bwk = bwk;
    }

    @Override
    public String toString() {
        return target;
    }

}
