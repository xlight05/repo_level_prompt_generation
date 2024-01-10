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
package org.jsemantic.servicerules.drools.session.factory;

import org.jsemantic.core.context.SemanticContext;
import org.jsemantic.core.context.factory.SemanticContextFactory;
import org.jsemantic.core.knowledgedb.KnowledgeDB;
import org.jsemantic.core.session.SemanticSession;
import org.jsemantic.core.session.factory.skeletal.AbstractSemanticSessionFactory;
import org.jsemantic.servicerules.drools.context.SemanticContextFactoryImpl;
import org.jsemantic.servicerules.drools.rules.DroolsRuleSessionFactoryImpl;

/**
 * <p>
 * The Class SemanticSessionFactoryImpl is the  concrete implementation of
 * the SemanticSessionFactory interface that provides the stateless and stateful
 * instances of the SemanticSession.<br>
 * 
 * <p>
 * In this case, the SemanticSession wraps a Drool RuleSession.<br>
 */
public class SemanticSessionFactoryImpl extends AbstractSemanticSessionFactory {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SemanticSessionFactoryImpl.class);

	/**
	 * Instantiates a new semantic session factory impl.
	 * 
	 * @param rules the rules
	 */
	public SemanticSessionFactoryImpl(String rules) {
		super(rules, new DroolsRuleSessionFactoryImpl(rules),
				(SemanticContextFactory) new SemanticContextFactoryImpl());
	}

	/**
	 * Instantiates a new semantic session factory impl.
	 * 
	 * @param rules the rules
	 * @param stateful the stateful
	 */
	public SemanticSessionFactoryImpl(String rules, boolean stateful) {
		super(rules, stateful, new DroolsRuleSessionFactoryImpl(rules),
				(SemanticContextFactory) new SemanticContextFactoryImpl());
	}

	/**
	 * Gets the stateful session.
	 * 
	 * @param semanticContext the semantic context
	 * @param db the db
	 * 
	 * @return the stateful session
	 */
	protected SemanticSession getStatefulSession(
			SemanticContext semanticContext, KnowledgeDB knowledgeDB) {
		return SemanticSessionUtil.getStatefulSession(semanticContext,
				knowledgeDB);
	}

	/**
	 * Gets the stateless session.
	 * 
	 * @param semanticContext the semantic context
	 * @param db the db
	 * 
	 * @return the stateless session
	 */
	protected SemanticSession getStatelessSession(
			SemanticContext semanticContext, KnowledgeDB knowledgeDB) {
		return SemanticSessionUtil.getStatelessSession(semanticContext,
				knowledgeDB);
	}

}
