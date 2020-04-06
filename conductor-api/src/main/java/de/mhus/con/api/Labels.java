package de.mhus.con.api;

public interface Labels extends ICollection<String> {

	boolean matches(Labels selector);

}
