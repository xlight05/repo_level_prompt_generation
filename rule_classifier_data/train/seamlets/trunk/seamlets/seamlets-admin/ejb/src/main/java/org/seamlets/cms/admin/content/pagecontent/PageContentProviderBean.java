package org.seamlets.cms.admin.content.pagecontent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.seamlets.cms.entities.PageDefinition;
import org.seamlets.cms.entities.pagecomponent.CMSComponentHelper;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;


@Stateless
@Name("pageContentProvider")
public class PageContentProviderBean implements PageContentProvider {
	
	@PersistenceContext(unitName = "seamlets-components-datasource")
	private EntityManager entityManager;
	
	@In
	private PageDefinition selectedPage;
	
	@In
	private Map<String, List<CMSComponentHelper>>						componentCategories;
	
	public PageContentEntity getRootPageContentForPart(String viewPartId) {
		Query query = entityManager.createNamedQuery("pageContent.rootPageContentForViewPart");
		query.setParameter("pageDefinition", selectedPage);
		query.setParameter("viewPartId", viewPartId);
		try {
			return (PageContentEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Entry<String, List<CMSComponentHelper>>> getComponentCategories() {
		return new ArrayList<Entry<String,List<CMSComponentHelper>>>(componentCategories.entrySet());
	}
}
