package org.seamlets.identity.home;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.seamlets.identity.*;

@Name("permissionHome")
@AutoCreate
public class PermissionHome extends EntityHome<UserPermission> {
	
	@In
	private EntityManager securityEntityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return securityEntityManager;
	}

	public void setPermissionPermissionId(Integer id) {
		setId(id);
	}

	public Integer getPermissionPermissionId() {
		return (Integer) getId();
	}

	@Override
	protected UserPermission createInstance() {
		UserPermission permission = new UserPermission();
		return permission;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public UserPermission getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
