package de.mhus.cur.core;

import java.io.IOException;
import java.util.LinkedList;

import de.mhus.deploy.api.Conductor;
import de.mhus.deploy.api.Project;
import de.mhus.lib.core.MFile;
import de.mhus.lib.core.logging.Log;

public class CurUtil {

	public static final Log log = Log.getLog(Conductor.class);
	public static final String PROPERTY_FAE = "_conductor.fae";
	public static final String PROPERTY_MVN_PATH = "_conductor.schema.mvn.path";
	
    public static void orderProjects(LinkedList<Project> projects, String order, boolean orderAsc) {
        
    }

	public static String execute(Conductor cur, String cmd) throws IOException {
		log.d("execute",cmd);
		Process p = Runtime.getRuntime().exec(cmd,null,cur.getRoot());
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			log.w(cmd,e);
		}
		String stderr = MFile.readFile(p.getErrorStream());
		String stdout = MFile.readFile(p.getInputStream());
		log.d("result",stdout,stderr);
		return stdout;
	}

}
