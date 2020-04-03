package de.mhus.cur.core;

import de.mhus.deploy.api.Plugin;

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

}
