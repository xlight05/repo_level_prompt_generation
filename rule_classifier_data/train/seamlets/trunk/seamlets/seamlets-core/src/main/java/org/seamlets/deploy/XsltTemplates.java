package org.seamlets.deploy;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.deployment.FileDescriptor;
import org.jboss.seam.log.Log;

/**
 * 
 * Diese Klasse enthält einen Cache für Transformer-Objekte. Diese
 * repräsentieren die compilierten XSLT-Stylesheets und können später jederzeit
 * verwendet werden, um Transformer oder TransformerHandler zu erzeugen, da der
 * fertige Transformer nicht thread-safe ist, wird er in unserer Implementierung
 * jeweils nur ein mal verwendet und dann entsorgt. Die XSLT-Stylesheets werden
 * in allen WARs gesuch.
 * 
 * @author dzwicker
 * 
 */
@Name("org.seamlets.deployment.xsltTemplates")
@Startup
@Scope(ScopeType.APPLICATION)
@Install(precedence = Install.BUILT_IN)
public class XsltTemplates implements Serializable {

	private SAXTransformerFactory						saxFactory		= (SAXTransformerFactory) TransformerFactory
																				.newInstance();

	private Map<String, Templates>						templateStore	= new HashMap<String, Templates>();

	@Logger
	private Log											log;

	@In(required = false, value = "#{warRootDeploymentStrategy.deploymentHandlers['dotXsltDotTemplateDotXmlDeploymentHandler']}")
	private DotXsltDotTemplateDotXslDeploymentHandler	deploymentHandler;

	@Create
	public void startup() {
		
		String fileNameSuffix = deploymentHandler.getMetadata().getFileNameSuffix();
		if (deploymentHandler != null) {
			for (FileDescriptor is : deploymentHandler.getResources()) {
				try {
					Templates templates = loadTemplate(is.getUrl());
					String templateKey = is.getName();
					templateKey = templateKey.replace("WEB-INF/xslt/", "");
					templateKey = templateKey.replace("/", ".");
					templateKey = templateKey.replace(fileNameSuffix, "");
					templateKey = templateKey.substring(0, templateKey.length() - 1);
					
					templateStore.put(templateKey, templates);
					log.info("XsltTemplates: key: #0 from file: #1", templateKey, is.getName());
				} catch (TransformerConfigurationException e) {
					log.error("XsltTemplate not loaded", e);
				} catch (IOException e) {
					log.error("XsltTemplate not loaded", e);
				}
			}
		}
	}

	private Templates loadTemplate(URL transformerURL) throws TransformerConfigurationException, IOException {
		return saxFactory.newTemplates(new StreamSource(transformerURL.openStream()));
	}

	public Templates getTemplates(String key) {
		return templateStore.get(key);
	}

}
