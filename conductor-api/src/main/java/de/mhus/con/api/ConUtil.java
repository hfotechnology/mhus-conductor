package de.mhus.con.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.UUID;

import de.mhus.conductor.api.meta.Version;
import de.mhus.lib.core.MCast;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.MValidator;
import de.mhus.lib.core.console.Console;
import de.mhus.lib.core.console.Console.COLOR;
import de.mhus.lib.core.logging.Log;
import de.mhus.lib.core.logging.Log.LEVEL;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

public class ConUtil {

	private static final Log log = Log.getLog(Conductor.class);
	public static final String PROPERTY_FAE = "conductor.fae";
	public static final String PROPERTY_CMD_PATH = "conductor.cmd.";
	public static final String PROPERTY_PATH = "conductor.path";
	public static final String DEFAULT_PATHES_UNIX = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin";
	public static final String DEFAULT_PATHES_WINDOWS = "C:\\Program Files;C:\\Winnt;C:\\Winnt\\System32";
    public static final String PROPERTY_VERSION = "conductor.version";
    public static final String PROPERTY_LIFECYCLE = "conductor.lifecycle";
    public static final String PROPERTY_DOWNLOAD_SNAPSHOTS = "conductor.downloadSnapshots";
    public static final String ENV_HOME = "CONDUCTOR_HOME";
    public static final String ENV_HOME_DEFAULT = ".conductor";
    public static final String PROPERTY_ROOT = "conductor.root";
    public static final String PROPERTY_HOME = "conductor.home";
    // private static Console console;
    public static final String PROPERTY_VALIDATORS = "conductor.validators";
	
    public static void orderProjects(LinkedList<Project> projects, String order, boolean orderAsc) {
        projects.sort(new Comparator<Project>() {

            @Override
            public int compare(Project o1, Project o2) {
                int ret = compare( o1.getLabels().getOrNull(order), o2.getLabels().getOrNull(order));
                if (!orderAsc) ret = ret * -1;
                return ret;
            }

            private int compare(String o1, String o2) {
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return -1;
                if (o2 == null) return 1;
                if (MValidator.isNumber(o1) && MValidator.isNumber(o2))
                    return Double.compare(MCast.todouble(o1, 0), MCast.todouble(o2, 0));
                return o1.compareTo(o2);
            }
        });
    }

	public static String[] execute(String name, File rootDir, String cmd, boolean infoOut) throws IOException {
		
		log.i(name,"execute",cmd,rootDir);
		String shortName = MString.truncateNice(name, 40, 15);

		ProcessBuilder processBuilder = new ProcessBuilder();
		if (rootDir != null)
		    processBuilder.directory(rootDir);
		if (MSystem.isWindows())
			// Windows
			processBuilder.command("cmd.exe", "/c", cmd);
		else
			// Unix
			processBuilder.command("/bin/bash", "-c", cmd);

		Console console = getConsole();
		
		boolean output = infoOut || log.isLevelEnabled(LEVEL.DEBUG);
		
        try {

            Process process = processBuilder.start();

            BufferedReader outReader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder stdOutBuilder = new StringBuilder();

            final BufferedReader errReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder stdErrBuilder = new StringBuilder();

            Thread errorWriterTask = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    String line;
                    try {
                        while ((line = errReader.readLine()) != null) {
                            
                            if (output)
                                synchronized (process) {
                                    console.print("[");
                                    console.setColor(COLOR.RED, null);
                                    console.print(shortName);
                                    console.cleanup();
                                    console.print("] ");
                                    console.println(line);
                                    console.flush();
                                }
                            if (stdErrBuilder.length() > 0) 
                                stdErrBuilder.append("\n");
                            stdErrBuilder.append(line);
                        }
                    } catch (Throwable t) {}
                }
            });
            errorWriterTask.start();
            
            String line;
            while ((line = outReader.readLine()) != null) {
                
                if (output)
                    synchronized (process) {
                        console.print("[");
                        console.setColor(COLOR.GREEN, null);
                        console.print(shortName);
                        console.cleanup();
                        console.print("] ");
                        console.println(line);
                        console.flush();
                    }
                if (stdOutBuilder.length() > 0) 
                	stdOutBuilder.append("\n");
                stdOutBuilder.append(line);
            }


            int exitCode = process.waitFor();
            
            errorWriterTask.interrupt();
            
    		String stderr = stdErrBuilder.toString();
    		String stdout = stdOutBuilder.toString();
    		log.i(name,"exitCode",exitCode);
    		return new String[] {stdout, stderr, String.valueOf(exitCode)};
            

        } catch (InterruptedException e) {
            throw new IOException(e);
        }
		
		
	}

	public static Console getConsole() {
	    Console ret = Console.get();
	    log.t("Console",ret.getClass());
	    return ret;
//	    if (console == null) {
//	        String term = System.getenv("TERM");
//            if (term != null) {
//                term = term.toLowerCase();
//                if (term.indexOf("xterm") >= 0) {
//                    try {
//                        console = new XTermConsole() {
//                            @Override
//                            public boolean isSupportColor() {
//                                return true;
//                            }
//        
//                        };
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//            if (console == null) console = Console.get();
//            Console.set(console);
//	    }
//        return console;
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
		String systemPath = System.getenv("PATH");
		if (MSystem.isWindows())
			pathes = con.getProperties().getString(ConUtil.PROPERTY_PATH, DEFAULT_PATHES_WINDOWS 
			        + (systemPath == null ? "" : ";" + systemPath) ).split(";");
		else
			pathes = con.getProperties().getString(ConUtil.PROPERTY_PATH, DEFAULT_PATHES_UNIX 
			        + (systemPath == null ? "" : ":" + systemPath) ).split(":");
		
		for (String path : pathes) {
			File file = new File(path + File.separator + cmd);
			if (file.exists() && file.isFile() && file.canExecute() && file.canRead())
				return file.getAbsolutePath();
		}
		throw new NotFoundException("Command not found",cmd);
	}

    public static MUri getDefaultConfiguration(String name) {
        String ext = MString.afterLastIndex(name, '.');
        name = MString.beforeLastIndex(name, '.');
        MUri uri = MUri.toUri("mvn:de.mhus.conductor/conductor-plugin/"+Version.VERSION+"/"+ext+"/"+name);
        return uri;
    }

    public static File getFile(File root, String path) {
        File f = new File(path);
        if (!f.isAbsolute())
            f = new File(root, path);
        return f;
    }

    public static File getHome() {
        String home = System.getenv(ENV_HOME);
        if (MString.isEmpty(home))
            return new File(MSystem.getUserHome(), ENV_HOME_DEFAULT).getAbsoluteFile();
        return new File(home);
    }

    public static File createTempFile(Conductor con, Class<?> owner, String suffix) throws IOException {
        File tmp = new File(getHome(), "tmp");
        if (tmp.exists() && tmp.isDirectory()) {
            File file = new File(tmp, owner.getSimpleName() + "-" + UUID.randomUUID() + "." + suffix);
            file.deleteOnExit();
            return file;
        }
        File file = File.createTempFile(owner.getSimpleName(), suffix);
        file.deleteOnExit();
        return file;
    }

    public static String getMainPackageName() {
        return "de.mhus.con";
    }

}
