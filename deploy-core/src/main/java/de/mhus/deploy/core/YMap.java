package de.mhus.deploy.core;

import java.util.Map;

public class YMap {

	private Map<String, Object> map;

	@SuppressWarnings("unchecked")
	public YMap(Object obj) {
		map = (Map<String, Object>)obj;
	}

	public YMap getMap(String key) {
		return new YMap(map.get(key));
	}
	
	public YList getList(String key) {
		return new YList(map.get(key));
	}
	
	public String getString(String key) {
		Object val = map.get(key);
		if (val == null) return null;
		if (val instanceof String) return (String) val;
		return String.valueOf(val);
	}
	
}
