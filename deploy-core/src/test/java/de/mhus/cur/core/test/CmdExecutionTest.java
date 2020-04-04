package de.mhus.cur.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import de.mhus.cur.core.CurUtil;
import de.mhus.lib.core.MSystem;

public class CmdExecutionTest {

	//@Test
	public void testCurPing() throws IOException {
		CurUtil.execute(new File("."), "ping -c 3 -i 2 google.com");
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
