package de.mhus.con.core;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Lifecycle;
import de.mhus.con.api.Steps;
import de.mhus.lib.core.MSystem;

public class LifecycleImpl implements Lifecycle {

    private Steps steps;
    private String name;
    @SuppressWarnings("unused")
    private Conductor con;
    
    public LifecycleImpl(String name, Steps steps) {
        this.name = name;
        this.steps = steps;
    }
    
    @Override
    public Steps getSteps() {
        return steps;
    }

    @Override
    public String getName() {
        return name;
    }

    public void init(Conductor con) {
        this.con = con;
    }

    @Override
    public String toString() {
        return MSystem.toString(this, name);
    }

}
