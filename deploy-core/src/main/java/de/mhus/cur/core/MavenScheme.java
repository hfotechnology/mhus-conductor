package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;

import de.mhus.lib.core.MApi;
import de.mhus.lib.core.base.SingleBaseStrategy;
import de.mhus.lib.core.logging.Log;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.MRuntimeException;
import de.mhus.lib.errors.NotFoundException;

public class MavenScheme implements Scheme {

	private String repositoryLocation;

	@Override
	public File load(Conductor cur, MUri uri) throws IOException, NotFoundException {
        MApi.setDirtyTrace(true);
        MApi.get().getLogFactory().setDefaultLevel(Log.LEVEL.DEBUG);
        MApi.get().getBaseControl().setFindStrategy(new SingleBaseStrategy());
		
		File location = getArtifactLocation(cur, uri);
		
		if (location.exists())
			return location;
		
		// mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get -Dartifact=com.google.guava:guava:15.0 -DrepoUrl=
		String mvnPath = cur.getProperties().getString(CurUtil.PROPERTY_MVN_PATH, "mvn");
		CurUtil.execute(cur, mvnPath + " org.apache.maven.plugins:maven-dependency-plugin:2.1:get -Dartifact="
			+uri.getPath().replace('/', ':').substring(1)
			+" -DrepoUrl=");
		
		if (location.exists())
			return location;
		
		System.out.println(location);
		throw new NotFoundException("maven artifact not found",uri,location);
	}
	
	public File getArtifactLocation(Conductor cur, MUri uri) throws IOException {
		if (repositoryLocation == null) {
			// mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout
//			MavenCli cli = new MavenCli();
//			cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
			
			String mvnPath = cur.getProperties().getString(CurUtil.PROPERTY_MVN_PATH, "mvn");
			repositoryLocation = CurUtil.execute(cur, mvnPath + " help:evaluate -Dexpression=settings.localRepository -q -DforceStdout");
		}
		
		File dir = new File(repositoryLocation);
		if (!dir.exists() || !dir.isDirectory())
			throw new MRuntimeException("maven local repository not found",repositoryLocation);
		
		String[] parts = uri.getPath().split("/");
		
		String path = parts[1].replace('.', '/') + "/" + parts[2] + "/" + parts[3] + "/" + parts[2] + "-" + parts[3];
		
		String ext = parts.length > 4 ? parts[4] : "jar";
		String x = parts.length > 5 ? parts[5] : null;
		if (x != null)
			path = path + "-" + x;
		path = path + "." + ext;
		
		File location = new File(dir, path);

		return location;
	}

}
