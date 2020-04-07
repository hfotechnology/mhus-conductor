package de.mhus.con.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import de.mhus.con.api.Conductor;
import de.mhus.con.api.Context;
import de.mhus.con.api.Lifecycle;
import de.mhus.con.core.ConductorImpl;
import de.mhus.con.core.ConfigTypesImpl;
import de.mhus.con.core.ConfiguratorDefault;
import de.mhus.con.core.ContextImpl;
import de.mhus.con.core.FileScheme;
import de.mhus.con.core.LabelsImpl;
import de.mhus.con.core.ProjectsValidator;
import de.mhus.con.core.SchemesImpl;
import de.mhus.con.core.YmlConfigType;
import de.mhus.lib.errors.MException;

public class ConfiguratorTest {

    @Test
    public void testLoading() throws MException {
        
        Conductor con = new ConductorImpl(new File("../example/sample-parent"));
        
        ConfiguratorDefault config = new ConfiguratorDefault();
        ((SchemesImpl)config.getSchemes()).put("file", new FileScheme() );
        ((SchemesImpl)config.getSchemes()).put("mvn", new DummyScheme() );
        ((ConfigTypesImpl)config.getTypes()).put("yml", new YmlConfigType());
        ((ConfigTypesImpl)config.getTypes()).put("yaml", new YmlConfigType());
        
        config.getValidators().add(new ProjectsValidator());
        
        
        URI uri = URI.create("file:conductor.yml");
        config.configure(uri, con, null);
        
        // test parameters
        assertEquals("4", con.getProperties().getString("overwriteMe"));
        assertEquals("1", con.getProperties().getString("rootWasThere"));
        assertEquals("1", con.getProperties().getString("parentWasThere"));
        assertEquals("1", con.getProperties().getString("defaultLifecycleWasThere"));
        assertEquals("1", con.getProperties().getString("conductorWasThere"));
        
        // test projects
        assertEquals(4, con.getProjects().size());
        {
            LabelsImpl labels = new LabelsImpl();
            labels.put("group", "bundles");
            assertEquals(2, con.getProjects().select(labels).size());
        }
        {
            LabelsImpl labels = new LabelsImpl();
            labels.put("group", "parent");
            assertEquals(1, con.getProjects().select(labels).size());
        }
        {
            LabelsImpl labels = new LabelsImpl();
            assertEquals(4, con.getProjects().select(labels).size());
        }
        Context context = new ContextImpl(con);
        
        // test plugins
        assertEquals(8, con.getPlugins().size());
        assertEquals("2.0.0", TestUtil.getPluginVersion( context.make(con.getPlugins().get("newParent").getUri()) ) );

        // test lifecycle
        assertEquals(1, con.getLifecycles().size());
        Lifecycle lc = con.getLifecycles().get("default");
        assertNotNull(lc);
        assertEquals(22, lc.getSteps().size());
        
        
    }
}
