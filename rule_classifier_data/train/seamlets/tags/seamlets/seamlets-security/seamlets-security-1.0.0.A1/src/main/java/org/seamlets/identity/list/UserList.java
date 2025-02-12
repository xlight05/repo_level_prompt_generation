package org.seamlets.identity.list;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.identity.*;

import java.util.Arrays;

@Name("userList")
public class UserList extends EntityQuery<User> {

	private static final String		EJBQL			= "select user from User user";

	private static final String[]	RESTRICTIONS	= {
			"lower(user.username) like concat(lower(#{userList.user.username}),'%')",
			"lower(user.firstName) like concat(lower(#{userList.user.firstName}),'%')",
			"lower(user.lastName) like concat(lower(#{userList.user.lastName}),'%')", };

	private User					user			= new User();

	public UserList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public User getUser() {
		return user;
	}
}
