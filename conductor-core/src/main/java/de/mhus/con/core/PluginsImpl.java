package de.mhus.con.core;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Plugin;
import de.mhus.con.api.Plugins;

public class PluginsImpl extends XCollection<Plugin> implements Plugins {

    @SuppressWarnings("unused")
    private Conductor con;

    public void init(Conductor con) {
        this.con = con;
    }

}
