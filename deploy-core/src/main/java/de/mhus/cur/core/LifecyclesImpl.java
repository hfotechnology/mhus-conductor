package de.mhus.cur.core;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.Lifecycle;
import de.mhus.cur.api.Lifecycles;

public class LifecyclesImpl extends XCollection<Lifecycle> implements Lifecycles {

    @SuppressWarnings("unused")
    private Conductor cur;

    public void init(Conductor cur) {
        this.cur = cur;
    }
	
}
