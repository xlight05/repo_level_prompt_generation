package org.seamlets.cms.components.tab.security;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import org.seamlets.identity.Role;

@Local
public interface ISecurityTabView extends Serializable {

	public void create();

	public void remove();

	public String getViewStateUser();

	public String getViewStateRole();

	public String getViewStatePermission();
	
	public String getSelectedTab();

	public void setSelectedTab(String selectedTab);

	public String getPassword();

	public void setPassword(String password);

	public String getConfirmPassword();

	public void setConfirmPassword(String password);

	//USER
	public void queryUser();

	public void createUser();

	public void editUser();

	public void saveUser();

	public void removeUser();
	
	public void cancelUserEdit();
	
	public List<Role> getAvailableGroups();
	
	public List<Role> getAssignedGroups();
	
	public void setAssignedGroups(List<Role> assignedGroups);
	
//	public List<Permission> getAvailableUserPermissions();
//
//	public List<UserPermission> getAssignedUserPermissions();
//
//	public void setAssignedPermissions(List<UserPermission> assignedUserPermissions);
	
	//ROLES
	public void queryRole();

	public void createRole();

	public void editRole();

	public void saveRole();

	public void removeRole();
	
	public void cancelRoleEdit();
	
//	public List<Permission> getAvailableRolePermissions();
//
//	public List<RolePermission> getAssignedRolePermissions();
//
//	public void setAssignedRolePermissions(List<RolePermission> assignedPermissions);
	
	//PERMISSIONS
	public void queryPermission();

	public void createPermission();

	public void editPermission();

	public void savePermission();

	public void removePermission();
	
	public void cancelPermissionEdit();
}
