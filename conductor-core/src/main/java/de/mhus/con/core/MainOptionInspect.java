package de.mhus.con.core;

import java.util.LinkedList;
import java.util.Map.Entry;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.ConfigType;
import de.mhus.con.api.MainOptionHandler;
import de.mhus.con.api.Project;
import de.mhus.con.api.Scheme;
import de.mhus.con.api.Validator;

@AOption(alias = "--inspect")
public class MainOptionInspect implements MainOptionHandler {

    @Override
    public void execute(Cli cli, String cmd, LinkedList<String> queue) {
        
        String what = "projects";
        if (!queue.isEmpty())
            what = queue.removeFirst();
        
        if (what.equals("cli"))
            inspectCli(cli);
        else if (what.equals("projects"))
            inspectProjects(cli);
    }

    private void inspectProjects(Cli cli) {
        Conductor con = cli.getConductor();
        for (Project project : con.getProjects()) {
            System.out.println(">>> " + project.getName());
            System.out.println("    Directory: " + project.getRootDir());
            System.out.println("    Path      : " + project.getPath());
            System.out.println("    Labels    : " + project.getLabels());
            System.out.println("    Properties: " + project.getProperties());
        }
    }

    private void inspectCli(Cli cli) {
        System.out.println("Schemes:");
        for (Entry<String, Scheme> entry : cli.getSchemes().entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue().getClass().getCanonicalName());
        
        System.out.println();
        System.out.println("Config Types:");
        for (Entry<String, ConfigType> entry : cli.getConfigTypes().entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue().getClass().getCanonicalName());
         
        System.out.println();
        System.out.println("Validators:");
        for (Entry<String, Validator> entry : cli.getValidators().entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue().getClass().getCanonicalName());

        System.out.println();
        System.out.println("Options:");
        for (Entry<String, MainOptionHandler> entry : cli.getOptions().entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue().getClass().getCanonicalName());
        
    }

    @Override
    public String getUsage(String cmd) {
        return "[projects|cli]";
    }

    @Override
    public String getDescription(String cmd) {
        return "Inspect current conductor configuration";
    }

}
