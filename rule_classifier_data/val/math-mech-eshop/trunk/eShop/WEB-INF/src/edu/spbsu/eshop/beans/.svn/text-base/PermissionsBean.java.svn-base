package edu.spbsu.eshop.beans;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Group;

public class PermissionsBean {
    private SessionBean userBean;

    public boolean isUserHaveRightsToEditProduct() {
	Customer user = userBean.getUser();
	if (user == null)
	    return false;
	Set<Group.Permission> userPermissions = user.getGroup()
		.getPermissions();
	return userPermissions.contains(Group.Permission.EditProduct);
    }

    public Map<String, Boolean> getUserHasRightTo() {
	return new Map<String, Boolean>() {

	    @Override
	    public void clear() {

	    }

	    @Override
	    public boolean containsKey(Object key) {
		if (key instanceof String) {
		    return Group.Permission.valueOf((String) key) != null;
		}
		return false;

	    }

	    @Override
	    public boolean containsValue(Object value) {
		return Group.Permission.valueOf((String) value) != null;
	    }

	    @Override
	    public Set<java.util.Map.Entry<String, Boolean>> entrySet() {
		return null;
	    }

	    @Override
	    public Boolean get(Object key) {
		if (key instanceof String) {
		    Customer user = userBean.getUser();
		    Group userGroup = user.getGroup();
		    if (user == null)
			return false;
		    ErrorBean.setErrorMessage("" + userGroup.getPermissions());
		    Set<Group.Permission> userPermissions = userGroup
			    .getPermissions();
		    return userPermissions.contains(Group.Permission
			    .valueOf((String) key));
		}
		return false;
	    }

	    @Override
	    public boolean isEmpty() {
		return false;
	    }

	    @Override
	    public Set<String> keySet() {
		ErrorBean.setErrorMessage("set");
		return null;
	    }

	    @Override
	    public Boolean put(String key, Boolean value) {
		return false;
	    }

	    @Override
	    public void putAll(Map<? extends String, ? extends Boolean> m) {

	    }

	    @Override
	    public Boolean remove(Object key) {
		return false;
	    }

	    @Override
	    public int size() {
		return Group.Permission.values().length;
	    }

	    @Override
	    public Collection<Boolean> values() {
		return null;
	    }

	};
    }

    public void setUserBean(SessionBean userBean) {
	this.userBean = userBean;
    }
}
