package lk.apiit.friends.service.rules.impl;

import lk.apiit.friends.model.User;
import lk.apiit.friends.service.rules.MembershipRule;

/**
 * A Membership Rule which defines a particular User Type (Student / Staff)
 * as the basis.
 *  
 * @author Yohan Liyanage
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public class UserTypeRule implements MembershipRule {

	private static final long serialVersionUID = -3649295844685949684L;

	private String userType;

	/**
	 * Returns UserType as String
	 * @return user type
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the UserType to the given UserType in String Form.
	 * Note that this is to be used only by JPA. Use the
	 * {@link #setUserType(Class)} version instead.
	 * 
	 * @param userType type of user
	 */
	@Deprecated
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * Sets the UserType to the type of the
	 * given User subclass type.
	 * 
	 * @param clazz class of a sub-type of User
	 */
	public void setUserType(Class<? extends User> clazz) {
		this.userType = clazz.getSimpleName();
	}

	/**
	 * Returns true if the given user is of the specified
	 * type.
	 */
	@Override
	public boolean check(User user) {
		return user.getClass().getSimpleName().equals(userType);
	}

}
