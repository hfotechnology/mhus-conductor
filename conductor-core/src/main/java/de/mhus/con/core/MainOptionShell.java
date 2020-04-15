package de.mhus.con.core;

import java.util.LinkedList;
import java.util.Scanner;

import de.mhus.con.api.AOption;
import de.mhus.con.api.Cli;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.MainOptionHandler;
import de.mhus.lib.core.MString;

@AOption(alias="--shell")
public class MainOptionShell implements MainOptionHandler {

	@Override
	public void execute(Cli cli, String cmd, LinkedList<String> queue) {
		Conductor con = ((MainCli)cli).getConductor();
		ExecutorDefault exec = new ExecutorDefault();
		exec.setConductor(con);

		Scanner input = new Scanner(System.in);
		System.out.print("> ");
        while (input.hasNext()) {
            String line = input.nextLine();
            if (line.equals("quit"))
            	break;
            if (line.startsWith("lifecycle")) {
            	String lifecycle = MString.afterIndex(line, ' ').trim();
            	exec.execute(con, lifecycle);
            } else
            if (line.equals("step:")) {
        		System.out.print("# ");
        		StringBuilder stepDef = new StringBuilder();
            	while (input.hasNext()) {
                    String line2 = input.nextLine();
            		if (line2.equals("-"))
            			break;
            		stepDef.append(line2);
            		System.out.print("# ");
            	}
            	System.out.println("Execute: " + stepDef);
            } else {
            	String[] parts = line.split(" ");
            	MainOptionHandler option = ((MainCli)cli).getOptions().get(parts[0]);
            	if (option != null) {
            		LinkedList<String> q = new LinkedList<>();
            		for (int i = 1; i < parts.length; i++)
            			q.add(parts[i]);
            		option.execute(cli, parts[0], q);
            	}
            }
    		System.out.print("> ");
        }
        input.close();
	}

	@Override
	public String getUsage(String cmd) {
		return null;
	}

	@Override
	public String getDescription(String cmd) {
		return "execute shell input";
	}

}
