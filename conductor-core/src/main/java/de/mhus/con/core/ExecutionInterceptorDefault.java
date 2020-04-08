package de.mhus.con.core;

import java.util.LinkedList;
import java.util.List;

import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Context;
import de.mhus.con.api.ErrorInfo;
import de.mhus.con.api.ExecutionInterceptorPlugin;
import de.mhus.con.api.Project;
import de.mhus.con.api.Project.STATUS;
import de.mhus.con.api.Step;
import de.mhus.con.api.Steps;
import de.mhus.lib.core.MDate;
import de.mhus.lib.core.MStopWatch;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.console.Console;
import de.mhus.lib.core.console.Console.COLOR;

public class ExecutionInterceptorDefault implements ExecutionInterceptorPlugin {

    private LinkedList<Result> stepResults;
    private MStopWatch watch;

    @Override
    public void executeBegin(Context context) {
        String step = context.getStep().getTitle();
        Console console = ConUtil.getConsole();
        console.flush();
        console.println();
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.setBold(false);
        console.print("[");
        console.print(context.getExecutor().getCurrentStepCount());
        console.print("/");
        console.print(context.getExecutor().getLifecycle().getSteps().size());
        console.print("] ");
        console.setColor(COLOR.RED, null);
        console.print(step);
        if (context.getProject() != null) {
            console.setColor(COLOR.BRIGHT_BLACK, null);
            console.print(" >>> ");
            console.setColor(COLOR.GREEN, null);
            console.print(context.getProject().getName());
        }
        console.println();
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.println();
        console.setBold(false);
        console.flush();

    }

    @Override
    public void executeError(Context context, Throwable t) {
        if (context.getProject() != null)
            stepResults.add(new Result(STATUS.FAILURE,context));
    }

    @Override
    public void executeEnd(Context context) {
        if (context.getProject() != null)
            stepResults.add(new Result(STATUS.SUCCESS,context));
    }

    @Override
    public void executeEnd(Conductor con, String lifecycle, Steps steps, List<ErrorInfo> errors) {
        watch.stop();
        boolean isError = false;
        Console console = ConUtil.getConsole();
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.print("  ");
        console.println(lifecycle);
        console.println();
        console.setBold(false);
        for (Project p : con.getProjects()) {
            String name = p.getName() + " ";
            console.print("  ");
            console.print(name);
            console.print(MString.rep('.', 60 - name.length()));
            console.print(" ");
            switch (p.getStatus()) {
            case FAILURE:
                console.setColor(COLOR.RED, null);
                isError = true;
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
            for (Result result : stepResults) {
                if (result.project.getName().equals(p.getName())) {
                    String stepTitle = result.step.getTitle();
                    console.print("    ");
                    console.setColor(COLOR.BRIGHT_BLACK, null);
                    console.print(stepTitle);
                    console.print(" ");
                    console.print(MString.rep('.', 57 - stepTitle.length()));
                    console.print(" ");
                    switch (result.status) {
                    case FAILURE:
                        console.setColor(COLOR.RED, null);
                        isError = true;
                        break;
                    case SUCCESS:
                        console.setColor(COLOR.GREEN, null);
                        break;
                    default:
                        break;
                    }
                    console.println(result.status);
                    console.cleanup();
                }
            }
        }
        console.println();
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
                console.println("  BUILD FAILED");
            } else {
                console.setColor(COLOR.GREEN, null);
                console.println("  BUILD SUCCESS");
            }
        }
        console.cleanup();
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.setBold(false);
        console.print("    Total Time: ");
        console.println(watch.getCurrentTimeAsString());
        console.print("    Finished at: ");
        console.println(MDate.toIsoDateTime(watch.getStop()));
        console.setBold(true);
        console.println("------------------------------------------------------------------------");
        console.setBold(false);
    }

    @Override
    public void executeBegin(Conductor con, String lifecycle, Steps steps) {
        stepResults = new LinkedList<>();
        watch = new MStopWatch().start();
    }

    static class Result {

        private Step step;
        private Project project;
        private STATUS status;

        public Result(Project.STATUS status, Context context) {
            this.step = context.getStep();
            this.project = context.getProject();
            this.status = status;
        }
        
    }
}
