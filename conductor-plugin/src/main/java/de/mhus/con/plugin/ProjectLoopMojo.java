package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Step;
import de.mhus.con.core.ContextStep;
import de.mhus.con.core.ExecutorDefault;
import de.mhus.lib.core.MLog;

@AMojo(name = "loopProjects",target = "loop")
public class ProjectLoopMojo extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        boolean done = false;
        while (context.getStep().matchCondition(context)) {
            done = true;
            for (Step caze : context.getStep().getSubSteps()) {
                if (caze.matchCondition(context)) {
                    log().d("Execute case",caze);
                    ((ExecutorDefault)context.getExecutor()).execute( ((ContextStep)caze).getInstance() );
                }
            }
        }
        return done;
    }

}
