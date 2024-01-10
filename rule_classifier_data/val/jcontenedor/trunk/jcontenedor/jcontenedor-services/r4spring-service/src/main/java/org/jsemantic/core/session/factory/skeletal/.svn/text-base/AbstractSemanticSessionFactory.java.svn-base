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
package org.jsemantic.core.session.factory.skeletal;

import java.util.Map;

import org.jsemantic.core.RuleSessionFactory;
import org.jsemantic.core.context.SemanticContext;
import org.jsemantic.core.context.factory.SemanticContextFactory;
import org.jsemantic.core.knowledgedb.KnowledgeDB;
import org.jsemantic.core.knowledgedbfactory.KnowledgeDBFactory;
import org.jsemantic.core.session.SemanticSession;
import org.jsemantic.core.session.factory.SemanticSessionFactory;
import org.jsemantic.core.session.factory.exception.SemanticSessionCreationException;

/**
 * A factory for creating AbstractSemanticSession objects.
 */
public abstract class AbstractSemanticSessionFactory implements
		SemanticSessionFactory {

	/** The Constant log. */
	public static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(AbstractSemanticSessionFactory.class);

	/** The rules. */
	private String rules = null;

	/** The is stateful. */
	private boolean isStateful = false;

	/** The knowledge factory. */
	private KnowledgeDBFactory knowledgeFactory = null;

	/** The context factory. */
	private SemanticContextFactory contextFactory = null;

	/** The rule session factory. */
	private RuleSessionFactory ruleSessionFactory = null;

	/** The session variables. */
	private Map<?,?> sessionVariables = null;

	/**
	 * Instantiates a new abstract semantic session factory.
	 * 
	 * @param rules the rules
	 * @param ruleSessionFactory the rule session factory
	 * @param contextFactory the context factory
	 */
	public AbstractSemanticSessionFactory(String rules,
			RuleSessionFactory ruleSessionFactory,
			SemanticContextFactory contextFactory) {
		this.rules = rules;
		this.ruleSessionFactory = ruleSessionFactory;
		this.contextFactory = contextFactory;
	}

	/**
	 * Instantiates a new abstract semantic session factory.
	 * 
	 * @param rules the rules
	 * @param stateful the stateful
	 * @param ruleSessionFactory the rule session factory
	 * @param contextFactory the context factory
	 */
	public AbstractSemanticSessionFactory(String rules, Boolean stateful,
			RuleSessionFactory ruleSessionFactory,
			SemanticContextFactory contextFactory) {
		this.rules = rules;
		this.isStateful = stateful.booleanValue();
		this.ruleSessionFactory = ruleSessionFactory;
		this.contextFactory = contextFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.session.factory.SemanticSessionFactory#setKnowledgeFactory(org.jsemantic.core.knowledgedbfactory.KnowledgeDBFactory)
	 */
	public void setKnowledgeDBFactory(KnowledgeDBFactory knowledgeFactory) {
		this.knowledgeFactory = knowledgeFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.session.factory.SemanticSessionFactory#getInstance()
	 */
	public SemanticSession getInstance()
			throws SemanticSessionCreationException {

		if (this.rules == null || this.rules.equals("")) {

			SemanticSessionCreationException e = new SemanticSessionCreationException(
					"Rule file paramater must have a valid location: "
							+ this.rules);

			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			log.error(e.getMessage(), e);
		}

		if (log.isDebugEnabled()) {
			log.debug("SemanticSession Instance requested.");
		}

		KnowledgeDB db = null;

		if (knowledgeFactory != null) {
			db = knowledgeFactory.getInstance();
		}

		SemanticContext semanticContext = createSemanticContext(db,
				ruleSessionFactory);

		return getSession(semanticContext, db);
	}

	/**
	 * Gets the stateless session.
	 * 
	 * @param semanticContext the semantic context
	 * @param db the db
	 * 
	 * @return the stateless session
	 */
	protected abstract SemanticSession getStatelessSession(
			SemanticContext semanticContext, KnowledgeDB db);

	/**
	 * Gets the stateful session.
	 * 
	 * @param semanticContext the semantic context
	 * @param db the db
	 * 
	 * @return the stateful session
	 */
	protected abstract SemanticSession getStatefulSession(
			SemanticContext semanticContext, KnowledgeDB db);

	/**
	 * Gets the session.
	 * 
	 * @param semanticContext the semantic context
	 * @param db the db
	 * 
	 * @return the session
	 */
	private SemanticSession getSession(SemanticContext semanticContext,
			KnowledgeDB db) {
		if (!isStateful) {
			return getStatelessSession(semanticContext, db);
		}
		return getStatefulSession(semanticContext, db);
	}

	/**
	 * Creates a new AbstractSemanticSession object.
	 * 
	 * @param db the db
	 * @param ruleSessionFactory the rule session factory
	 * 
	 * @return the semantic context
	 */
	private SemanticContext createSemanticContext(KnowledgeDB db,
			RuleSessionFactory ruleSessionFactory) {
		SemanticContext semanticContext = contextFactory.getContext(
				ruleSessionFactory, db, sessionVariables,
				this.isStateful);
		return semanticContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.session.factory.SemanticSessionFactory#setSessionVariables(java.util.Map)
	 */
	public void setSessionVariables(Map<?,?> sessionVariables) {
		this.sessionVariables = sessionVariables;
	}
}
