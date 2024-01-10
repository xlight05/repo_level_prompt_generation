package lk.apiit.friends.web.struts2.actions;

import lk.apiit.friends.model.User;
import lk.apiit.friends.web.struts2.interceptors.AuthenticationAwareInterceptor;
import lk.apiit.friends.web.support.annotations.AuthenticationAware;

/**
 * Abstract Action which provides the functionality for
 * maintaining information about authenticated user.
 * 
 * @author Yohan Liyanage
 * @version 15-Sep-2008
 * @since 15-Sep-2008
 */
public abstract class AbstractAuthAwareAction extends AbstractBaseAction {

	private static final long serialVersionUID = -7521591503656261842L;

	/**
	 * User reference.
	 */
	protected User user;

	/**
	 * Returns the authenticated user, if available, or null.
	 * 
	 * @return Authenticated User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the authenticated user. This is to be used by the
	 * {@link AuthenticationAwareInterceptor}.
	 * 
	 * @param user authenticated user
	 */
	@AuthenticationAware
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
