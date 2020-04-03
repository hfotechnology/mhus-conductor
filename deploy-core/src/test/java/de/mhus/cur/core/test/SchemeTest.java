package de.mhus.cur.core.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.mhus.cur.core.Conductor;
import de.mhus.cur.core.ConductorImpl;
import de.mhus.cur.core.CurUtil;
import de.mhus.cur.core.MavenScheme;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.util.MUri;
import de.mhus.lib.errors.NotFoundException;

public class SchemeTest {

	@Test
	public void testMavenScheme() throws IOException, NotFoundException {
		MavenScheme scheme = new MavenScheme();
        Conductor cur = new ConductorImpl(new File("../example/sample-parent"));
        
        String mvnPath = "/usr/local/bin/mvn"; //XXX
        if (new File(mvnPath).exists()) {
	        ((MProperties)cur.getProperties()).put(CurUtil.PROPERTY_MVN_PATH, mvnPath);
	        
			MUri uri = MUri.toUri("mvn:/com.google.guava/guava/15.0");
			
			File loc = scheme.getArtifactLocation(cur, uri);
			System.out.println(loc);
			
			if (loc.exists())
				loc.delete();
			
			scheme.load(cur, uri);
			
			assertTrue(loc.exists());
        } else {
        	System.err.println("Maven not found, skip test: " + mvnPath);
        }
	}
}
