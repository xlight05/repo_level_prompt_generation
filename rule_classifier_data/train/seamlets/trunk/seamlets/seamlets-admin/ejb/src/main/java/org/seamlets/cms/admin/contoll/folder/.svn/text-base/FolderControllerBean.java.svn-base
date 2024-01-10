package org.seamlets.cms.admin.contoll.folder;

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
import org.seamlets.cms.entities.NavigationFolder;


@Stateless
@AutoCreate
@Name("folderController")
public class FolderControllerBean implements FolderController {

	@In
	private NavigationFolder			selectedFolder;

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

	public void removeFolder() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_DELETE);

		if (!entityManager.contains(selectedFolder))
			selectedFolder = entityManager.merge(selectedFolder);
		entityManager.remove(selectedFolder);

		applicationController.refresh();
		navigationTreeStateAdviser.clearSelection();

		facesMessages.addFromResourceBundle(Severity.INFO, "folder.remove");
	}

	public void updateFolder() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_UPDATE);

		if (!entityManager.contains(selectedFolder))
			selectedFolder = entityManager.merge(selectedFolder);

		entityManager.persist(selectedFolder);
		applicationController.refresh();
		navigationTreeStateAdviser.setSelectedTreeNode(selectedFolder);

		facesMessages.addFromResourceBundle(Severity.INFO, "folder.update");
	}

}
