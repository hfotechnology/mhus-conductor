package de.mhus.con.plugin;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;

@AMojo(name="test")
public class TestMojo implements ExecutePlugin {

	@Override
	public void execute(Context context) {
		System.err.println("TestPlugin for: " + context);
	}

}
