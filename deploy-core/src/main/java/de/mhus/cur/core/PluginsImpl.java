package de.mhus.cur.core;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.Plugin;
import de.mhus.cur.api.Plugins;

public class PluginsImpl extends XCollection<Plugin> implements Plugins {

    @SuppressWarnings("unused")
    private Conductor cur;

    public void init(Conductor cur) {
        this.cur = cur;
    }

}
