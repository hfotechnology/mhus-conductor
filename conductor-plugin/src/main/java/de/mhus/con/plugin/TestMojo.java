package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;

@AMojo(name="test")
public class TestMojo extends AbstractMavenExecute {

	@Override
	public void execute2(File dir, Context context) {
		System.err.println("TestPlugin for: " + context + " - Dir: " + dir);
	}

}
