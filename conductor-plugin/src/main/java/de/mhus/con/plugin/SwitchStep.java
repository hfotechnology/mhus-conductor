package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Step;
import de.mhus.con.core.ExecutorDefault;
import de.mhus.lib.core.MLog;

@AMojo(name = "switch",target = "switch")
public class SwitchStep extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        for (Step caze : context.getStep().getSubSteps()) {
            if (caze.matchCondition(context)) {
                log().d("Execute case",caze);
                ((ExecutorDefault)context.getExecutor()).execute(caze);
                return true;
            }
        }
        return false;
    }

}
