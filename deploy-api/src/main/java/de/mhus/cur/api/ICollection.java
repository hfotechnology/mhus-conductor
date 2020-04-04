package de.mhus.cur.api;

public interface ICollection<T> extends Iterable<T> {

	T get(String name);
	
	String[] keys();

	T getOrNull(String name);
	
	int size();
}
