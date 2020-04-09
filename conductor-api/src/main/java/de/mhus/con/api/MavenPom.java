package de.mhus.con.api;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.mhus.lib.core.MXml;

public class MavenPom {

	private Document doc;
	private Element root;

	public MavenPom(File pomFile) throws Exception {
		doc = MXml.loadXml(pomFile);
		root = doc.getDocumentElement();
	}
	
	
	
}
