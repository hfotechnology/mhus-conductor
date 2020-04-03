package de.mhus.cur.core;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.Lifecycle;
import de.mhus.deploy.api.Lifecycles;

public class LifecyclesImpl extends XCollection<Lifecycle> implements Lifecycles {

    @SuppressWarnings("unused")
    private Conductor cur;

    public void init(Conductor cur) {
        this.cur = cur;
    }
	
}
