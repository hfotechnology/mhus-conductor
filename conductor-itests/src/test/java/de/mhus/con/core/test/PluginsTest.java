package de.mhus.con.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import de.mhus.con.core.MainCli;
import de.mhus.con.core.MainOptionConsole;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.io.YOutputStream;
import de.mhus.lib.errors.MException;

public class PluginsTest {

    private static MainCli cli;
    private static MainOptionConsole console;
    private static ByteArrayOutputStream out;
    private static PrintStream orgOut;

    @BeforeAll
    public static void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
        cli = new MainCli();
        console = new MainOptionConsole();
        console.init(cli);
        out = new ByteArrayOutputStream();
        YOutputStream swt = new YOutputStream(System.out, out);
        orgOut = System.out;
        System.setOut(new PrintStream(swt));
    }
    
    @AfterAll
    public static void deinit() {
        System.setOut(orgOut);
    }
    
    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        TestUtil.start(testInfo);
    }
    
    @Test
    void testMavenVersion() throws MException {
        {
            out.reset();
            console.execute("print ${maven.version()}");
            String str = new String(out.toByteArray()).trim();
            System.out.println("version> " + str);
            assertTrue(str.length() > 0);
            assertEquals(2, MString.countCharacters(str, '.'));
        }
        {
            out.reset();
            console.execute("print ${maven.version(os.name)}");
            String str = new String(out.toByteArray()).trim();
            System.out.println("os.name> " + str);
            assertTrue(str.length() > 0);
        }
        {
            out.reset();
            console.execute("print ${maven.version(os.arch)}");
            String str = new String(out.toByteArray()).trim();
            System.out.println("os.arch> " + str);
            assertTrue(str.length() > 0);
        }
        {
            out.reset();
            console.execute("print ${maven.version(os.version)}");
            String str = new String(out.toByteArray()).trim();
            System.out.println("os.version> " + str);
            assertTrue(str.length() > 0);
        }
    }

    
}
