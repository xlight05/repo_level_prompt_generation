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
package org.jsemantic.servicerules.drools.context;

import java.util.Iterator;
import java.util.Map;

import org.drools.StatefulSession;
import org.drools.StatelessSession;
import org.drools.base.ReferenceOriginalGlobalExporter;
import org.jsemantic.core.RuleSessionFactory;
import org.jsemantic.core.context.SemanticContext;
import org.jsemantic.core.context.exception.SemanticContextCreationException;
import org.jsemantic.core.context.factory.SemanticContextFactory;
import org.jsemantic.core.context.impl.SemanticContextImpl;
import org.jsemantic.core.knowledgedb.KnowledgeDB;

/**
 * <p>
 * The Class SemanticContextFactoryImpl is the Drools version implementation of
 * the SemanticContextFactory.<br>
 * <p>
 * See the jSemanticCore java docs for more information.
 */
public class SemanticContextFactoryImpl implements SemanticContextFactory {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SemanticContextFactoryImpl.class);

	/**
	 * Gets the context.
	 * 
	 * @param rulesSessionFactory the rules session factory
	 * @param db the db
	 * @param externalCtx the external ctx
	 * @param sessionVariables the session variables
	 * @param isStateful the is stateful
	 * 
	 * @return the context
	 * 
	 * @throws SemanticContextCreationException the semantic context creation exception
	 */
	public SemanticContext getContext(RuleSessionFactory rulesSessionFactory,
			KnowledgeDB db,
			Map<?,?> sessionVariables, boolean isStateful)
			throws SemanticContextCreationException {

		SemanticContext ctx = null;

		Object rulesSession = null;

		if (!isStateful) {

			if (log.isDebugEnabled()) {
				log.debug("Creating StatelessSemanticContext...");
			}

			rulesSession = getStatelessSession(rulesSessionFactory);

		} else {
			if (log.isDebugEnabled()) {
				log.debug("Creating StatefulSemanticContext...");
			}
			rulesSession = getStatefulSession(rulesSessionFactory, db);
		}

		if (log.isDebugEnabled()) {
			log.debug("Creating SemanticContext...");
		}

		ctx = new SemanticContextImpl(rulesSession,
				sessionVariables);

		if (!isStateful) {
			((StatelessSession) rulesSession).setGlobal(
					SemanticContext.SESSION_CONTEXT, ctx);
		} else {
			((StatefulSession) rulesSession).setGlobal(
					SemanticContext.SESSION_CONTEXT, ctx);
		}

		if (log.isDebugEnabled()) {
			log.debug("Drools SemanticContext created: " + ctx);
		}

		return ctx;
	}

	/**
	 * Gets the stateless session.
	 * 
	 * @param rulesSessionFactory the rules session factory
	 * 
	 * @return the stateless session
	 */
	private StatelessSession getStatelessSession(
			RuleSessionFactory rulesSessionFactory) {
		StatelessSession session = (StatelessSession) rulesSessionFactory
				.getStatelessRuleSession();
		session.setGlobalExporter(new ReferenceOriginalGlobalExporter());
		return session;
	}

	/**
	 * Gets the stateful session.
	 * 
	 * @param rulesSessionFactory the rules session factory
	 * @param db the db
	 * 
	 * @return the stateful session
	 */
	private StatefulSession getStatefulSession(
			RuleSessionFactory rulesSessionFactory, KnowledgeDB db) {

		StatefulSession rulesSession = (StatefulSession) rulesSessionFactory
				.getStatefulRuleSession();
		if (db != null) {
			Iterator i = db.getKnowledge();

			while (i.hasNext()) {
				rulesSession.insert(i.next());
			}
		}

		return rulesSession;
	}

}
