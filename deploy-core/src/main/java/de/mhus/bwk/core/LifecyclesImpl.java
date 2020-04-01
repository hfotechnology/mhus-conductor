package de.mhus.bwk.core;

public class LifecyclesImpl extends XCollection<Lifecycle> implements Lifecycles {

    @SuppressWarnings("unused")
    private Bwk bwk;

    public void init(Bwk bwk) {
        this.bwk = bwk;
    }
	
}
