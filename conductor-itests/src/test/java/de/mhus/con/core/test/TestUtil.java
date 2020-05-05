package de.mhus.con.core.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.mhus.lib.core.MApi;
import de.mhus.lib.core.MXml;
import de.mhus.lib.core.logging.Log;
import de.mhus.lib.core.util.MUri;

public class TestUtil {

	public static void enableDebug() {
        MApi.setDirtyTrace(false);
        MApi.get().getLogFactory().setDefaultLevel(Log.LEVEL.DEBUG);
	}
	
    public static String getPluginVersion(String uriStr) {
        MUri uri = MUri.toUri(uriStr);
        String[] parts = uri.getPath().split("/");
        return parts[2];
    }

	public static String conrentVersion() throws ParserConfigurationException, SAXException, IOException {
		Document doc = MXml.loadXml(new File("pom.xml"));
		String version = MXml.getValue(doc.getDocumentElement(), "/parent/version", "");
		return version;
	}

}
