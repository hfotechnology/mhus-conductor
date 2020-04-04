package de.mhus.cur.core;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.Lifecycle;
import de.mhus.cur.api.Steps;
import de.mhus.lib.core.MSystem;

public class LifecycleImpl implements Lifecycle {

    private Steps steps;
    private String name;
    @SuppressWarnings("unused")
    private Conductor cur;
    
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

    public void init(Conductor cur) {
        this.cur = cur;
    }

    @Override
    public String toString() {
        return MSystem.toString(this, name);
    }

}
