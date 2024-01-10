package org.seamlets.identity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.jboss.seam.annotations.security.management.RoleName;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "rolename" }) })
public class Role implements Serializable {

	private Long				id;

	@Column(unique = true)
	private String				rolename;
	private List<RolePermission>	permissions;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@RoleName
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@ManyToMany
	@Filter(name = "rolePermission", condition = "discriminator = 'r'")
	@JoinTable(name = "RolePermissions", joinColumns = @JoinColumn(name = "rolename"), inverseJoinColumns = @JoinColumn(name = "recipient"))
	@OrderBy("target, action")
	public List<RolePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<RolePermission> permissions) {
		this.permissions = permissions;
	}

}