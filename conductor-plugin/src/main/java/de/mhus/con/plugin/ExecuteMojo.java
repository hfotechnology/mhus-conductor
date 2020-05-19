/*#man mojo
 *#title: Execute Sub Steps

For each project execute the sub steps in a row.
 
IMPORTANT: You can't use scope:step plugins as sub steps.

 */
package de.mhus.con.plugin;

import java.io.Closeable;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Step;
import de.mhus.con.core.ContextStep;
import de.mhus.con.core.ExecutorDefault;
import de.mhus.lib.core.MLog;

@AMojo(name = "execute",target = "execute")
public class ExecuteMojo extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        boolean done = false;
        try ( Closeable x = ((ExecutorDefault)context.getExecutor()).enterSubSteps(context.getStep()) ) {
            for (Step caze : context.getStep().getSubSteps()) {
                done = true;
                ((ExecutorDefault)context.getExecutor()).executeInternal( ((ContextStep)caze).getInstance(), context.getProject() );
            }
        }
        return done;
    }

}
