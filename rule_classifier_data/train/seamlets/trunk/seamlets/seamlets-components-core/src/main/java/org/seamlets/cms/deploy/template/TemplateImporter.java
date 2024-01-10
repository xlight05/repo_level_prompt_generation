package org.seamlets.cms.deploy.template;

import java.io.Serializable;

import javax.ejb.Local;

import org.seamlets.cms.entities.template.TemplateEntity;
import org.seamlets.cms.xsd.template.Template;


@Local
public interface TemplateImporter<R extends TemplateEntity> extends Serializable {
	
	@SuppressWarnings("unchecked")
	public Class<? extends TemplateImporter> getType();
	
	public abstract R doImport(Template template, String viewId);
}
