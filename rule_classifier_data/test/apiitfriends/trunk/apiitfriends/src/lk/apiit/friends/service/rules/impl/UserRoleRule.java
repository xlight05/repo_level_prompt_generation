package lk.apiit.friends.service.rules.impl;

import lk.apiit.friends.model.RoleTypes;
import lk.apiit.friends.model.User;
import lk.apiit.friends.model.UserRole;
import lk.apiit.friends.service.rules.MembershipRule;

/**
 * A Rule which defines a particular UserRole as the
 * basis.
 * 
 * @author Yohan Liyanage
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public class UserRoleRule implements MembershipRule {

	private static final long serialVersionUID = 4000726281932814058L;

	private RoleTypes role;

	/**
	 * Returns the RoleType used by this Rule
	 * @return
	 */
	public RoleTypes getRole() {
		return role;
	}

	/**
	 * Sets the RoleType of this Rule
	 * @param role
	 */
	public void setRole(RoleTypes role) {
		this.role = role;
	}

	/**
	 * Checks a given user to see if the user
	 * possess the role specified in this rule.
	 * 
	 * @param user User
	 * @return true if success
	 */
	@Override
	public boolean check(User user) {
		for (UserRole r : user.getLoginDetails().getRoles()) {
			if (role.equals(r.getRole())) {
				return true;
			}
		}
		return false;
	}

}
