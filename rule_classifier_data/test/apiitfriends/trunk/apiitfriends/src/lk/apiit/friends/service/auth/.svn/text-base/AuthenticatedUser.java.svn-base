package lk.apiit.friends.service.auth;

import lk.apiit.friends.model.User;
import lk.apiit.friends.model.UserRole;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

/**
 * Spring Security UserDetails implementation. This is used for security
 * services to identify authenticated users and their privileges.
 * 
 * @author Yohan Liyanage
 * 
 * @version 15-Sep-2008
 * @since 04-Sep-2008
 */
public class AuthenticatedUser implements UserDetails {

	private static final long serialVersionUID = -2277949943164736317L;

	private User user;
	
	/**
	 * Constructs an AuthenticatedUser object which wraps a given
	 * User.
	 * 
	 * @param user User instance
	 */
	public AuthenticatedUser(User user) {
		super();
		if (user==null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		this.user = user;
	}

	/**
	 * Returns the User instance represented by this
	 * AuthenticatedUser instance.
	 * 
	 * @return User instance
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GrantedAuthority[] getAuthorities() {
		
		GrantedAuthority[] authorities = new GrantedAuthority[user.getLoginDetails().getRoles().size()];
		
		int i=0;
		for (UserRole role : user.getLoginDetails().getRoles()) {
			authorities[i++] = createAuthority(role);
		}
		
		return authorities;
	}

	/**
	 * Creates the GrantedAuthority instance for given User Role.
	 * 
	 * @param role UserRole role
	 * @return GrantedAuthority (Spring Security)
	 */
	private GrantedAuthority createAuthority(UserRole role) {
		return new GrantedAuthorityImpl(role.getRoleName());
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPassword() {
		return user.getLoginDetails().getPasswordHash();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername() {
		return user.getLoginDetails().getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonExpired() {
		return ! user.getLoginDetails().isExpired();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonLocked() {
		return ! user.getLoginDetails().isLocked();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return user.getLoginDetails().isEnabled();
	}

}
