package de.mhus.con.core;

import java.io.File;
import java.io.IOException;

import de.mhus.con.api.AScheme;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Conductor;
import de.mhus.con.api.Scheme;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

@AScheme(name="mvn")
public class MavenScheme extends MLog implements Scheme {

	private static String repositoryLocation;

	public static File getLocalRepositoryPath(Conductor con) throws NotFoundException, IOException {
		if (repositoryLocation == null) {
			// mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout
//			MavenCli cli = new MavenCli();
//			cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
			String mvnPath = ConUtil.cmdLocation(con, "mvn");
			repositoryLocation = ConUtil.execute("MVN",con.getRoot(), mvnPath + " help:evaluate -Dexpression=settings.localRepository -q -DforceStdout")[0];
		}
		
		File dir = new File(repositoryLocation);
		if (!dir.exists() || !dir.isDirectory())
			throw new NotFoundException("maven local repository not found", repositoryLocation);

		return dir;
	}
	
	@Override
	public File load(Conductor con, MUri uri) throws IOException, NotFoundException {
		File location = getArtifactLocation("MVN",con, uri);
		
		if (location.exists())
			return location;
		
		// String mvnUrl = uri.getPath().replace('/', ':');
        String[] parts = uri.getPath().split("/");
        String group    = parts[0];
		String artifact = parts[1];
		String version  = parts[2];
		String ext      = parts.length > 3 ? parts[3] : null;
		String classifier = parts.length > 4 ? parts[4] : null;

		assert group != null;
		assert artifact != null;
		assert version !=null;
		
		if (!con.getProperties().getBoolean(ConUtil.PROPERTY_DOWNLOAD_SNAPSHOTS, false) && version.endsWith("-SNAPSHOT"))
		    throw new NotFoundException("maven SNAPSHOT artifact not found",uri,location);

		log().i("Load Maven Resource",location,group,artifact,version,ext,classifier);
		
		// mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get -Dartifact=com.google.guava:guava:15.0 -DrepoUrl=
		String mvnPath = ConUtil.cmdLocation(con, "mvn");
		ConUtil.execute(uri.getPath(),con.getRoot(), mvnPath + " dependency:get"
		    +" -DartifactId="+artifact
            +" -DgroupId="+group
            +" -Dversion="+version
            + (ext == null ? "" : " -Dpackaging="+ext)
            + (classifier == null ? "" : " -Dclassifier="+classifier)
			+" -DrepoUrl=");
		
		if (location.exists())
			return location;
		
		System.out.println(location);
		throw new NotFoundException("maven artifact not found",uri,location);
	}
	
	public File getArtifactLocation(String name, Conductor con, MUri uri) throws IOException, NotFoundException {
		
		File dir = getLocalRepositoryPath(con);
				
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
