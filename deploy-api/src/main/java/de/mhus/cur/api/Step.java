package de.mhus.cur.api;

import java.util.LinkedList;

import de.mhus.lib.core.IProperties;

public interface Step {

    LinkedList<String> getArguments();

    Labels getSelector();

    String getOrder();

    boolean isOrderAsc();

    String getTarget();

    String getCondition();

	boolean matchCondition(Context context);

	String getTitle();

	IProperties getProperties();
    
}
