package org.seamlets.identity.home;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.seamlets.identity.*;

@Name("permissionHome")
public class PermissionHome extends EntityHome<Permission> {

	public void setPermissionPermissionId(Integer id) {
		setId(id);
	}

	public Integer getPermissionPermissionId() {
		return (Integer) getId();
	}

	@Override
	protected Permission createInstance() {
		Permission permission = new Permission();
		return permission;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Permission getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
