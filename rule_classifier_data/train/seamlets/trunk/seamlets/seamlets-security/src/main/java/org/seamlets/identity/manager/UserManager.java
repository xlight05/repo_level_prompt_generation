package org.seamlets.identity.manager;

import static org.jboss.seam.ScopeType.EVENT;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;
import org.seamlets.identity.User;


@Scope(EVENT)
@Name("userManager")
public class UserManager implements Serializable {
	
	@In
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<User> listUsersByName() {
		Identity.instance().checkPermission(IdentityManager.USER_PERMISSION_NAME, IdentityManager.PERMISSION_READ);
		
		Query userQuery = entityManager.createNamedQuery("user.list.name");
		List<User> users = userQuery.getResultList();

		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> listUsersByUsername() {
		Identity.instance().checkPermission(IdentityManager.USER_PERMISSION_NAME, IdentityManager.PERMISSION_READ);
		
		Query userQuery = entityManager.createNamedQuery("user.list.username");
		List<User> users = userQuery.getResultList();

		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> listUsersByName(String lastNameFilter) {
		Identity.instance().checkPermission(IdentityManager.USER_PERMISSION_NAME, IdentityManager.PERMISSION_READ);
		
		Query userQuery = entityManager.createNamedQuery("user.list.name.filter");
		userQuery.setParameter("lastName", lastNameFilter);
		List<User> users = userQuery.getResultList();

		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> listUsersByUsername(String usernameFilter) {
		Identity.instance().checkPermission(IdentityManager.USER_PERMISSION_NAME, IdentityManager.PERMISSION_READ);
		
		Query userQuery = entityManager.createNamedQuery("user.list.username.filter");
		userQuery.setParameter("username", usernameFilter);
		List<User> users = userQuery.getResultList();

		return users;
	}

}
