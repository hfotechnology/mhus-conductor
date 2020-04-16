package de.mhus.con.core;

import java.io.File;
import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.MainOptionHandler;

@AOption(alias= {"-d"})
public class MainOptionDirectory implements MainOptionHandler {

    @Override
    public void execute(Cli cli, String cmd, LinkedList<String> queue) {
        String path = queue.removeFirst();
        ((MainCli)cli).rootDir = new File(path);
    }

    @Override
    public String getUsage(String cmd) {
        return "<directory>";
    }

    @Override
    public String getDescription(String cmd) {
        return "Set execution root directory";
    }

}
