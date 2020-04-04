package de.mhus.deploy.plugin;

import de.mhus.deploy.api.Context;
import de.mhus.deploy.api.ExecutePlugin;
import de.mhus.deploy.api.Mojo;

@Mojo(name="test")
public class TestMojo implements ExecutePlugin {

	@Override
	public void execute(Context context) {
		System.out.println("HEHEHE !!!");
	}

}
