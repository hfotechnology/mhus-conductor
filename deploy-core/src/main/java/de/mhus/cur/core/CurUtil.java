package de.mhus.cur.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.Project;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.logging.Log;

public class CurUtil {

	private static final Log log = Log.getLog(Conductor.class);
	public static final String PROPERTY_FAE = "_conductor.fae";
	public static final String PROPERTY_MVN_PATH = "_conductor.schema.mvn.path";
	
    public static void orderProjects(LinkedList<Project> projects, String order, boolean orderAsc) {
        
    }

	public static String[] execute(File rootDir, String cmd) throws IOException {
		
		log.d("execute",cmd);

		ProcessBuilder processBuilder = new ProcessBuilder();
		if (MSystem.isWindows())
			// Windows
			processBuilder.command("cmd.exe", "/c", cmd);
		else
			// Unix
			processBuilder.command("/bin/bash", "-c", cmd);
			
        try {

            Process process = processBuilder.start();

            BufferedReader outReader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder stdOutBuilder = new StringBuilder();
            
            String line;
            while ((line = outReader.readLine()) != null) {
                System.out.println("[OUT] " + line);
                stdOutBuilder.append(line).append("\n");
            }

            BufferedReader errReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder stdErrBuilder = new StringBuilder();
            while ((line = errReader.readLine()) != null) {
                System.out.println("[ERR] " + line);
                stdErrBuilder.append(line).append("\n");
            }

            int exitCode = process.waitFor();
    		String stderr = stdOutBuilder.toString();
    		String stdout = stdErrBuilder.toString();
    		log.d("result",stdout,stderr,exitCode);
    		return new String[] {stdout, stderr, String.valueOf(exitCode)};
            

        } catch (InterruptedException e) {
            throw new IOException(e);
        }
		
		
	}

}
