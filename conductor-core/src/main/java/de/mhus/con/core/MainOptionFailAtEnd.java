package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.MainOptionHandler;

@AOption(alias="-fae")
public class MainOptionFailAtEnd implements MainOptionHandler {

    @Override
    public void execute(Cli cli, String cmd, LinkedList<String> queue) {
        ((MainCli)cli).getOverlayProperties().put(ConUtil.PROPERTY_FAE, true);
    }

    @Override
    public String getUsage(String cmd) {
        return "";
    }

    @Override
    public String getDescription(String cmd) {
        return "Fail-At-End Option";
    }

}
