package org.seamlets.cms.provider;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;

@Name("pageContentEntityProvider")
public class PageContentEntityProvider implements Serializable {
	
	@In
	private EntityManager componentsEntityManager;
	
	public PageContentEntity loadPageContent(Integer id) {
		PageContentEntity pageContentEntity = componentsEntityManager.find(PageContentEntity.class, id);
		return pageContentEntity;
	}
}
