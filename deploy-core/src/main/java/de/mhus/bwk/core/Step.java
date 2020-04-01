package de.mhus.bwk.core;

import java.util.LinkedList;

public interface Step {

    LinkedList<String> getParameters();

    Labels getSelector();

    String getOrder();

    boolean isOrderAsc();

    String getTarget();

}
