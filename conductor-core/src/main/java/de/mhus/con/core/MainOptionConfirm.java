package de.mhus.con.core;

import java.util.LinkedList;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.MainOptionHandler;
import de.mhus.lib.core.MProperties;

@AOption(alias = {"-CS","-CCMD","-CC","-CBEEP"})
public class MainOptionConfirm implements MainOptionHandler {

    @Override
    public void execute(Cli cli, String cmd, LinkedList<String> queue) {
        if (cmd.equals("-CS") || cmd.equals("-CC")) {
            ((MainCli) cli).getOverlayProperties().setBoolean(ConUtil.PROPERTY_CONFIRM_STEPS, cmd.equals("-CS"));
            if (((MainCli)cli).isConductor())
                ((MProperties)cli.getConductor().getProperties()).setBoolean(ConUtil.PROPERTY_CONFIRM_STEPS, cmd.equals("-CS"));
        }
        if (cmd.equals("-CCMD") || cmd.equals("-CC")) {
            ((MainCli) cli).getOverlayProperties().setBoolean(ConUtil.PROPERTY_CONFIRM_CMDS, cmd.equals("-CCMD"));
            if (((MainCli)cli).isConductor())
                ((MProperties)cli.getConductor().getProperties()).setBoolean(ConUtil.PROPERTY_CONFIRM_CMDS, cmd.equals("-CCMD"));
        }
        if (cmd.equals("-CBEEP") || cmd.equals("-CC")) {
            ((MainCli) cli).getOverlayProperties().setBoolean(ConUtil.PROPERTY_CONFIRM_BEEP, cmd.equals("-CBEEP"));
            if (((MainCli)cli).isConductor())
                ((MProperties)cli.getConductor().getProperties()).setBoolean(ConUtil.PROPERTY_CONFIRM_BEEP, cmd.equals("-CBEEP"));
        }
    }

    @Override
    public String getUsage(String cmd) {
        return "";
    }

    @Override
    public String getDescription(String cmd) {
        if (cmd.equals("-CS"))
            return "Confirm each step before execution";
        if (cmd.equals("-CCMD"))
            return "Confirm each command before execution";
        if (cmd.equals("-CC"))
            return "Clear Confirm options";
        return null;
    }

}
