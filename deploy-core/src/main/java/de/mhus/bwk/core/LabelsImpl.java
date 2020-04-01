package de.mhus.bwk.core;

public class LabelsImpl extends XCollection<String> implements Labels {

    public LabelsImpl() {
        
    }
	public LabelsImpl(YMap map) {
	    for (String key : map.getKeys())
	        put(key, map.getString(key));
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

}
