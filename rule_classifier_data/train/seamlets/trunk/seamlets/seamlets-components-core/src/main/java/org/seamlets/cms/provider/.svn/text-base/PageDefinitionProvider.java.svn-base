package org.seamlets.cms.provider;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.seamlets.cms.entities.PageDefinition;

@Name("pageDefinitionProvider")
public class PageDefinitionProvider implements Serializable {
	
	@In
	private EntityManager componentsEntityManager;
	
	public PageDefinition loadPageDefinition(Integer id) {
		PageDefinition pageDefinition = componentsEntityManager.find(PageDefinition.class, id);
		return pageDefinition;
	}
}
