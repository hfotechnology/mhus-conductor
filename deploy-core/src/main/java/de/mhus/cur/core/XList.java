package de.mhus.cur.core;

import java.util.Iterator;
import java.util.LinkedList;

public class XList<T> implements IList<T>{

	protected LinkedList<T> list = new LinkedList<>();
	
	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public int size() {
		return list.size();
	}
	
	public void add(T entry) {
		list.add(entry);
	}

}
