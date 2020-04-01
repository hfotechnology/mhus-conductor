package de.mhus.bwk.core;

public class LifecycleImpl implements Lifecycle {

    private Steps steps;
    private String name;
    @SuppressWarnings("unused")
    private Bwk bwk;
    
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

    public void init(Bwk bwk) {
        this.bwk = bwk;
    }

    @Override
    public String toString() {
        return name;
    }

}
