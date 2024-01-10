package org.seamlets.xslt;

import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.seamlets.deploy.XsltTemplates;
import org.seamlets.exceptions.ViewIdNotManagedBySeamletsException;
import org.seamlets.page.SeamletsPage;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Diese Klasse holt SAX-Events aus dem durch das übergebene SeamletsDocumentMeta
 * definierte Dokument. Hierzu wird intern die Quelle noch einmal direkt
 * aufgerufen.
 * 
 * Jetzt wird hier das XSLT-Template aus dem Store geholt und verwendet, um die
 * vom der Quelle generierten SAX-Events zu transformieren. Das Ergebnis der
 * Transformation wird auf den ContentHandler geschickt.
 * 
 * @author dzwicker
 */
@Name("org.seamlets.xslt.documentTransformer")
@AutoCreate
@Install(precedence=Install.BUILT_IN)
public class DocumentTransformer implements Serializable {
	
	@Logger
	private Log log;

	@In(value = "org.seamlets.deployment.xsltTemplates")
	private XsltTemplates xsltTemplates;
	
	public void getTransformedCmsContent(SeamletsPage seamletsPage, ContentHandler handler)
			throws TransformerConfigurationException, SAXException, ViewIdNotManagedBySeamletsException {
		String templateKey = seamletsPage.getTemplateKey();
		Templates templates = xsltTemplates.getTemplates(templateKey);
		
		
		if(log.isDebugEnabled()) {
			SAXTransformerFactory saxFactory = (SAXTransformerFactory) TransformerFactory.newInstance();
			TransformerHandler transformerHandler = saxFactory.newTransformerHandler(templates);
			StringWriter sw = new StringWriter();
			Result result = new StreamResult(sw);
			transformerHandler.setResult(result);
			seamletsPage.getSAXContent(transformerHandler);
			log.debug("Transformed Seamlet:\n\n#0", sw.toString());
		}
		
		SAXTransformerFactory saxFactory = (SAXTransformerFactory) TransformerFactory.newInstance();
		TransformerHandler transformerHandler = saxFactory.newTransformerHandler(templates);
		SAXResult result = new SAXResult(handler);
		transformerHandler.setResult(result);
		seamletsPage.getSAXContent(transformerHandler);
	}

}
