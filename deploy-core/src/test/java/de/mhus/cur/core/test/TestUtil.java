package de.mhus.cur.core.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.mhus.lib.core.MXml;
import de.mhus.lib.core.util.MUri;

public class TestUtil {

    public static String getPluginVersion(String uriStr) {
        MUri uri = MUri.toUri(uriStr);
        String[] parts = uri.getPath().split("/");
        return parts[2];
    }

	public static String currentVersion() throws ParserConfigurationException, SAXException, IOException {
		Document doc = MXml.loadXml(new File("pom.xml"));
		String version = MXml.getValue(doc.getDocumentElement(), "/parent/version", "");
		return version;
	}

	public static String mvnLocation() {
		// TODO Auto-generated method stub
		return "/usr/local/bin/mvn";
	}

}
