package org.seamlets.cms.deploy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.seamlets.cms.entities.PageDefinition;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;
import org.seamlets.cms.entities.template.TemplateEntity;

@Name("org.seamlets.cms.jaxbClassRepository")
@Scope(ScopeType.APPLICATION)
@Startup
public class JAXBClassRepository implements Serializable{
	
	private Set<Class<?>> jaxbContextClasses = new HashSet<Class<?>>();
	
	@In(value = "#{deploymentStrategy.annotatedClasses['org.seamlets.cms.annotations.PageContent']}", required = false)
	private Set<Class<? extends PageContentEntity>>	pageContentClasses;
	
	@In(value = "#{deploymentStrategy.annotatedClasses['org.seamlets.cms.annotations.Template']}", required = false)
	private Set<Class<? extends TemplateEntity>>	templateClasses;
	
	@Create
	public void create() {
		jaxbContextClasses.add(PageDefinition.class);
		jaxbContextClasses.addAll(pageContentClasses);
		jaxbContextClasses.addAll(templateClasses);
	}
	
	public Class<?>[] getRegisteredClasses() {
		return jaxbContextClasses.toArray(new Class<?>[jaxbContextClasses.size()]);
	}

	public static JAXBClassRepository instance() {
		return (JAXBClassRepository) Component.getInstance("org.seamlets.cms.jaxbClassRepository");
	}
}
