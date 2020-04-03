package de.mhus.deploy.plugin;

import de.mhus.deploy.api.ConductorPlugin;
import de.mhus.deploy.api.Context;
import de.mhus.deploy.api.Mojo;

@Mojo(name="test")
public class TestMojo implements ConductorPlugin {

	@Override
	public void execute(Context context) {
		System.out.println("HEHEHE !!!");
	}

}
