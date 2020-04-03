package de.mhus.cur.core;

public interface Labels extends ICollection<String> {

	boolean matches(Labels selector);

}
