package de.mhus.bwk.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import de.mhus.bwk.core.Bwk;
import de.mhus.bwk.core.BwkImpl;
import de.mhus.bwk.core.ConfigTypesImpl;
import de.mhus.bwk.core.ConfiguratorDefault;
import de.mhus.bwk.core.Context;
import de.mhus.bwk.core.ContextImpl;
import de.mhus.bwk.core.FileScheme;
import de.mhus.bwk.core.LabelsImpl;
import de.mhus.bwk.core.Lifecycle;
import de.mhus.bwk.core.ProjectsValidator;
import de.mhus.bwk.core.SchemesImpl;
import de.mhus.bwk.core.YmlConfigType;
import de.mhus.lib.errors.MException;

public class ConfiguratorTest {

    @Test
    public void testLoading() throws MException {
        
        Bwk bwk = new BwkImpl(new File("../example/sample-parent"));
        
        ConfiguratorDefault config = new ConfiguratorDefault();
        ((SchemesImpl)config.getSchemes()).put("file", new FileScheme() );
        ((SchemesImpl)config.getSchemes()).put("mvn", new DummyScheme() );
        ((ConfigTypesImpl)config.getTypes()).put("yml", new YmlConfigType());
        ((ConfigTypesImpl)config.getTypes()).put("yaml", new YmlConfigType());
        
        config.getValidators().add(new ProjectsValidator());
        
        
        URI uri = URI.create("file:deploy.yml");
        config.configure(uri, bwk);
        
        // test parameters
        assertEquals("4", bwk.getParameters().getString("overwriteMe"));
        assertEquals("1", bwk.getParameters().getString("rootWasThere"));
        assertEquals("1", bwk.getParameters().getString("parentWasThere"));
        assertEquals("1", bwk.getParameters().getString("defaultLifecycleWasThere"));
        assertEquals("1", bwk.getParameters().getString("deployWasThere"));
        
        // test projects
        assertEquals(4, bwk.getProjects().size());
        {
            LabelsImpl labels = new LabelsImpl();
            labels.put("group", "bundles");
            assertEquals(2, bwk.getProjects().select(labels).size());
        }
        {
            LabelsImpl labels = new LabelsImpl();
            labels.put("group", "parent");
            assertEquals(1, bwk.getProjects().select(labels).size());
        }
        {
            LabelsImpl labels = new LabelsImpl();
            assertEquals(4, bwk.getProjects().select(labels).size());
        }
        Context context = new ContextImpl(bwk);
        
        // test plugins
        assertEquals(2, bwk.getPlugins().size());
        assertEquals("2.0.0", TestUtil.getPluginVersion( context.make(bwk.getPlugins().get("newParent").getUrl()) ) );

        // test lifecycle
        assertEquals(1, bwk.getLifecycles().size());
        Lifecycle lc = bwk.getLifecycles().get("default");
        assertNotNull(lc);
        assertEquals(23, lc.getSteps().size());
        
        
    }
}
