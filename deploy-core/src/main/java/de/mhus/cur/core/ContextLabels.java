package de.mhus.cur.core;

import java.util.Iterator;

import de.mhus.cur.api.Labels;

public class ContextLabels implements Labels {

	private ContextImpl context;
	private Labels inst;

	public ContextLabels(ContextImpl context, Labels inst) {
		this.context = context;
		this.inst = inst;
	}

	@Override
	public String get(String name) {
		return context.make(inst.get(name));
	}

	@Override
	public String[] keys() {
		return inst.keys();
	}

	@Override
	public String getOrNull(String name) {
		return context.make(inst.getOrNull(name));
	}

	@Override
	public int size() {
		return inst.size();
	}

	@Override
	public Iterator<String> iterator() {
		return inst.iterator();
	}

    @Override
	public boolean matches(Labels selector) {
		for (String sKey : selector.keys()) {
			String sValue = selector.get(sKey);
			String lValue = getOrNull(sKey);
			if (lValue == null) return false;
			if (!lValue.matches(sValue)) return false;
		}
		return true;
	}

    @Override
	public String toString() {
		return inst.toString();
	}

}
