package com.xebia.gclogviewer.util;

import java.io.CharArrayReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Content {
	@Test
	public void testReadXml() throws ParserConfigurationException,
			SAXException, IOException {
		String xmlstring = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Report >" +
				"<Person name=\"Jessey\"><Date s=\"2007/1/2\"></Date ></Person ></Report >";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		CharArrayReader characterStream = new CharArrayReader(xmlstring
				.toCharArray());
		InputSource is = new InputSource(characterStream);
		Document documentXML = documentBuilder.parse(is);
	}
}
