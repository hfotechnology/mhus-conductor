package de.mhus.cur.core;

import java.util.LinkedList;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.Labels;
import de.mhus.deploy.api.Step;

public class StepImpl implements Step {

	protected LinkedList<String> parameters;
	protected LabelsImpl selector;
	protected String order;
	protected boolean orderAsc = true;
	protected String target;
    protected Conductor cur;

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
