package de.mhus.con.core;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Lifecycle;
import de.mhus.con.api.Lifecycles;

public class LifecyclesImpl extends XCollection<Lifecycle> implements Lifecycles {

    @SuppressWarnings("unused")
    private Conductor con;

    public void init(Conductor con) {
        this.con = con;
    }
	
}
