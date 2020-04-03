package de.mhus.cur.core;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.Plugin;
import de.mhus.deploy.api.Plugins;

public class PluginsImpl extends XCollection<Plugin> implements Plugins {

    @SuppressWarnings("unused")
    private Conductor cur;

    public void init(Conductor cur) {
        this.cur = cur;
    }

}
