package org.seamlets.cms.admin.contoll.domain;

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
import org.seamlets.cms.entities.Domain;


@Stateless
@AutoCreate
@Name("domainContoller")
public class DomainControllBean implements DomainContoller {

	@In
	private Domain						selectedDomain;

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

	public void removeDomain() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_DELETE);
		
		if (!entityManager.contains(selectedDomain))
			selectedDomain = entityManager.merge(selectedDomain);
		entityManager.remove(selectedDomain);

		applicationController.refresh();
		navigationTreeStateAdviser.clearSelection();

		facesMessages.addFromResourceBundle(Severity.INFO, "domain.remove");
	}

	public void updateDomain() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_UPDATE);

		if (!entityManager.contains(selectedDomain))
			selectedDomain = entityManager.merge(selectedDomain);

		entityManager.persist(selectedDomain);
		entityManager.flush();
		applicationController.refresh();
		
		if(selectedDomain.getApplication().equals(applicationController.getAktiveApplication())) {
			navigationTreeStateAdviser.setSelectedTreeNode(selectedDomain);
		}
		else {
			navigationTreeStateAdviser.clearSelection();
		}

		facesMessages.addFromResourceBundle(Severity.INFO, "domain.update");
	}

}
