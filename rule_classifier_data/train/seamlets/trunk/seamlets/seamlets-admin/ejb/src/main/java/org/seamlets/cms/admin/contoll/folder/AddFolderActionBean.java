package org.seamlets.cms.admin.contoll.folder;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.seamlets.cms.admin.contoll.application.ApplicationController;
import org.seamlets.cms.admin.contoll.tree.NavigationTreeStateAdviser;
import org.seamlets.cms.entities.Application;
import org.seamlets.cms.entities.Domain;
import org.seamlets.cms.entities.NavigationFolder;


@Stateful
@Name("addFolderAction")
public class AddFolderActionBean implements AddFolderAction {

	private boolean						show;

	private NavigationFolder			navigationFolder;

	@In(required = false)
	private Domain						selectedDomain;

	@In(required = false)
	private NavigationFolder			selectedFolder;

	@In
	private ApplicationController		applicationController;

	@In
	private NavigationTreeStateAdviser	navigationTreeStateAdviser;

	@PersistenceContext(unitName = "seamlets-components-datasource", type = PersistenceContextType.EXTENDED)
	private EntityManager				entityManager;

	@Create
	public void create() {
	}

	@Remove
	@Destroy
	public void remove() {
	}

	public boolean isDisabled() {
		return selectedFolder == null && selectedDomain == null;
	}

	@Begin(nested = true)
	public void showDialog() {
		navigationFolder = new NavigationFolder();
		show = true;
	}

	public void addFolder() {
		Domain aktiveDomain;
		Application aktiveApplication;
		if (selectedDomain != null) {
			aktiveDomain = selectedDomain;
			aktiveApplication = selectedDomain.getApplication();
		} else if (selectedFolder != null) {
			aktiveDomain = selectedFolder.getDomain();
			aktiveApplication = aktiveDomain.getApplication();
		} else {
			throw new IllegalStateException(
					"A new navigationfolder can only be added to a domain or another navigationfolder. But both are null.");
		}

		navigationFolder.setApplication(aktiveApplication);
		navigationFolder.setDomain(aktiveDomain);

		if (selectedFolder != null) {
			navigationFolder.setParentNavigationFolder(selectedFolder);
			selectedFolder.getNavigationFolders().add(navigationFolder);
		} else {
			aktiveDomain.getRootFolders().add(navigationFolder);
		}

		entityManager.persist(navigationFolder);
		show = false;

		applicationController.refresh();
		navigationTreeStateAdviser.setSelectedTreeNode(navigationFolder);
	}

	@End
	public void hideDialog() {
		show = false;
	}

	public boolean isShow() {
		return show;
	}

	public NavigationFolder getNavigationFolder() {
		return navigationFolder;
	}

	public void setNavigationFolder(NavigationFolder navigationFolder) {
		this.navigationFolder = navigationFolder;
	}

	public String getTitle() {
		StringBuilder builder = new StringBuilder();

		if (selectedFolder != null) {
			builder.append("Folder '");
			builder.append(selectedFolder.getName());
			builder.append(" (ID:");
			builder.append(selectedFolder.getId());
			builder.append(")");
			builder.append(" in ");
			builder.append("Domain '");
			builder.append(selectedFolder.getDomain().getRootUrl());
			builder.append(" (ID:");
			builder.append(selectedFolder.getDomain().getId());
			builder.append(")'");
		} else {
			builder.append("Domain '");
			builder.append(selectedDomain.getRootUrl());
			builder.append(" (ID:");
			builder.append(selectedDomain.getId());
			builder.append(")'");
		}

		return builder.toString();
	}

}
