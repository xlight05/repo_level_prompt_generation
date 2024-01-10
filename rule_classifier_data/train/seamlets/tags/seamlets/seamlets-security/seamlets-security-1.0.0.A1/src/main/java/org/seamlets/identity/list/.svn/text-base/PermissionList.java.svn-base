package org.seamlets.identity.list;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.identity.*;

import java.util.Arrays;

@Name("permissionList")
public class PermissionList extends EntityQuery<Permission> {

	private static final String		EJBQL			= "select permission from Permission permission";

	private static final String[]	RESTRICTIONS	= {
			"lower(permission.action) like concat(lower(#{permissionList.permission.action}),'%')",
			"lower(permission.discriminator) like concat(lower(#{permissionList.permission.discriminator}),'%')",
			"lower(permission.recipient) like concat(lower(#{permissionList.permission.recipient}),'%')",
			"lower(permission.target) like concat(lower(#{permissionList.permission.target}),'%')", };

	private Permission				permission		= new Permission();

	public PermissionList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Permission getPermission() {
		return permission;
	}
}
