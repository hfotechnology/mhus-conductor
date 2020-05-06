package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Project;
import de.mhus.con.core.ContextProject;
import de.mhus.lib.core.MCast;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MString;

@AMojo(name = "nextSnapshotVersions")
public class NextSnapshotVersions extends MLog implements ExecutePlugin {

	@Override
	public boolean execute(Context context) throws Exception {
	    Project project = context.getProject();
    	// String changed = ((LabelsImpl)project.getLabels()).getOrNull("version.changed");
    	String version = ((MProperties)project.getProperties()).getString("version", null);
    	if (version != null) {
    		String[] parts = version.split("\\.");
    		if (parts.length >= 2) {
    			parts[1] = String.valueOf(MCast.toint(parts[1], 0) + 1);
    			if (parts.length >= 3)
    				parts[2] = "0";
    		} else
    		if (parts.length >= 1)
    			parts[0] = String.valueOf(MCast.toint(parts[0], 0) + 1);
    		version = MString.join(parts, '.') + "-SNAPSHOT";
    		log().i("Changed project",project.getName(),version);
    		
    		((MProperties)((ContextProject)project).getInstance().getProperties()).setString("version", version);
    	}
		return true;
	}

}
