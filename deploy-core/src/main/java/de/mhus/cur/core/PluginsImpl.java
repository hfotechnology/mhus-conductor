package de.mhus.cur.core;

public class PluginsImpl extends XCollection<Plugin> implements Plugins {

    @SuppressWarnings("unused")
    private Conductor cur;

    public void init(Conductor cur) {
        this.cur = cur;
    }

}
