package lk.apiit.friends.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * User Role representation. This class is used due to
 * persistence difficulties with 'enum' collections through
 * JPA.
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
@Entity
public class UserRole {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String roleName;
	
	@Transient
	private RoleTypes role;
	
	@ManyToOne
	private LoginDetails loginRef;
	
	public UserRole() {
		super();
	}
	
	/**
	 * Constructs a UserRole instance for given Role Type
	 * and LoginDetails instance
	 * 
	 * @param role Role Type
	 * @param login LoginDetails instance for User
	 */
	public UserRole(RoleTypes role, LoginDetails login) {
		this.roleName = "ROLE_" + role.toString();
		this.role = role;
		setLoginRef(login);
	}
	
	public String getRoleName() {
		return roleName;
	}

	public RoleTypes getRole() {
		return role;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
		this.role = Enum.valueOf(RoleTypes.class, roleName.substring(5));
	}

	public LoginDetails getLoginRef() {
		return loginRef;
	}


	public void setLoginRef(LoginDetails loginRef) {
		this.loginRef = loginRef;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof UserRole) {
			UserRole role = (UserRole) obj;
			if (this.roleName.equals(role.getRoleName())) {
				if (this.loginRef!=null) {
					this.loginRef.equals(role.getLoginRef());
				}
				else {
					if (role.getLoginRef()==null) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return roleName.hashCode();
	}

	@Override
	public String toString() {
		return roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
