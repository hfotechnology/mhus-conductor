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
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.MXml;

@AMojo(name="updatePomProperties")
public class UpdatePomParentVersion extends MLog implements ExecutePlugin {

    @Override
    public void execute(Context context) throws Exception {

        File pomFile = new File(context.getProject().getRootDir(), "pom.xml");
        if (!pomFile.exists()) {
            log().i("pom.xml not found in project, skip");
            return;
        }
        Document pomDoc = MXml.loadXml(pomFile);
        Element pomE = pomDoc.getDocumentElement();
        Element parentE = MXml.getElementByPath(pomE, "parent");
        if (parentE == null) {
            log().d("pom parent not found, skip");
            return;
        }
        Element versionE = MXml.getElementByPath(parentE, "version");
        if (versionE == null) {
            log().d("pom parent version not found, skip");
            return;
        }

        String version = context.getStep().getProperties().getString("version");
        if (MXml.getValue(versionE, false).equals(version)) {
            log().i("version not changed",version);
            return;
        }
        
        // remove all
        NodeList l = versionE.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node le = l.item(i);
            versionE.removeChild(le);
        }
        Text versionTE = pomDoc.createTextNode(version);
        versionE.appendChild(versionTE);
        
        log().d("update pom",pomFile);
        MXml.saveXml(pomE, pomFile);
        
    }

}
