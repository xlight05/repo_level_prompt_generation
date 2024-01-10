package org.seamlets.cms.admin.contoll.domain;

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


@Stateful
@Name("addDomainAction")
public class AddDomainActionBean implements AddDomainAction {

	private boolean						show;

	private Domain						domain;

	@In(required = false)
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

	@Begin(nested = true)
	public void showDialog() {
		domain = new Domain();
		show = true;
	}

	public void addDomain() {
		Application aktiveApplication = applicationController.getAktiveApplication();

		domain.setApplication(aktiveApplication);

		entityManager.persist(domain);
		show = false;
		
		applicationController.refresh();
		navigationTreeStateAdviser.setSelectedTreeNode(domain);
	}

	@End
	public void hideDialog() {
		show = false;
	}

	public boolean isShow() {
		return show;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

}
