package de.mhus.con.core;

import java.util.HashMap;
import java.util.Iterator;

import de.mhus.con.api.ICollection;
import de.mhus.lib.errors.MRuntimeException;

public class XCollection<T> implements ICollection<T> {

	protected HashMap<String, T> collection = new HashMap<>();

	@Override
	public T getOrNull(String name) {
		return collection.get(name);
	}
	
	@Override
	public T get(String name) {
		T ret = collection.get(name);
		if (ret == null) throw new MRuntimeException(getClass().getSimpleName(),"not found",name);
		return ret;
	}

	@Override
	public String[] keys() {
		return collection.keySet().toArray(new String[0]);
	}
	
	public void put(String name, T entry) {
		collection.put(name, entry);
	}

    public void remove(String name) {
        collection.remove(name);
    }
    
    @Override
    public Iterator<T> iterator() {
        return collection.values().iterator();
    }

    @Override
    public String toString() {
        return collection.toString();
    }
    
    @Override
    public int size() {
        return collection.size();
    }

}
