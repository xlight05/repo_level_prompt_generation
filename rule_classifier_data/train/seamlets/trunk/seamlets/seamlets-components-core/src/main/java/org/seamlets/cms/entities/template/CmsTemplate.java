package org.seamlets.cms.entities.template;

import java.util.Map;

import javax.persistence.Transient;

import org.jboss.seam.Component;
import org.seamlets.cms.annotations.Template;
import org.seamlets.cms.entities.template.viewprovider.TemplateConfigurationViewProvider;


public abstract class CmsTemplate extends TemplateEntity {

	@SuppressWarnings("unchecked")
	@Transient
	public String getConfigurationView() {
		if (this instanceof TemplateConfigurationViewProvider) {
			return ((TemplateConfigurationViewProvider) this).getConfViewId();
		}

		Template templateAnnotation = this.getClass().getAnnotation(Template.class);
		if (templateAnnotation.confViewId().length() != 0) {
			return templateAnnotation.confViewId();
		}

		String confViewId = ((Map<String, String>) Component.getInstance("confViewIds")).get(templateViewId);
		if (confViewId != null && confViewId.length() != 0) {
			return confViewId;
		}
		return null;
	}
}
