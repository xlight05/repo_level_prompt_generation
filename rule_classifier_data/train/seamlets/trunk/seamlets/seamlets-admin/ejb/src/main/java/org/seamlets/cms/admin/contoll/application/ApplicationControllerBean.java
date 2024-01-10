package org.seamlets.cms.admin.contoll.application;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.seamlets.annotation.CookieSavedState;
import org.seamlets.annotation.CookieValue;
import org.seamlets.annotation.LoadCookies;
import org.seamlets.annotation.SaveCookies;
import org.seamlets.cms.entities.Application;
import org.seamlets.cms.list.ApplicationList;

@Stateful
@AutoCreate
@CookieSavedState
@Name("applicationController")
public class ApplicationControllerBean implements ApplicationController {

	private Application				aktiveApplication;

	@Logger
	private Log						log;

	@In
	private AddApplicationAction	addApplicationAction;

	@In
	private FacesMessages			facesMessages;

	@In
	private Identity				identity;

	@In
	private ApplicationList			applicationList;

	@CookieValue(path = "/", maxAge = Integer.MAX_VALUE)
	private String					application;

	@PersistenceContext(unitName = "seamlets-components-datasource", type = PersistenceContextType.EXTENDED)
	private EntityManager			entityManager;

	@Create
	@LoadCookies
	@Begin(join = true)
	public void create() {
		List<Application> applications = applicationList.getResultList();
		if (applications.isEmpty()) {
			addApplicationAction.showDialog();
		} else {
			if (application != null && application.length() != 0) {
				applicationList.getApplication().setName(application);
				List<Application> result = applicationList.getResultList();
				if (!result.isEmpty()) {
					for (Application application2 : result) {
						if (application.equals(application2.getName())) {
							aktiveApplication = application2;
							log.info("AktivApplication set to '#0' from cookie for user '#1'", application, identity
									.getCredentials().getUsername());
							applicationList.getApplication().setName(null);
							return;
						}
					}
				}
			}
			applicationList.getApplication().setName(null);
			aktiveApplication = applications.get(0);
			application = aktiveApplication.getName();
			log.info("AktivApplication set to default for user '#0'", identity.getCredentials().getUsername());
		}
	}

	@Remove
	@Destroy
	@SaveCookies
	public void remove() {
	}

	public void removeApplication() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_DELETE);

		if (!entityManager.contains(aktiveApplication))
			aktiveApplication = entityManager.merge(aktiveApplication);

		entityManager.remove(aktiveApplication);

		facesMessages.addFromResourceBundle(Severity.INFO, "application.remove");
	}

	public void updateApplication() {
		identity.checkPermission(PERMISSION_NAME, PERMISSION_UPDATE);
		entityManager.persist(aktiveApplication);
		facesMessages.addFromResourceBundle(Severity.INFO, "application.update");
	}

	public Application getAktiveApplication() {
		return aktiveApplication;
	}

	@SaveCookies
	public void setAktiveApplication(Application aktiveApplication) {
		if (aktiveApplication == null)
			return;

		this.aktiveApplication = aktiveApplication;

		if (!entityManager.contains(this.aktiveApplication)) {
			this.aktiveApplication = entityManager.merge(this.aktiveApplication);
		}
		entityManager.refresh(this.aktiveApplication);

		application = this.aktiveApplication.getName();
	}

	public void refresh() {
		if (!entityManager.contains(aktiveApplication)) {
			aktiveApplication = entityManager.merge(aktiveApplication);
		}
		entityManager.refresh(aktiveApplication);
	}
}
