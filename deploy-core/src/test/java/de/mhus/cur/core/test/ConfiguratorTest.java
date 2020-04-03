package de.mhus.cur.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import de.mhus.cur.core.Conductor;
import de.mhus.cur.core.ConductorImpl;
import de.mhus.cur.core.ConfigTypesImpl;
import de.mhus.cur.core.ConfiguratorDefault;
import de.mhus.cur.core.Context;
import de.mhus.cur.core.ContextImpl;
import de.mhus.cur.core.FileScheme;
import de.mhus.cur.core.LabelsImpl;
import de.mhus.cur.core.Lifecycle;
import de.mhus.cur.core.ProjectsValidator;
import de.mhus.cur.core.SchemesImpl;
import de.mhus.cur.core.YmlConfigType;
import de.mhus.lib.errors.MException;

public class ConfiguratorTest {

    @Test
    public void testLoading() throws MException {
        
        Conductor cur = new ConductorImpl(new File("../example/sample-parent"));
        
        ConfiguratorDefault config = new ConfiguratorDefault();
        ((SchemesImpl)config.getSchemes()).put("file", new FileScheme() );
        ((SchemesImpl)config.getSchemes()).put("mvn", new DummyScheme() );
        ((ConfigTypesImpl)config.getTypes()).put("yml", new YmlConfigType());
        ((ConfigTypesImpl)config.getTypes()).put("yaml", new YmlConfigType());
        
        config.getValidators().add(new ProjectsValidator());
        
        
        URI uri = URI.create("file:conductor.yml");
        config.configure(uri, cur);
        
        // test parameters
        assertEquals("4", cur.getProperties().getString("overwriteMe"));
        assertEquals("1", cur.getProperties().getString("rootWasThere"));
        assertEquals("1", cur.getProperties().getString("parentWasThere"));
        assertEquals("1", cur.getProperties().getString("defaultLifecycleWasThere"));
        assertEquals("1", cur.getProperties().getString("deployWasThere"));
        
        // test projects
        assertEquals(4, cur.getProjects().size());
        {
            LabelsImpl labels = new LabelsImpl();
            labels.put("group", "bundles");
            assertEquals(2, cur.getProjects().select(labels).size());
        }
        {
            LabelsImpl labels = new LabelsImpl();
            labels.put("group", "parent");
            assertEquals(1, cur.getProjects().select(labels).size());
        }
        {
            LabelsImpl labels = new LabelsImpl();
            assertEquals(4, cur.getProjects().select(labels).size());
        }
        Context context = new ContextImpl(cur);
        
        // test plugins
        assertEquals(3, cur.getPlugins().size());
        assertEquals("2.0.0", TestUtil.getPluginVersion( context.make(cur.getPlugins().get("newParent").getUri()) ) );

        // test lifecycle
        assertEquals(1, cur.getLifecycles().size());
        Lifecycle lc = cur.getLifecycles().get("default");
        assertNotNull(lc);
        assertEquals(23, lc.getSteps().size());
        
        
    }
}
