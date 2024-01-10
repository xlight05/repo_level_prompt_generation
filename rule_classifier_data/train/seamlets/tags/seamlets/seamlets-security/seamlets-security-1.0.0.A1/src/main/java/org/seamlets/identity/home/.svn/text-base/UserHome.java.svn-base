package org.seamlets.identity.home;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.seamlets.identity.*;

@Name("userHome")
public class UserHome extends EntityHome<User> {

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
