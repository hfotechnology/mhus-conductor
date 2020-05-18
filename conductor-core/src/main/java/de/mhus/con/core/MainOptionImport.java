package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;

@AOption(alias = "-i")
public class MainOptionImport implements MainOptionHandler {

    @Override
    public void execute(Cli cli, String cmd, LinkedList<String> queue) {
        String incl = queue.removeFirst();
        if (incl.indexOf(':') < 0)
            incl = "default:" + incl;
        ((MainCli)cli).defaultImports.add(incl);
    }

    @Override
    public String getUsage(String cmd) {
        return "<import>";
    }

    @Override
    public String getDescription(String cmd) {
        return "Import additional configuration";
    }

}
