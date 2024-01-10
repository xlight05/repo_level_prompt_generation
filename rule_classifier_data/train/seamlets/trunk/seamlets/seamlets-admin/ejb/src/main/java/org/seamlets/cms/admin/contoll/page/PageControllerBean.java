package org.seamlets.cms.admin.contoll.page;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.security.Identity;
import org.seamlets.cms.admin.contoll.application.ApplicationController;
import org.seamlets.cms.admin.contoll.tree.NavigationTreeStateAdviser;
import org.seamlets.cms.entities.PageDefinition;
import org.seamlets.cms.entities.viewpart.ViewPart;


@Stateless
@AutoCreate
@Name("pageController")
public class PageControllerBean implements PageController {
	
	@In
	private PageDefinition			selectedPage;

	@In
	private Identity					identity;

	@In
	private ApplicationController		applicationController;

	@In
	private NavigationTreeStateAdviser	navigationTreeStateAdviser;

	@In
	private FacesMessages				facesMessages;

	@PersistenceContext(unitName = "seamlets-components-datasource")
	private EntityManager				entityManager;

	
	public void removePage() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_DELETE);

		if (!entityManager.contains(selectedPage))
			selectedPage = entityManager.merge(selectedPage);
		entityManager.remove(selectedPage);

		applicationController.refresh();
		navigationTreeStateAdviser.clearSelection();

		facesMessages.addFromResourceBundle(Severity.INFO, "page.remove");
	}

	public void updatePage() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_UPDATE);

		if (!entityManager.contains(selectedPage))
			selectedPage = entityManager.merge(selectedPage);
		
		checkViewParts();

		entityManager.persist(selectedPage);
		applicationController.refresh();
		navigationTreeStateAdviser.setSelectedTreeNode(selectedPage);

		facesMessages.addFromResourceBundle(Severity.INFO, "page.update");
	}

	private void checkViewParts() {
		Map<String, ViewPart> viewPartsMap = new HashMap<String, ViewPart>();
		
		for(ViewPart viewPart : selectedPage.getViewParts()) {
			viewPartsMap.put(viewPart.getName(), viewPart);
		}
		
		//TODO Delete old ViewParts not needed anymore
		for(org.seamlets.cms.xsd.template.ViewPart viewPart : selectedPage.getTemplate().getViewParts()) {
			if(!viewPartsMap.containsKey(viewPart.getName())) {
				ViewPart newViewPart = new ViewPart();
				newViewPart.setName(viewPart.getName());
				newViewPart.setSortorder(viewPart.getSortorder());
				newViewPart.setPageDefinition(selectedPage);
				
				entityManager.persist(newViewPart);
				selectedPage.getViewParts().add(newViewPart);
			}
		}
		
	}

}
