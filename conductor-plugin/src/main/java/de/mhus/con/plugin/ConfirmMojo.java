package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Plugin.SCOPE;
import de.mhus.con.api.StopLifecycleException;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.console.Console;
import de.mhus.lib.core.console.Console.COLOR;

@AMojo(name = "confirm",target = "confirm", scope = SCOPE.STEP)
public class ConfirmMojo implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        if (context.getProperties().getBoolean(ConUtil.PROPERTY_Y, false)) return true;

        Console console = ConUtil.getConsole();

        String msg = context.getStep().getProperties().getString("message", "");
        if (MString.isSet(msg)) {
            console.println();
            console.println(msg);
        }
        String prompt = context.getStep().getProperties().getString("prompt", "");

        console.setColor(COLOR.RED, null);
        console.print(prompt);
        console.cleanup();
        
        console.print(" (y/n) ");
        console.flush();
        while (true) {
            int input = console.read();
            if (input == 'n')
                throw new StopLifecycleException(context, "Not Confirmed",prompt);
            if (input == 'y')
                return true;
        }
    }

}
