package de.mhus.con.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import de.mhus.conductor.api.meta.Version;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.logging.Log;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

public class ConUtil {

	private static final Log log = Log.getLog(Conductor.class);
	public static final String PROPERTY_FAE = "_conductor.fae";
	public static final String PROPERTY_CMD_PATH = "_conductor.cmd.";
	public static final String PROPERTY_PATH = "_conductor.path";
	public static final String DEFAULT_PATHES_UNIX = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin";
	public static final String DEFAULT_PATHES_WINDOWS = "C:\\Program Files;C:\\Winnt;C:\\Winnt\\System32";
	
    public static void orderProjects(LinkedList<Project> projects, String order, boolean orderAsc) {
        
    }

	public static String[] execute(String name, File rootDir, String cmd) throws IOException {
		
		log.i(name,"execute",cmd);

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
                System.out.println("["+name+"]" + line);
                if (stdOutBuilder.length() > 0) 
                	stdOutBuilder.append("\n");
                stdOutBuilder.append(line);
            }

            BufferedReader errReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder stdErrBuilder = new StringBuilder();
            while ((line = errReader.readLine()) != null) {
                System.err.println("["+name+"] " + line);
                if (stdErrBuilder.length() > 0) 
                	stdErrBuilder.append("\n");
                stdErrBuilder.append(line);
            }

            int exitCode = process.waitFor();
    		String stderr = stdErrBuilder.toString();
    		String stdout = stdOutBuilder.toString();
    		log.i(name,"exitCode",exitCode);
    		log.t("result",stdout,stderr,exitCode);
    		return new String[] {stdout, stderr, String.valueOf(exitCode)};
            

        } catch (InterruptedException e) {
            throw new IOException(e);
        }
		
		
	}

	public static String cmdLocationOrNull(Conductor con, String cmd) {
		try {
			return cmdLocation(con, cmd);
		} catch (NotFoundException e) {
			return null;
		}
	}
	
	//TODO cache findings
	public static String cmdLocation(Conductor con, String cmd) throws NotFoundException {
		if (con != null) {
			// check direct configuration
			String path = con.getProperties().getString(ConUtil.PROPERTY_CMD_PATH + cmd.toUpperCase(), null);
			if (path != null) return path;
		}
		String[] pathes = null;
		if (MSystem.isWindows())
			pathes = con.getProperties().getString(ConUtil.PROPERTY_PATH, DEFAULT_PATHES_WINDOWS).split(";");
		else
			pathes = con.getProperties().getString(ConUtil.PROPERTY_PATH, DEFAULT_PATHES_UNIX).split(":");
		
		for (String path : pathes) {
			File file = new File(path + File.separator + cmd);
			if (file.exists() && file.isFile() && file.canExecute() && file.canRead())
				return file.getAbsolutePath();
		}
		throw new NotFoundException("Command not found",cmd);
	}

    public static MUri getDefaultConfiguration() {
        MUri uri = MUri.toUri("mvn:de.mhus.conductor/conductor-plugin/"+Version.VERSION+"/yml/configuration-default");
        return uri;
    }

}
