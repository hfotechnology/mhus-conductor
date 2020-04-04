package de.mhus.cur.api;

import java.util.LinkedList;

public interface Step {

    LinkedList<String> getParameters();

    Labels getSelector();

    String getOrder();

    boolean isOrderAsc();

    String getTarget();

    String getCondition();

	boolean matchCondition(Context context);

	String getTitle();
    
}
