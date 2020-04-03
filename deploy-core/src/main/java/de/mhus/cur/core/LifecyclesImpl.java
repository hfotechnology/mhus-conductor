package de.mhus.cur.core;

public class LifecyclesImpl extends XCollection<Lifecycle> implements Lifecycles {

    @SuppressWarnings("unused")
    private Conductor cur;

    public void init(Conductor cur) {
        this.cur = cur;
    }
	
}
