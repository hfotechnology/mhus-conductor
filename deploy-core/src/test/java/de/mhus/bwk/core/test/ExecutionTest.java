package de.mhus.bwk.core.test;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import de.mhus.bwk.core.Bwk;
import de.mhus.bwk.core.BwkImpl;
import de.mhus.bwk.core.ConfigTypesImpl;
import de.mhus.bwk.core.ConfiguratorDefault;
import de.mhus.bwk.core.ExecutorDefault;
import de.mhus.bwk.core.FileScheme;
import de.mhus.bwk.core.ProjectsValidator;
import de.mhus.bwk.core.SchemesImpl;
import de.mhus.bwk.core.YmlConfigType;
import de.mhus.lib.errors.MException;

public class ExecutionTest {

    @Test
    public void testExecution() throws MException {
        
        Bwk bwk = new BwkImpl(new File("../example/sample-parent"));
        
        ConfiguratorDefault config = new ConfiguratorDefault();
        ((SchemesImpl)config.getSchemes()).put("file", new FileScheme() );
        ((SchemesImpl)config.getSchemes()).put("mvn", new DummyScheme() );
        ((ConfigTypesImpl)config.getTypes()).put("yml", new YmlConfigType());
        ((ConfigTypesImpl)config.getTypes()).put("yaml", new YmlConfigType());
        
        config.getValidators().add(new ProjectsValidator());
        
        
        URI uri = URI.create("file:deploy.yml");
        config.configure(uri, bwk);

        ExecutorDefault executor = new ExecutorDefault();
        
        executor.execute(bwk, "default");
        
    }
}
