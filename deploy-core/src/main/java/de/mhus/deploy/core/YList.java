package de.mhus.deploy.core;

import java.util.LinkedList;
import java.util.List;

public class YList {

	private List<Object> list;

	@SuppressWarnings("unchecked")
	public YList(Object obj) {
		list = (List<Object>)obj;
	}
	
	public int size() {
		return list.size();
	}
	
	public String getString(int index) {
		Object ret = list.get(index);
		if (ret == null) return null;
		if (ret instanceof String) return (String) ret;
		return String.valueOf(ret);
	}
	
	public List<String> toStringList() {
		LinkedList<String> ret = new LinkedList<>();
		for (int i = 0; i < list.size(); i++)
			ret.add(getString(i));
		return ret;
	}

}
