package org.seamlets.identity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jboss.seam.annotations.security.permission.PermissionAction;
import org.jboss.seam.annotations.security.permission.PermissionTarget;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Permission implements Serializable {

	private Integer	permissionId;

	private String	target;

	private String	action;

	@Id
	@GeneratedValue
	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	@PermissionTarget
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@PermissionAction
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}