package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.StopLifecycleException;
import de.mhus.con.api.Plugin.SCOPE;

@AMojo(name = "failed", target="failed",scope=SCOPE.STEP)
public class FailedMojo implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        String reason = context.getProperties().getString("reason", "");
        throw new StopLifecycleException(context, reason);
    }

}
