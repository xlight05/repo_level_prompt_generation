package lk.apiit.friends.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;


/**
 * User Login Details.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-September-2008
 * @since 04-September-2008
 */
@Entity
public class LoginDetails {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private User user;
	@Column(unique=true)
	private String username;
	private String passwordHash;
	private String securityQuestion;
	private String securityAnswer;
	private boolean locked = false;
	private boolean expired = false;
	private boolean enabled = false;
	
	// User Roles
	@OneToMany(mappedBy="loginRef", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private Set<UserRole> roles = new HashSet<UserRole>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
	
	public void addRole(RoleTypes role) {
		this.roles.add(new UserRole(role, this));
	}

	public void removeRole(RoleTypes role) {
		this.roles.remove(new UserRole(role, this));
	}
}
