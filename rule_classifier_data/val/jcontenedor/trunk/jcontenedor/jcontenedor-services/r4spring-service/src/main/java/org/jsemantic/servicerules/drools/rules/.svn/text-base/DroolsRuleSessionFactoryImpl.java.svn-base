/*
 * Copyright 2007-2008, www.jsemantic.org, www.adolfoestevez.com,
 * http://semanticj2ee.blogspot.com/ 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsemantic.servicerules.drools.rules;

import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.StatelessSession;
import org.jsemantic.core.RuleException;
import org.jsemantic.core.RuleSessionFactory;

/**
 * <p>
 * This class provides the concrete instances of Drools RuleSessions (stateless
 * and stateful) from the RuleBase.<br>
 * <p>
 * The RuleBase is stored in the class and reused by the
 * SemanticSessionFactory as is an expensive object to create (it loads the rules files from disk).<br>
 */
public class DroolsRuleSessionFactoryImpl implements RuleSessionFactory {

	/** The log. */
	private final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(DroolsRuleSessionFactoryImpl.class);

	/** The rule base. */
	private RuleBase ruleBase = null;

	/** The helper. */
	private static DroolsRuleHelper helper = new DroolsRuleHelper();

	/**
	 * Instantiates a new drools rule session factory impl.
	 * 
	 * @param ruleFile the rule file
	 * 
	 * @throws RuleException the rule exception
	 */
	public DroolsRuleSessionFactoryImpl(String ruleFile) throws RuleException {

		ruleBase = helper.getRuleBase(ruleFile);

		if (log.isInfoEnabled()) {
			log.info("RuleBase adquired = " + ruleBase + ", RuleFile: "
					+ ruleFile);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.RuleSessionFactory#getStatelessRuleSession()
	 */
	public Object getStatelessRuleSession() {
		StatelessSession session = ruleBase.newStatelessSession();

		if (log.isDebugEnabled()) {
			log.info("StatelessSession session = " + session);
		}
		return session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.RuleSessionFactory#getStatefulRuleSession()
	 */
	public Object getStatefulRuleSession() {
		StatefulSession session = ruleBase.newStatefulSession();
		if (log.isDebugEnabled()) {
			log.info("StatefulSession session = " + session);
		}
		return session;
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		ruleBase = null;
		helper = null;
	}
}
