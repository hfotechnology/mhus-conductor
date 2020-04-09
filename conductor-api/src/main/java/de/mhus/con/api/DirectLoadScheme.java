package de.mhus.con.api;

import java.io.IOException;

import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

public interface DirectLoadScheme extends Scheme {

	ConductorPlugin loadPlugin(MUri uri, String mojoName) throws NotFoundException, IOException;
	
	String loadContent(MUri uri);
	
}
