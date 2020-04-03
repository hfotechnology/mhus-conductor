package de.mhus.cur.core;

import java.util.LinkedList;

import de.mhus.lib.errors.MRuntimeException;

public class StepImpl implements Step {

    private LinkedList<String> parameters;
    private LabelsImpl selector;
    private String order;
    private boolean orderAsc = true;
    private String target;
    @SuppressWarnings("unused")
    private Conductor cur;

    public StepImpl(YMap map) {
        
        // target:
        target = map.getString("target");
        
        try {
            // parameters:
            parameters = new LinkedList<>();
            if (map.isString("parameters")) {
                parameters.add(map.getString("parameters"));
            } else
            if (map.isList("parameters")) {
                YList parametersE = map.getList("parameters");
                if (parametersE != null) {
                    parametersE.toStringList().forEach(v -> parameters.add(v));
                }
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
        } catch (Throwable t) {
            throw new MRuntimeException("step",target,t);
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

    public void init(Conductor cur) {
        this.cur = cur;
    }

    @Override
    public String toString() {
        return target;
    }

}
