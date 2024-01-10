package lk.apiit.friends.service.rules.impl;

import lk.apiit.friends.model.Staff;
import lk.apiit.friends.model.StaffType;
import lk.apiit.friends.model.User;
import lk.apiit.friends.service.rules.MembershipRule;

/**
 * A Rule which uses the type of Staff as the basis.
 * 
 * @author Yohan Liyanage
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public class StaffTypeRule implements MembershipRule {

	private static final long serialVersionUID = -3510705755280815446L;

	private StaffType type;
	
	
	/**
	 * Returns the Staff Type of this rule.
	 * 
	 * @return StaffType
	 */
	public StaffType getType() {
		return type;
	}

	/**
	 * Sets the StaffType for this Rule.
	 * 
	 * @param type staff type
	 */
	public void setType(StaffType type) {
		this.type = type;
	}

	/**
	 * Returns true if the given user is a Staff user
	 * who is of the specified StaffType.
	 * 
	 * @param user user
	 * @return true if success
	 */
	@Override
	public boolean check(User user) {
		
		if (user instanceof Staff) {
			if (type.equals(((Staff) user).getType())) {
				return true;
			}
		}
		return false;
	}

}
