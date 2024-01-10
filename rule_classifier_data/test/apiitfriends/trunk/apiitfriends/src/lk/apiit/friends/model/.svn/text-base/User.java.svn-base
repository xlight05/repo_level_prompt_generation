package lk.apiit.friends.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Abstract User Model, which defines common properties
 * of all users.
 * 
 * @author Yohan Liyanage
 * 
 * @version 12-September-2008
 * @since 04-September-2008
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class User implements Serializable {

	private static final long serialVersionUID = 4746289791188514501L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	protected String firstName;
	protected String lastName;
	@Column(unique=true)
	protected String email;
	
	// Login Details for User
	@OneToOne(cascade=CascadeType.ALL, optional=false, fetch=FetchType.EAGER)
	@PrimaryKeyJoinColumn
	protected LoginDetails loginDetails;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LoginDetails getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}

	
}
