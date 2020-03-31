package de.mhus.deploy.core;

public interface ICollection<T> {

	T get(String name);
	
	String[] keys();

	T getOrNull(String name);
	
}
