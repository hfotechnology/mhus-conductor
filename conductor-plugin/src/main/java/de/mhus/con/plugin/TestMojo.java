package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;

@AMojo(name="test")
public class TestMojo extends AbstractMavenExecute {

	@Override
	public boolean execute2(File dir, String moduleName, Context context) {
		System.err.println("TestPlugin for: " + context + "/" + moduleName + " - Dir: " + dir);
        return true;
	}

}
