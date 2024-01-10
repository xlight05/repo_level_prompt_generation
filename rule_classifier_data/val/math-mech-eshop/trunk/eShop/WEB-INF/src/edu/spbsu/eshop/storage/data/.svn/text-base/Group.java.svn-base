package edu.spbsu.eshop.storage.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "groups")
public class Group extends EShopPersistanceUnit {

    @Column(name = "name")
    private String name;

    @Column
    private String stringPermissions = "";

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    /**
     * @deprecated Use {@link getPermissions} method instead
     */

    @Deprecated
    public String getStringPermissions() {
	return stringPermissions;
    }

    @Deprecated
    public void setStringPermissions(String stringPermissions) {
	this.stringPermissions = stringPermissions;
    }

    /**
     * Gets set of permissions bounded to the current group
     * 
     * @return unmodifiable Set of permissions
     */

    @Transient
    public Set<Permission> getPermissions() {
	return stringToEnumSet(stringPermissions);
    }

    /**
     * Transforms String to set of {@link Permission} bounded to current group.
     * 
     * For example, if the string parameter equals 5 than group will have
     * WriteComment permission.
     * 
     * @param stringPermissions
     *            - the String representation of permissions
     * @return Set of permissions bounded to the current group
     */
    private Set<Permission> stringToEnumSet(String stringPermissions) {
	Set<Permission> permissions = new HashSet<Permission>();
	if (stringPermissions == null || stringPermissions.equals("")) {
	    return permissions;
	}
	Permission[] allPermissions = Permission.values();
	for (String permissionNumber : stringPermissions.split(",")) {
	    permissions.add(allPermissions[Integer.parseInt(permissionNumber)]);
	}
	return permissions;
    }

    @Transient
    public void setPermissions(Set<Permission> permissions) {
	this.stringPermissions = enumSetToString(permissions);
    }

    /**
     * Transforms set of {@link Permission} bounded to current group into
     * String.
     * 
     * For example, if a group has CreateUser and EditGroup permissions than the
     * string representation will be 0,1.
     * 
     * @param enumSet
     *            - the set of permissions
     * @return String representation of permissions
     */
    private String enumSetToString(Set<Permission> enumSet) {
	StringBuffer permissonsBuffer = new StringBuffer();
	for (Enum<?> e : enumSet) {
	    permissonsBuffer.append(e.ordinal()).append(",");
	}
	String permissionsNumbers = permissonsBuffer.toString();
	// Cuts last comma
	permissionsNumbers = permissionsNumbers.substring(0, permissionsNumbers
		.lastIndexOf(','));
	return permissionsNumbers;
    }

    /**
     * modification can toss user groups permissions. Be aware.
     * 
     * @author Hanhe
     * 
     */
    public static enum Permission {
	CreateUser, EditGroup, RemoveUser, SetUserGroup, EditProduct, WriteComment, DeleteComment, EditProductsPrice, AddCategories, RemoveCategories, EditUser, AddProduct, ListGroup, ListUsers,

    }
}
