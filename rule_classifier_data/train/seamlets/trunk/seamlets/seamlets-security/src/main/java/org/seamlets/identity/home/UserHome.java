package org.seamlets.identity.home;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.seamlets.identity.*;

@Name("userHome")
@AutoCreate
public class UserHome extends EntityHome<User> {

	@In
	private EntityManager	securityEntityManager;

	@Override
	public EntityManager getEntityManager() {
		return securityEntityManager;
	}

	public void setUserUsername(String id) {
		setId(id);
	}

	public String getUserUsername() {
		return (String) getId();
	}

	@Override
	protected User createInstance() {
		User user = new User();
		return user;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public User getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
