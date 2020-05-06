package de.mhus.con.core;

import de.mhus.con.api.Labels;

public class LabelsImpl extends XCollection<String> implements Labels {

    @Override
	public boolean matches(Labels selector) {
		for (String sKey : selector.keys()) {
			String sValue = selector.get(sKey);
			String lValue = getOrNull(sKey);
			if (lValue == null) {
			    log().t(sKey, "not found in project");
			    return false;
			}
			if (!lValue.equals(sValue) && !lValue.matches(sValue)) {
			    log().t(lValue,"not matches",sValue);
			    return false;
			}
		}
		return true;
	}

}
