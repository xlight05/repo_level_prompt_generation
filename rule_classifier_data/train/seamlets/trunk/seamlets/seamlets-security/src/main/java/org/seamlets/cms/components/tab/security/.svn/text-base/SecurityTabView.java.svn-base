package org.seamlets.cms.components.tab.security;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.security.management.PasswordHash;
import org.seamlets.cms.annotations.TabView;
import org.seamlets.identity.Role;
import org.seamlets.identity.home.PermissionHome;
import org.seamlets.identity.home.RoleHome;
import org.seamlets.identity.home.UserHome;
import org.seamlets.identity.list.RoleList;

@Stateful
@Name("securityTab")
@TabView(tabId = "security", tabLableKey = "tab.security", tabViewId = "securityTab.xhtml")
public class SecurityTabView extends org.seamlets.cms.tab.TabView implements ISecurityTabView {

	private static final String	LIST	= "list";
	private static final String	EDIT	= "edit";
	private static final String	CREATE	= "create";

	@RequestParameter
	private String				username;

	@RequestParameter
	private Long				roleId;

	@RequestParameter
	private Integer				permissionId;

	@In
	private UserHome			userHome;

	@In
	private RoleList			roleList;

	@In
	private RoleHome			roleHome;

	@In
	private PermissionHome		permissionHome;

	@In
	private FacesMessages		facesMessages;

	@In
	private PasswordHash		passwordHash;

	private String				viewStateUser;
	private String				viewStateRole;
	private String				viewStatePermission;

	private String				password;
	private String				confirmPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	private String	selectedTab;

	@Create
	public void create() {
		viewStateUser = LIST;
		viewStateRole = LIST;
		viewStatePermission = LIST;
	}

	@Destroy
	@Remove
	public void remove() {
	}

	@Override
	public String getViewStateUser() {
		return viewStateUser;
	}

	@Override
	public String getViewStateRole() {
		return viewStateRole;
	}

	@Override
	public String getViewStatePermission() {
		return viewStatePermission;
	}

	@Override
	public String getSelectedTab() {
		return selectedTab;
	}

	@Override
	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	@Override
	public void queryUser() {
	}

	@Override
	public void createUser() {
		userHome.clearInstance();
		viewStateUser = CREATE;
	}

	@Override
	public void editUser() {
		password = null;
		confirmPassword = null;
		userHome.setId(username);
		viewStateUser = EDIT;
	}

	@Override
	public void saveUser() {
		if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
			facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN, "addUser.empty.password");
			return;
		}

		if (password.equals("") && confirmPassword.equals("")
				&& (userHome.getInstance().getPassword() == null || userHome.getInstance().getPassword().equals(""))) {
			facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN, "addUser.no.password");
			return;
		}

		if (password.length() != 0) {
			String hashedPassword = passwordHash.generateSaltedHash(password, userHome.getInstance().getUsername(),
					PasswordHash.ALGORITHM_SHA);

			userHome.getInstance().setPassword(hashedPassword);
		}

		if (userHome.isManaged())
			userHome.update();
		else
			userHome.persist();

		viewStateUser = LIST;
	}

	@Override
	public void removeUser() {
		viewStateUser = LIST;
	}

	@Override
	public void cancelUserEdit() {
		userHome.clearInstance();
		viewStateUser = LIST;
	}

	@Override
	public List<Role> getAvailableGroups() {
		roleList.setOrderDirection("asc");
		roleList.setOrderColumn("rolename");

		List<Role> availableGroups = roleList.getResultList();
		availableGroups.removeAll(userHome.getInstance().getRoles());
		return availableGroups;
	}

	@Override
	public List<Role> getAssignedGroups() {
		return userHome.getInstance().getRoles();
	}

	@Override
	public void setAssignedGroups(List<Role> assignedGroups) {
		userHome.getInstance().setRoles(assignedGroups);
	}

	// @Override
	// public List<UserPermission> getAvailableRolePermissions() {
	// permissionList.setOrder("target, action asc");
	//
	// List<UserPermission> availablePermissions =
	// permissionList.getResultList();
	// availablePermissions.removeAll(roleHome.getInstance().getPermissions());
	// return availablePermissions;
	// }
	//
	// @Override
	// public List<RolePermission> getAssignedRolePermissions() {
	// return roleHome.getInstance().getPermissions();
	// }
	//
	// @Override
	// public void setAssignedRolePermissions(List<RolePermission>
	// assignedPermissions) {
	// roleHome.getInstance().setPermissions(assignedPermissions);
	// }

	@Override
	public void cancelPermissionEdit() {
		viewStatePermission = LIST;
	}

	@Override
	public void cancelRoleEdit() {
		viewStateRole = LIST;
	}

	@Override
	public void createPermission() {
		permissionHome.clearInstance();
		viewStatePermission = CREATE;
	}

	@Override
	public void createRole() {
		roleHome.clearInstance();
		viewStateRole = CREATE;
	}

	@Override
	public void editPermission() {
		permissionHome.setId(permissionId);
		viewStatePermission = EDIT;
	}

	@Override
	public void editRole() {
		roleHome.setId(roleId);
		viewStateRole = EDIT;
	}

	@Override
	public void queryPermission() {
	}

	@Override
	public void queryRole() {
	}

	@Override
	public void removePermission() {
		viewStatePermission = LIST;
	}

	@Override
	public void removeRole() {
		viewStateRole = LIST;
	}

	@Override
	public void savePermission() {
		if (false) {
			Conversation.instance().end();
		}
		viewStatePermission = LIST;
	}

	@Override
	public void saveRole() {
		if (false) {
			Conversation.instance().end();
		}
		viewStateRole = LIST;
	}
}
