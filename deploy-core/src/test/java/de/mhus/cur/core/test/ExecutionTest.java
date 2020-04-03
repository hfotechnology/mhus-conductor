package de.mhus.cur.core.test;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import de.mhus.cur.core.Conductor;
import de.mhus.cur.core.ConductorImpl;
import de.mhus.cur.core.ConfigTypesImpl;
import de.mhus.cur.core.ConfiguratorDefault;
import de.mhus.cur.core.ExecutorDefault;
import de.mhus.cur.core.FileScheme;
import de.mhus.cur.core.ProjectsValidator;
import de.mhus.cur.core.SchemesImpl;
import de.mhus.cur.core.YmlConfigType;
import de.mhus.lib.errors.MException;

public class ExecutionTest {

    @Test
    public void testExecution() throws MException {
        
        Conductor cur = new ConductorImpl(new File("../example/sample-parent"));
        
        ConfiguratorDefault config = new ConfiguratorDefault();
        ((SchemesImpl)config.getSchemes()).put("file", new FileScheme() );
        ((SchemesImpl)config.getSchemes()).put("mvn", new DummyScheme() );
        ((ConfigTypesImpl)config.getTypes()).put("yml", new YmlConfigType());
        ((ConfigTypesImpl)config.getTypes()).put("yaml", new YmlConfigType());
        
        config.getValidators().add(new ProjectsValidator());
        
        
        URI uri = URI.create("file:deploy.yml");
        config.configure(uri, cur);

        ExecutorDefault executor = new ExecutorDefault();
        
        executor.execute(cur, "default");
        
    }
}
