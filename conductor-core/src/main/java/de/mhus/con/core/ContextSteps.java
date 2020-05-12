package de.mhus.con.core;

import java.util.ArrayList;
import java.util.Iterator;

import de.mhus.con.api.Step;
import de.mhus.con.api.Steps;

public class ContextSteps implements Steps {

    private ArrayList<Step> list;

    public ContextSteps(ContextImpl context, Steps inst) {
        list = new ArrayList<>(inst.size());
        inst.forEach(i -> list.add(new ContextStep(context, i)));
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<Step> iterator() {
        return list.iterator();
    }

}
