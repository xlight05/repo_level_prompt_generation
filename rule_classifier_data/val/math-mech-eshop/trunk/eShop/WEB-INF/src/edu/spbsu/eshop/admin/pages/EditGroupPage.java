package edu.spbsu.eshop.admin.pages;

import java.util.HashSet;
import java.util.Set;

import edu.spbsu.eshop.beans.ErrorBean;
import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Group;
import edu.spbsu.eshop.storage.data.Group.Permission;

public class EditGroupPage extends AbstractAdminRoomPage {
    private Group group;
    private PermissionWarp[] groupPermissionsWarp;

    public EditGroupPage() {
	super("edit_group", null, "editGroup.jsf");
	this.setNecessaryPermsissions(Permission.EditGroup);
	this.setAction(new AbstractOnPageRequestAction() {

	    @Override
	    protected Object getObjectById(Long id) {
		return Storage.getGroup(id);
	    }

	    @Override
	    protected void setObject(Object object) {

		group = (Group) object;

		Permission[] permissions = Permission.values();
		int permissionsNumber = permissions.length;

		EditGroupPage.this.groupPermissionsWarp = new PermissionWarp[permissionsNumber];

		for (int i = 0; i < permissionsNumber; i++) {
		    boolean selected = group.getPermissions().contains(
			    permissions[i]);
		    groupPermissionsWarp[i] = new PermissionWarp(
			    permissions[i], selected);
		}
		ErrorBean.setErrorMessage(EditGroupPage.this.getSelectedGroup()
			.getName());

	    }

	});

    }

    public Group getSelectedGroup() {
	return group;
    }

    public PermissionWarp[] getGroupPermissionsWarp() {
	return groupPermissionsWarp;
    }

    public String saveGroup() {
	Set<Permission> permissions = new HashSet<Permission>();
	for (PermissionWarp permissionWarp : groupPermissionsWarp) {
	    if (permissionWarp.selected) {
		permissions.add(permissionWarp.permission);
	    }
	}
	group.setPermissions(permissions);
	Storage.merge(group);
	return "content";
    }

    public static class PermissionWarp {
	private boolean selected;
	Group.Permission permission;

	public PermissionWarp(Permission permission, boolean selected) {
	    this.permission = permission;
	    this.selected = selected;
	}

	public String getName() {
	    return permission.toString();
	}

	public boolean isSelected() {
	    return selected;
	}

	public void setSelected(boolean selected) {
	    this.selected = selected;
	}
    }

}
