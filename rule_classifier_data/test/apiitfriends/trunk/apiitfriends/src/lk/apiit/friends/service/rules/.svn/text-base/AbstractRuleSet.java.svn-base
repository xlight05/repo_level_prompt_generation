package lk.apiit.friends.service.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Class which provides common functionality for
 * RuleSets.
 * 
 * @author Yohan Liyanage
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public abstract class AbstractRuleSet implements MembershipRule {

	private static final long serialVersionUID = -8144971672244070429L;
	
	/**
	 * Set of Rules.
	 */
	protected List<MembershipRule> rules = new ArrayList<MembershipRule>();

	/**
	 * No-args constructor.
	 */
	public AbstractRuleSet() {
		super();
	}

	/**
	 * Adds a Rule to this RuleSet.
	 * 
	 * @param rule rule
	 */
	public void addRule(MembershipRule rule) {
		rules.add(rule);
	}

	/**
	 * Removes a rule from this RuleSet.
	 * 
	 * @param rule rule
	 */
	public void removeRule(MembershipRule rule) {
		rules.remove(rule);
	}

	/**
	 * Sets the rules for this rule set. This is for JPA use
	 * only. Use {@link #addRule(MembershipRule)}, 
	 * {@link #removeRule(MembershipRule)}
	 * instead.
	 * 
	 * @param rules rules
	 */
	@Deprecated
	public void setRules(List<MembershipRule> rules) {
		this.rules = rules;
	}

}