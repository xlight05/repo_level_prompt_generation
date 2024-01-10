package lk.apiit.friends.service.rules;


import lk.apiit.friends.model.User;

/**
 * A set of rules which will be evaluated as an AND operation.
 * That is, all rules with in this rule set must evaluate to
 * true for this rule set to evaluate to true.
 * 
 * @author Yohan Liyanage
 * 
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public class AndRuleSet extends AbstractRuleSet {

	private static final long serialVersionUID = 772595139851565978L;

	/**
	 * Returns true if the given user satisfies all rules defined
	 * for this Rule Set (AND Operation).
	 * <p>
	 * Checks for each rule defined as a part of this rule set
	 * and if all checks are success, returns true. If a single
	 * rule fails, it returns false immediately.
	 * 
	 * @param user user to check
	 * @return boolean indicating success, failure of check
	 */
	public boolean check(User user) {
		
		// Check each rule
		for (MembershipRule rule : rules) {
			if (! rule.check(user)) {
				// If any fails, check fails
				return false;
			}
		}
		
		// All success, check success
		return true;
	}
	
}
