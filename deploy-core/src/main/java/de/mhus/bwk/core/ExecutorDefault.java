package de.mhus.bwk.core;

import java.util.LinkedList;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MString;

public class ExecutorDefault implements Executor {

    private Bwk bwk;

    @Override
    public void execute(Bwk bwk, String lifecycle) {
        this.bwk = bwk;
        
        Lifecycle lf = bwk.getLifecycles().get(lifecycle);

        Steps steps = lf.getSteps();
        execute(steps);
        
    }

    protected void execute(Steps steps) {
        for (Step step : steps)
            execute(step);
    }

    protected void execute(Step step) {
        
        // select projects
        Labels selector = step.getSelector();
        LinkedList<Project> projects = null;
        if (selector != null) {
            projects = new LinkedList<>(bwk.getProjects().select(selector));
        } else {
            projects = new LinkedList<>(bwk.getProjects().getAll());
        }
        
        // order
        String order = step.getOrder();
        if (MString.isSet(order)) {
            BwkUtil.orderProjects(projects, order, step.isOrderAsc());
        }

        execute(step, projects);
        
    }

    protected void execute(Step step, LinkedList<Project> projects) {
        for (Project project : projects)
            execute(step, project);
    }

    protected void execute(Step step, Project project) {
        
        IReadProperties additional = new MProperties();
        
        Context context = new ContextImpl(bwk, additional);
        
        
    }

}
