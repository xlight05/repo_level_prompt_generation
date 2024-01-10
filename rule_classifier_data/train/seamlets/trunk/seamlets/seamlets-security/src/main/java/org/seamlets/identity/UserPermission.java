package org.seamlets.identity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.security.permission.PermissionUser;

@Entity
public class UserPermission extends Permission {

	private String recipient;

	@PermissionUser
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}