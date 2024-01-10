package org.seamlets.identity.list;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.identity.*;

import java.util.Arrays;

import javax.persistence.EntityManager;

@Name("permissionList")
public class PermissionList extends EntityQuery<UserPermission> {

	private static final String		EJBQL			= "select permission from Permission permission";

	private static final String[]	RESTRICTIONS	= {
			"lower(permission.action) like concat(lower(#{permissionList.permission.action}),'%')",
			"lower(permission.discriminator) like concat(lower(#{permissionList.permission.discriminator}),'%')",
			"lower(permission.recipient) like concat(lower(#{permissionList.permission.recipient}),'%')",
			"lower(permission.target) like concat(lower(#{permissionList.permission.target}),'%')", };

	@In
	private EntityManager			securityEntityManager;

	private UserPermission			permission		= new UserPermission();

	public PermissionList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	@Override
	public EntityManager getEntityManager() {
		return securityEntityManager;
	}

	public UserPermission getPermission() {
		return permission;
	}
}
