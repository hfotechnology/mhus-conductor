package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.lib.core.MLog;

public abstract class AbstractExecute extends MLog implements ExecutePlugin {

    @Override
    public final void execute(Context context) throws Exception {
        execute2(context);
        if (context.getProject() != null && context.getProperties().getBoolean("touch", false)) {
            File root = context.getProject().getRootDir();
            long time = System.currentTimeMillis();
            log().d("Touch project folder",root,time);
            root.setLastModified(time);
        }
    }

    public abstract void execute2(Context context) throws Exception;
}
