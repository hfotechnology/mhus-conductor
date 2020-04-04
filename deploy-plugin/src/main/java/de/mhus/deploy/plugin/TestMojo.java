package de.mhus.deploy.plugin;

import de.mhus.cur.api.Context;
import de.mhus.cur.api.ExecutePlugin;
import de.mhus.cur.api.Mojo;

@Mojo(name="test")
public class TestMojo implements ExecutePlugin {

	@Override
	public void execute(Context context) {
		System.err.println("TestPlugin for: " + context);
	}

}
