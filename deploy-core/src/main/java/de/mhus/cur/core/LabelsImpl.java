package de.mhus.cur.core;

import de.mhus.deploy.api.Labels;

public class LabelsImpl extends XCollection<String> implements Labels {

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

}
