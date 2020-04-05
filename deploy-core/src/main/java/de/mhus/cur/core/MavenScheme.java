package de.mhus.cur.core;

import java.io.File;
import java.io.IOException;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.CurUtil;
import de.mhus.cur.api.Scheme;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

public class MavenScheme extends MLog implements Scheme {

	private static String repositoryLocation;

	public static File getLocalRepositoryPath(Conductor cur) throws NotFoundException, IOException {
		if (repositoryLocation == null) {
			// mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout
//			MavenCli cli = new MavenCli();
//			cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
			String mvnPath = CurUtil.cmdLocation(cur, "mvn");
			repositoryLocation = CurUtil.execute("MVN",cur.getRoot(), mvnPath + " help:evaluate -Dexpression=settings.localRepository -q -DforceStdout")[0];
		}
		
		File dir = new File(repositoryLocation);
		if (!dir.exists() || !dir.isDirectory())
			throw new NotFoundException("maven local repository not found", repositoryLocation);

		return dir;
	}
	
	@Override
	public File load(Conductor cur, MUri uri) throws IOException, NotFoundException {
		File location = getArtifactLocation("MVN",cur, uri);
		
		if (location.exists())
			return location;
		
		String mvnUrl = uri.getPath().replace('/', ':');
		log().i("Load Maven Resource",location,mvnUrl);
		
		// mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get -Dartifact=com.google.guava:guava:15.0 -DrepoUrl=
		String mvnPath = CurUtil.cmdLocation(cur, "mvn");
		CurUtil.execute(uri.getPath(),cur.getRoot(), mvnPath + " org.apache.maven.plugins:maven-dependency-plugin:2.1:get -Dartifact="
			+mvnUrl
			+" -DrepoUrl=");
		
		if (location.exists())
			return location;
		
		System.out.println(location);
		throw new NotFoundException("maven artifact not found",uri,location);
	}
	
	public File getArtifactLocation(String name, Conductor cur, MUri uri) throws IOException, NotFoundException {
		
		File dir = getLocalRepositoryPath(cur);
				
		String[] parts = uri.getPath().split("/");
		
		String path = parts[0].replace('.', '/') + "/" + parts[1] + "/" + parts[2] + "/" + parts[1] + "-" + parts[2];
		
		String ext = parts.length > 3 ? parts[3] : "jar";
		String classifier = parts.length > 4 ? parts[4] : null;
		if (classifier != null)
			path = path + "-" + classifier;
		path = path + "." + ext;
		
		File location = new File(dir, path);

		return location;
	}

}
