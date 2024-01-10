package org.seamlets.cms.admin.contoll.page;

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
import org.seamlets.cms.admin.contoll.tree.NavigationTreeStateAdviser;
import org.seamlets.cms.entities.Domain;
import org.seamlets.cms.entities.NavigationFolder;
import org.seamlets.cms.entities.PageDefinition;


@Stateful
@Name("addPageAction")
public class AddPageActionBean implements AddPageAction {

	private boolean						show;

	private PageDefinition				pageDefinition;

	@In(required = false)
	private Domain						selectedDomain;

	@In(required = false)
	private NavigationFolder			selectedFolder;

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
		pageDefinition = new PageDefinition();
		if (selectedFolder != null) {
			pageDefinition.setDomain(selectedFolder.getDomain());
			pageDefinition.setParentFolder(selectedFolder);
		} else {
			pageDefinition.setDomain(selectedDomain);
			selectedDomain.getRootPages().add(pageDefinition);
		}
		show = true;
	}

	public void addPage() {
		entityManager.persist(pageDefinition);
		show = false;

		if (selectedFolder != null) {
			selectedFolder.getPages().add(pageDefinition);
			if(!entityManager.contains(selectedFolder))
				selectedFolder = entityManager.merge(selectedFolder);
			entityManager.refresh(selectedFolder);
		}
		else {
			if(!entityManager.contains(selectedDomain))
				selectedDomain = entityManager.merge(selectedDomain);
			entityManager.refresh(selectedDomain);
		}
		navigationTreeStateAdviser.setSelectedTreeNode(pageDefinition);
	}

	@End
	public void hideDialog() {
		show = false;
	}

	public boolean isShow() {
		return show;
	}

	public PageDefinition getPageDefinition() {
		return pageDefinition;
	}

	public void setPageDefinition(PageDefinition pageDefinition) {
		this.pageDefinition = pageDefinition;
	}

	public boolean isValid() {
		String displayName = pageDefinition.getDisplayName();
		String viewID = pageDefinition.getViewID();
		String title = pageDefinition.getTitle();

		return displayName != null && !displayName.equals("") && viewID != null && !viewID.equals("") && title != null
				&& !title.equals("");
	}

}
