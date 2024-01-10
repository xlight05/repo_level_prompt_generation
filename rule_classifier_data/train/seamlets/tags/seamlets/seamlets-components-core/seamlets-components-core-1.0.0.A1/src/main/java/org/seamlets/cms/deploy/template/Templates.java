package org.seamlets.cms.deploy.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.contexts.ServletLifecycle;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.core.Interpolator;
import org.jboss.seam.core.Expressions.ValueExpression;
import org.jboss.seam.deployment.FileDescriptor;
import org.jboss.seam.log.Log;
import org.jboss.seam.transaction.Transaction;
import org.jboss.seam.transaction.UserTransaction;
import org.jboss.seam.util.Resources;
import org.seamlets.cms.entities.template.TemplateEntity;
import org.seamlets.cms.exception.MissingAnnotationException;
import org.seamlets.cms.xsd.template.Template;
import org.seamlets.cms.xsd.template.ViewPart;
import org.xml.sax.SAXException;


@Name("org.seamlets.cms.templateMapper")
@Scope(ScopeType.APPLICATION)
@Startup
public class Templates implements Serializable {

	@Logger
	private Log										log;

	@In("#{deploymentStrategy.annotatedClasses['org.seamlets.cms.annotations.Template']}")
	private Set<Class<? extends TemplateEntity>>	templateClasses;

	@In
	private Expressions								expressions;

	@In
	private Interpolator							interpolator;
	
	@In(required = false)
	@Out
	private SortedSet<TemplateContainer>			templates;

	private Unmarshaller							unmarshaller;

	@In(required = false)
	@Out
	private Map<String, String>						confViewIds;

	@Create
	public void create() {
		confViewIds = new HashMap<String, String>();
		templates = new TreeSet<TemplateContainer>(new TemplateContainerComparator());
		try {
			JAXBContext jc = JAXBContext.newInstance("org.seamlets.cms.xsd.template");
			unmarshaller = jc.createUnmarshaller();
			Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
					Resources.getResource("template-1.0.xsd", ServletLifecycle.getServletContext()));
			unmarshaller.setSchema(schema);
		} catch (JAXBException e) {
			log.fatal("Unable to unmarshall template-xsd", e);
		} catch (SAXException e) {
			log.fatal("Unable to unmarshall template-xsd", e);
		}

		if (DotTemplateDotXmlDeploymentHandler.instance() != null) {
			for (FileDescriptor is : DotTemplateDotXmlDeploymentHandler.instance().getResources()) {
				handleTemplateXml(is);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void handleTemplateXml(FileDescriptor file) {
		String fileName = file.getName();
		String viewId = "/" + fileName.substring(0, fileName.length() - ".template.xml".length()) + ".xhtml";
		String importerClass = "UNKNOWN";
		String entityClass = "UNKNOWN";
		String displayName = "UNKNOWN";
		String confViewId = "NOT_CONFIGURATED";
		String viewparts = "NONE"; 

		InputStream stream = null;
		try {
			stream = file.getUrl().openStream();
		} catch (IOException exception) {
			// No-op
			log.debug("error", exception);
		}
		if (stream != null) {
			log.debug("reading template.xml file: " + fileName);
			try {
				JAXBElement<Template> jaxbElement = (JAXBElement<Template>) unmarshaller.unmarshal(stream);
				Template template = jaxbElement.getValue();

				displayName = template.getDisplayName();

				String importerEL = template.getImporter();
				ValueExpression<TemplateImporter> valueEL = expressions.createValueExpression(importerEL,
						TemplateImporter.class);
				TemplateImporter importer = valueEL.getValue();
				importerClass = importer.getType().getCanonicalName();

				UserTransaction transaction = Transaction.instance();
				transaction.begin();

				TemplateEntity entity = importer.doImport(template, viewId);
				
				transaction.commit();
				
				Class<? extends TemplateEntity> entitiyClazz = entity.getClass();
				if (!templateClasses.contains(entitiyClazz)) {
					throw new MissingAnnotationException("The class '" + entitiyClazz.getCanonicalName()
							+ "' is not annotated with @Template");
				}
				
				entityClass = entitiyClazz.getCanonicalName();
				
				List<ViewPart> viewParts = template.getViewparts().getViewpart();
				viewparts = StringUtils.join(viewParts, ", ");

				templates.add(new TemplateContainer(viewId, template, entity));

				if (!template.getConfViewId().isEmpty()) {
					confViewId = template.getConfViewId().get(0);
					if (confViewId.startsWith("#{")) {
						confViewId = interpolator.interpolate(confViewId);
					}
					confViewIds.put(viewId, confViewId);
				}

			} catch (JAXBException e) {
				log.error("Unable to unmarshall Template '#0'", e, fileName);
			} catch (NotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicMixedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				Resources.closeStream(stream);
			}
		}
		log.info("Template: #0, viewId: #1, importer: #2, entity: #3, confViewId: #4, viewParts: [#5]", displayName,
				viewId, importerClass, entityClass, confViewId, viewparts);
	}
}
