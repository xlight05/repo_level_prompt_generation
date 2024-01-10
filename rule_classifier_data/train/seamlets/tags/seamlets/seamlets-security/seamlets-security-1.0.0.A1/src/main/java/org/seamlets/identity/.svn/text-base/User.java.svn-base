package org.seamlets.identity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;


@Entity
@NamedQueries( {
	@NamedQuery(name = "user.list.name", query = "FROM User ORDER BY lastName, firstName"),
	@NamedQuery(name = "user.list.name.filter", query = "FROM User WHERE lastName = :lastName ORDER BY lastName, firstName"),
	@NamedQuery(name = "user.list.username", query = "FROM User ORDER BY username"),
	@NamedQuery(name = "user.list.username.filter", query = "FROM User WHERE username = :username ORDER BY username")
})
public class User implements Serializable {

	private String		username;
	private String		password;
	private String		lastName;
	private String		firstName;
	private List<Role>	roles;

	@UserPrincipal
	@Id
	@Length(min = 5, max = 15)
	@Pattern(regex = "^\\w*$", message = "not a valid username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@UserPassword(hash = "sha")
	@NotNull
	@Lob
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@UserRoles
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = { @JoinColumn(name = "USERNAME") }, inverseJoinColumns = { @JoinColumn(name = "ID") })
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}