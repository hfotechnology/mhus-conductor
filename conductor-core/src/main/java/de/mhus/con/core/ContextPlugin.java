package de.mhus.con.core;

import de.mhus.con.api.Plugin;
import de.mhus.con.api.Project;

public class ContextPlugin implements Plugin {

	private ContextImpl context;
	private Plugin inst;

	public ContextPlugin(ContextImpl context, Plugin inst) {
		this.context = context;
		this.inst = inst;
	}

	@Override
	public String getTarget() {
		return inst.getTarget();
	}

	@Override
	public String getUri() {
		return context.make(inst.getUri());
	}

	@Override
	public String getMojo() {
		return context.make(inst.getMojo());
	}

	@Override
	public SCOPE getScope() {
		return inst.getScope();
	}

    @Override
	public String toString() {
		return inst.toString();
	}

    public Plugin getInstance() {
        return inst;
    }

}
