package org.seamlets.cms.deploy.template;

import java.io.Serializable;

import org.seamlets.cms.entities.template.TemplateEntity;
import org.seamlets.cms.xsd.template.Template;



public class TemplateContainer implements Serializable {
	
	private final String viewId;
	private final Template template;
	private final TemplateEntity templateEntity;
	
	
	public TemplateContainer(String viewId, Template template, TemplateEntity templateEntity) {
		this.viewId = viewId;
		this.template = template;
		this.templateEntity = templateEntity;
	}
	
	public String getViewId() {
		return viewId;
	}
	
	public Template getTemplate() {
		return template;
	}
	
	public TemplateEntity getTemplateEntity() {
		return templateEntity;
	}
}
