package org.seamlets.identity.list;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.identity.*;

import java.util.Arrays;

import javax.persistence.EntityManager;

@Name("roleList")
@AutoCreate
public class RoleList extends EntityQuery<Role> {

	private static final String		EJBQL			= "select role from Role role";

	private static final String[]	RESTRICTIONS	= { "lower(role.rolename) like concat(lower(#{roleList.role.rolename}),'%')", };

	@In
	private EntityManager			securityEntityManager;

	private Role					role			= new Role();

	public RoleList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	@Override
	public EntityManager getEntityManager() {
		return securityEntityManager;
	}

	public Role getRole() {
		return role;
	}
}
