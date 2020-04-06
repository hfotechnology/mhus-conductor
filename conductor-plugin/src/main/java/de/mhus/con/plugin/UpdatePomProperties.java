package de.mhus.con.plugin;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.Project;
import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MXml;

@AMojo(name="updatePomProperties")
public class UpdatePomProperties extends MLog implements ExecutePlugin {

    @Override
    public void execute(Context context) throws Exception {
        
        IReadProperties pp = context.getProject().getProperties();
        
        File pomFile = new File(context.getProject().getRootDir(), "pom.xml");
        if (!pomFile.exists()) {
            log().i("pom.xml not found in project, skip");
            return;
        }
        Document pomDoc = MXml.loadXml(pomFile);
        Element pomE = pomDoc.getDocumentElement();
        Element propE = MXml.getElementByPath(pomE, "properties");
        if (propE == null) {
            log().d("pom properties not found, skip");
            return;
        }

        boolean changed = false;
        for (Project project : context.getConductor().getProjects()) {
            String version = project.getProperties().getString("version", null);
            String name = project.getName();
            if (version != null) {
                String propKey = pp.getString("property." + name, name); // custom mapping
                Element propKeyE = MXml.getElementByPath(propE, propKey);
                if (propKeyE != null) {
                    String propValue = MXml.getValue(propKeyE, false);
                    if (!version.equals(propValue)) {
                        changed = true;
                        // remove all
                        NodeList l = propKeyE.getChildNodes();
                        for (int i = 0; i < l.getLength(); i++) {
                            Node le = l.item(i);
                            propKeyE.removeChild(le);
                        }
                        Text versionE = pomDoc.createTextNode(version);
                        propKeyE.appendChild(versionE);
                    }
                }
            }
        }
        
        if (changed) {
            log().d("update pom",pomFile);
            MXml.saveXml(pomE, pomFile);
        }
        
    }

}
