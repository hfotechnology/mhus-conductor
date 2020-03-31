package de.mhus.deploy.core;

import java.net.URI;
import java.util.HashSet;

import de.mhus.lib.core.MLog;

public class ConfiguratorDefault extends MLog implements Configurator {

	private static final String NODE_PROJECT = "project";
	private static final String NODE_IMPORT = "import";
	protected Schemes schemes = new SchemesImpl();
	protected ConfigTypes types = new ConfigTypesImpl();
	protected Bwk bwk;
	protected HashSet<String> loadedUris = new HashSet<>();
	
	@Override
	public void configure(URI uri, Bwk bwk) {
		this.bwk = bwk;
		overwrite(uri);
	}

	protected void overwrite(URI uri) {
		log().d("load uri",uri);
		loadedUris.add(uri.toString());
		// 1 load resource
		String schemeStr = uri.getScheme();
		Scheme scheme = schemes.get(schemeStr);
		String content = scheme.load(uri);
		ConfigType type = types.get(uri);
		YMap docE = type.create(content);
		YMap projectE = docE.getMap(NODE_PROJECT);
		YList importE = projectE.getList(NODE_IMPORT);
		loadImports(importE);
		
	}

	private void loadImports(YList importE) {
		for (String uriStr : importE.toStringList()) {
			if (loadedUris.contains(uriStr)) {
				log().d("Ignore, already loaded",uriStr);
			} else {
				URI uri = URI.create(uriStr);
				overwrite(uri);
			}
		}
	}

}
