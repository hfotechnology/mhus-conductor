package de.mhus.deploy.api;

import java.util.LinkedList;

public interface Step {

    LinkedList<String> getParameters();

    Labels getSelector();

    String getOrder();

    boolean isOrderAsc();

    String getTarget();

}
