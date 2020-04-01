package de.mhus.bwk.core;

import java.util.LinkedList;

public class StepImpl implements Step {

    private LinkedList<String> parameters;
    private LabelsImpl selector;
    private String order;
    private boolean orderAsc = true;
    private String target;
    @SuppressWarnings("unused")
    private Bwk bwk;

    public StepImpl(YMap map) {
        
        // target:
        target = map.getString("target");
        
        // parameters:
        YList parametersE = map.getList("parameters");
        parameters = new LinkedList<>();
        if (parametersE != null) {
            parametersE.toStringList().forEach(v -> parameters.add(v));
        }
        
        // selector:
        YMap selectorE = map.getMap("selector");
        if (selectorE != null)
            selector = new LabelsImpl(selectorE);
        else
            selector = new LabelsImpl();
        
        // order:
        order = map.getString("order");
        if (order != null) {
            order = order.trim();
            if (order.toLowerCase().endsWith(" asc")) {
                order = order.substring(0, order.length()-4);
                orderAsc  = true;
            } else if (order.toLowerCase().endsWith(" desc")) {
                order = order.substring(0, order.length()-5);
                orderAsc = false;
            }
        }
        
    }

    @Override
    public LinkedList<String> getParameters() {
        return parameters;
    }

    @Override
    public Labels getSelector() {
        return selector;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public boolean isOrderAsc() {
        return orderAsc;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public void init(Bwk bwk) {
        this.bwk = bwk;
    }

    @Override
    public String toString() {
        return target;
    }

}
