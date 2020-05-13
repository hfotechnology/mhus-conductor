package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Plugin.SCOPE;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MPeriod;
import de.mhus.lib.core.MThread;

@AMojo(name = "sleep",target = "sleep", scope = SCOPE.STEP)
public class SleepMojo extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        long interval = MPeriod.toMilliseconds(context.getProperties().getString("interval"), 0);
        MThread.sleep(interval);
        return true;
    }

}
