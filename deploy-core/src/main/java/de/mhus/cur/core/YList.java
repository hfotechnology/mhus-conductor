package de.mhus.cur.core;

import java.util.LinkedList;
import java.util.List;

import de.mhus.lib.core.util.EmptyList;

public class YList {

	private List<Object> list;

	@SuppressWarnings("unchecked")
	public YList(Object obj) {
		list = (List<Object>)obj;
	}
	
	public int size() {
	    if (list == null) return 0;
		return list.size();
	}
	
	public String getString(int index) {
        if (list == null || index >= list.size()) return null;
		Object ret = list.get(index);
		if (ret == null) return null;
		if (ret instanceof String) return (String) ret;
		return String.valueOf(ret);
	}
	
    public YMap getMap(int index) {
        if (list == null || index >= list.size()) return null;
        Object ret = list.get(index);
        if (ret == null) return null;
        return new YMap(ret);
    }
    
	public List<String> toStringList() {
        if (list == null) return new EmptyList<>();
		LinkedList<String> ret = new LinkedList<>();
		for (int i = 0; i < list.size(); i++)
			ret.add(getString(i));
		return ret;
	}

    public List<YMap> toMapList() {
        if (list == null) return new EmptyList<>();
        LinkedList<YMap> ret = new LinkedList<>();
        for (int i = 0; i < list.size(); i++)
            ret.add(getMap(i));
        return ret;
    }
    
}
