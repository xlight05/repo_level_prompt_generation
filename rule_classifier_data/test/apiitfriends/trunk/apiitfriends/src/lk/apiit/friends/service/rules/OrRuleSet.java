package lk.apiit.friends.service.rules;

import lk.apiit.friends.model.User;

/**
 * A set of rules which evaluates as OR Operation. That is,
 * this rule set evaluates to true if any of the rules of this
 * set evaluates to true.
 * 
 * @author Yohan Liyanage
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public class OrRuleSet extends  AbstractRuleSet {

	private static final long serialVersionUID = 995818379230892617L;

	/**
	 * Returns true if any of the rules in this rule set
	 * evaluates to true. This method will not evaluate
	 * any further when a match is found.
	 * 
	 * @param User user
	 * @return true if success
	 */
	@Override
	public boolean check(User user) {
		
		// Check all rules
		for (MembershipRule rule : rules) {
			if (rule.check(user)) {
				// If any evaluate to true
				return true;
			}
		}
		
		// No matches
		return false;
	}

}
