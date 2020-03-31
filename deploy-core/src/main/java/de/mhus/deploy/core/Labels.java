package de.mhus.deploy.core;

public interface Labels extends ICollection<String> {

	boolean matches(Labels selector);

}
