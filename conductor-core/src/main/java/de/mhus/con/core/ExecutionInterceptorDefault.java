package de.mhus.con.core;

import java.util.List;

import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Context;
import de.mhus.con.api.ErrorInfo;
import de.mhus.con.api.ExecutionInterceptorPlugin;
import de.mhus.con.api.Project;
import de.mhus.con.api.Steps;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.console.Console;
import de.mhus.lib.core.console.Console.COLOR;

public class ExecutionInterceptorDefault implements ExecutionInterceptorPlugin {

    @Override
    public void executeBegin(Context context) {

    }

    @Override
    public void executeError(Context context, Throwable t) {

    }

    @Override
    public void executeEnd(Context context) {

    }

    @Override
    public void executeEnd(Conductor con, String lifecycle, Steps steps, List<ErrorInfo> errors) {
        boolean isError = false;
        Console console = ConUtil.getConsole();
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.println(lifecycle);
        console.println("");
        console.setBold(false);
        for (Project p : con.getProjects()) {
            String name = p.getName() + " ";
            console.print(name);
            console.print(MString.rep('.', 60 - name.length()));
            console.print(" ");
            switch (p.getStatus()) {
            case FAILURE:
                console.setColor(COLOR.RED, null);
                isError = true;
                break;
            case NONE:
                console.setColor(COLOR.WHITE, null);
                break;
            case SKIPPED:
                console.setColor(COLOR.YELLOW, null);
                break;
            case SUCCESS:
                console.setColor(COLOR.GREEN, null);
                break;
            default:
                break;
            }
            console.println(p.getStatus());
            console.cleanup();
        }
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.setBold(false);
        if (!errors.isEmpty()) {
            for (ErrorInfo error : errors) {
                console.setColor(COLOR.RED, null);
                System.out.println("ERROR");
                System.out.println("    Step   : " + error.getContext().getStep());
                if (error.getContext().getProject() != null)
                    System.out.println("    Project: " + error.getContext().getProject());
                System.out.println("    Plugin : " + error.getContext().getPlugin().getTarget());
                if (error.getError() != null) {
                    console.setColor(COLOR.YELLOW, null);
                    System.out.println("Error:");
                    error.getError().printStackTrace();
                }
            }
        } else {
            if (isError) {
                console.setColor(COLOR.RED, null);
                console.println("BUILD FAILED");
            } else {
                console.setColor(COLOR.GREEN, null);
                console.println("BUILD SUCCESS");
            }
        }
        console.cleanup();
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.setBold(false);
    }

    @Override
    public void executeBegin(Conductor con, String lifecycle, Steps steps) {
    }

}
