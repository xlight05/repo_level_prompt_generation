package org.seamlets.cms.deploy.pagecomponent;

import org.jboss.seam.deployment.AbstractDeploymentHandler;
import org.jboss.seam.deployment.DeploymentMetadata;

public class DotPageComponentDotXmlDeploymentHandler extends AbstractDeploymentHandler {

	private static DeploymentMetadata	DOTPAGECOMPONENTDOTXML_SUFFIX_FILE_METADATA	= new DeploymentMetadata() {
		public String getFileNameSuffix() {
			return ".pagecomponent.xml";
		}
	};
																					
	public static final String			NAME = "org.seamlets.cms.deployment.DotPageComponentDotXmlDeploymentHandler";

	public String getName() {
		return NAME;
	}


	public DeploymentMetadata getMetadata() {
		return DOTPAGECOMPONENTDOTXML_SUFFIX_FILE_METADATA;
	}
	
	@Override
	public void postProcess(ClassLoader classLoader) {
		// TODO Auto-generated method stub
		super.postProcess(classLoader);
	}

}
