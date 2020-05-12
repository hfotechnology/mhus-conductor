package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.MojoException;
import de.mhus.lib.core.console.Console;

@AMojo(name = "confirm",target = "confirm")
public class ConfirmMojo implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        if (context.getProperties().getBoolean(ConUtil.PROPERTY_Y, false)) return true;

        Console console = ConUtil.getConsole();

        String prompt = context.getStep().getProperties().getString("prompt", "");

        console.print(prompt);
        console.print(" (y/n)");
        console.flush();
        while (true) {
            int input = console.read();
            if (input == 'n')
                throw new MojoException(context, "Not Confirmed");
            if (input == 'y')
                return true;
        }
    }

}
