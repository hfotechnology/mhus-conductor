package de.mhus.bwk.core;

public class PluginsImpl extends XCollection<Plugin> implements Plugins {

    @SuppressWarnings("unused")
    private Bwk bwk;

    public void init(Bwk bwk) {
        this.bwk = bwk;
    }

}
