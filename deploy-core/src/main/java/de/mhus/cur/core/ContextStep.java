package de.mhus.cur.core;

import java.util.LinkedList;

import de.mhus.deploy.api.Labels;
import de.mhus.deploy.api.Step;

public class ContextStep implements Step {

	private ContextImpl context;
	private Step inst;
	private ContextLabels selector;
	LinkedList<String> parameters;
	
	public ContextStep(ContextImpl context, Step inst) {
		this.context = context;
		this.inst = inst;
	}

	@Override
	public LinkedList<String> getParameters() {
		if (parameters == null) {
			parameters = new LinkedList<>();
			inst.getParameters().forEach(v -> parameters.add( context.make(v) ));
		}
		return parameters;
	}

	@Override
	public Labels getSelector() {
		if (selector == null)
			selector = new ContextLabels(context, inst.getSelector());
		return selector;
	}

	@Override
	public String getOrder() {
		return context.make(inst.getOrder());
	}

	@Override
	public boolean isOrderAsc() {
		return inst.isOrderAsc();
	}

	@Override
	public String getTarget() {
		return inst.getTarget();
	}

}
