package de.mhus.cur.api;

public interface Labels extends ICollection<String> {

	boolean matches(Labels selector);

}
