package org.seamlets.cms.deploy.template;

import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.deployment.AbstractDeploymentHandler;
import org.jboss.seam.deployment.DeploymentMetadata;
import org.jboss.seam.deployment.DeploymentStrategy;
import org.jboss.seam.deployment.WarRootDeploymentStrategy;

public class DotTemplateDotXmlDeploymentHandler extends AbstractDeploymentHandler {

	private static DeploymentMetadata	DOTTEMPLATEDOTXML_SUFFIX_FILE_METADATA	= new DeploymentMetadata() {

																					public String getFileNameSuffix() {
																						return ".template.xml";
																					}
																				};

	public static final String			NAME									= "org.seamlets.cms.deployment.DotTemplateDotXmlDeploymentHandler";

	public String getName() {
		return NAME;
	}

	public static DotTemplateDotXmlDeploymentHandler instance() {
		if (Contexts.isEventContextActive()) {
			if (Contexts.getEventContext().isSet(WarRootDeploymentStrategy.NAME)) {
				DeploymentStrategy deploymentStrategy = (DeploymentStrategy) Contexts.getEventContext().get(
						WarRootDeploymentStrategy.NAME);
				Object deploymentHandler = deploymentStrategy.getDeploymentHandlers().get(NAME);
				if (deploymentHandler != null) {
					return (DotTemplateDotXmlDeploymentHandler) deploymentHandler;
				}
			}
			return null;
		}
		throw new IllegalStateException("Event context not active");
	}

	public DeploymentMetadata getMetadata() {
		return DOTTEMPLATEDOTXML_SUFFIX_FILE_METADATA;
	}

}
