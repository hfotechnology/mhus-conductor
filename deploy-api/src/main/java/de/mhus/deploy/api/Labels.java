package de.mhus.deploy.api;

public interface Labels extends ICollection<String> {

	boolean matches(Labels selector);

}
