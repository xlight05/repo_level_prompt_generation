package org.seamlets.cms.list;

import java.util.Arrays;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.cms.entities.Application;

@AutoCreate
@Name("applicationList")
public class ApplicationList extends EntityQuery<Application> {

	private static final String		EJBQL			= "select application from Application application WHERE application.deleted IS NULL";

	private static final String[]	RESTRICTIONS	= {
			"lower(application.lastAccesUser) like concat(lower(#{applicationList.application.lastAccesUser}),'%')",
			"lower(application.name) like concat(lower(#{applicationList.application.name}),'%')", };

	@In
	private EntityManager			componentsEntityManager;

	private Application				application		= new Application();

	public ApplicationList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	@Override
	public EntityManager getEntityManager() {
		return componentsEntityManager;
	}

	public Application getApplication() {
		return application;
	}
}
