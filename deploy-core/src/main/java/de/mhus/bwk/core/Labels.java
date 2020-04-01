package de.mhus.bwk.core;

public interface Labels extends ICollection<String> {

	boolean matches(Labels selector);

}
