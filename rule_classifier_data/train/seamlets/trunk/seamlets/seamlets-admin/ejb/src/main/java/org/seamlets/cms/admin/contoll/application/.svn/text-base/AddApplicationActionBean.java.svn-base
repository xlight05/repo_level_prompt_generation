package org.seamlets.cms.admin.contoll.application;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.seamlets.cms.entities.Application;
import org.seamlets.cms.list.ApplicationList;

@Stateful
@AutoCreate
@Name("addApplicationAction")
public class AddApplicationActionBean implements AddApplicationAction {

	private boolean					show;

	private boolean					startWorking;

	private Application				application;

	@In(required = false)
	private ApplicationController	applicationController;

	@In
	private ApplicationList			applicationList;

	@PersistenceContext(unitName = "seamlets-components-datasource", type = PersistenceContextType.EXTENDED)
	private EntityManager			entityManager;

	@Create
	public void create() {
		show = applicationList.getResultCount() == 0;
		startWorking = show;
		application = new Application();
	}

	@Remove
	@Destroy
	public void remove() {
	}

	public void showDialog() {
		application = new Application();
		show = true;
	}

	public void addApplication() {
		entityManager.persist(application);

		show = false;
		if (startWorking)
			applicationController.setAktiveApplication(application);

	}

	public void hideDialog() {
		show = false;
	}

	public boolean isShow() {
		return show;
	}

	public boolean isStartWorking() {
		return startWorking;
	}

	public void setStartWorking(boolean startWorking) {
		this.startWorking = startWorking;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
