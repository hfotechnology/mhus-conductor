package de.mhus.con.plugin;

import java.io.Closeable;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.MojoException;
import de.mhus.con.api.Step;
import de.mhus.con.core.ContextStep;
import de.mhus.con.core.ExecutorImpl;
import de.mhus.lib.core.MCast;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MString;

@AMojo(name = "loopProjects",target = "loop")
public class ProjectLoopMojo extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        boolean done = false;
        
        String range = context.getStep().getProperties().getString("range",null);
        if (range != null) {
            double start = MCast.todouble(MString.beforeIndex(range, '-').trim(), 0);
            double end   = MCast.todouble(MString.afterIndex(range, '-').trim(), 0);
            double step = context.getStep().getProperties().getDouble("step",1);
            if (step == 0) throw new MojoException(context, "step is zero");
            String indexName = context.getStep().getProperties().getString("index","index");
            
            for (double d = start; start < end ? d < end : d > end; d = d + step ) {
                ((MProperties)context.getConductor().getProperties()).setDouble(indexName, d);
                done = true;
                try ( Closeable x = ((ExecutorImpl)context.getExecutor()).enterSubSteps(context.getStep()) ) {
                    for (Step caze : context.getStep().getSubSteps()) {
                        ((ExecutorImpl)context.getExecutor()).executeInternal( ((ContextStep)caze).getInstance(), context.getProject() );
                    }
                }
            }
        } else {
            while (context.getStep().matchCondition(context)) {
                done = true;
                for (Step caze : context.getStep().getSubSteps()) {
                    ((ExecutorImpl)context.getExecutor()).execute( ((ContextStep)caze).getInstance() );
                }
            }
        }
        return done;
    }

}
