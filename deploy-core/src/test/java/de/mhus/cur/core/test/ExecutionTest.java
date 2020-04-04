package de.mhus.cur.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.CurUtil;
import de.mhus.cur.core.ConductorImpl;
import de.mhus.cur.core.ConfigTypesImpl;
import de.mhus.cur.core.ConfiguratorDefault;
import de.mhus.cur.core.ExecutorDefault;
import de.mhus.cur.core.FileScheme;
import de.mhus.cur.core.MavenScheme;
import de.mhus.cur.core.ProjectsValidator;
import de.mhus.cur.core.SchemesImpl;
import de.mhus.cur.core.YmlConfigType;
import de.mhus.lib.core.MApi;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.base.SingleBaseStrategy;
import de.mhus.lib.core.logging.Log;
import de.mhus.lib.errors.MException;

public class ExecutionTest {

    @Test
    public void testExecution() throws MException, ParserConfigurationException, SAXException, IOException {

        //MApi.setDirtyTrace(true);
        MApi.get().getLogFactory().setDefaultLevel(Log.LEVEL.DEBUG);
        MApi.get().getBaseControl().setFindStrategy(new SingleBaseStrategy());

        Conductor cur = new ConductorImpl(new File("../example/sample-parent"));
        
        ConfiguratorDefault config = new ConfiguratorDefault();
        ((SchemesImpl)config.getSchemes()).put("file", new FileScheme() );
        ((SchemesImpl)config.getSchemes()).put("mvn", new DummyScheme() );
        ((ConfigTypesImpl)config.getTypes()).put("yml", new YmlConfigType());
        ((ConfigTypesImpl)config.getTypes()).put("yaml", new YmlConfigType());
        
        config.getValidators().add(new ProjectsValidator());
        
        
        URI uri = URI.create("file:conductor.yml");
        config.configure(uri, cur, null);

        String mvnPath = CurUtil.cmdLocation("mvn");
        if (new File(mvnPath).exists()) {
	        ((MProperties)cur.getProperties()).put(CurUtil.PROPERTY_MVN_PATH, mvnPath);
	        ((MProperties)cur.getProperties()).put("deploy.version", TestUtil.currentVersion());
	        ((SchemesImpl)cur.getSchemes()).put("mvn",new MavenScheme());
	        
	        ExecutorDefault executor = new ExecutorDefault();
	        executor.execute(cur, "default");
	        
        } else {
        	System.err.println("Maven not found, skip test: " + mvnPath);
        }
    }
    
    // @Test
    public void testCmdExecute() throws IOException {
    	String mvnPath = CurUtil.cmdLocation("mvn");
    	if (new File(mvnPath).exists()) {
    		CurUtil.execute("TEST", new File("../deploy-api"), mvnPath + " install");
    	} else {
        	System.err.println("Maven not found, skip test: " + mvnPath);
        }
    }
    
	//@Test
	public void testCurPing() throws IOException {
		CurUtil.execute("TEST",new File("."), "ping -c 3 -i 2 google.com");
	}
	
	//@Test
	public void testDirectPing() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (MSystem.isWindows())
			// Windows
			processBuilder.command("cmd.exe", "/c", "ping -n 3 google.com");
		else
			// Unix
			processBuilder.command("/bin/bash", "-c", "ping -c 3 google.com");
			
        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
}
