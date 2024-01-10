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
import org.jsemantic.core.knowledgedb.KnowledgeDB;
import org.jsemantic.core.session.SemanticSession;
import org.jsemantic.servicerules.drools.session.StatefulSemanticSessionImpl;
import org.jsemantic.servicerules.drools.session.StatelessSemanticSessionImpl;

/**
 * <p>
 * Util class to handle the Semantic Session instances. Each instance is bound
 * to the calling thread and reused (if called from the same thread) until the
 * session is disposed.<br>
 */
public class SemanticSessionUtil {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SemanticSessionUtil.class);

	/** The sessionHolder variable. */
	private static final ThreadLocal sessionHolder = new ThreadLocal();

	/**
	 * Removes the session from the session holder.
	 */
	public static void removeSession() {
		SemanticSessionUtil.sessionHolder.remove();
	}

	/**
	 * <p>
	 * Gets an instance of the stateless session. If there is an instance bound
	 * to the thread, that instance is returned instead of creating a new one.<br>
	 * 
	 * @param semanticContext the semantic context
	 * @param knowledgeDB the knowledge db
	 * 
	 * @return the stateless session
	 */
	public static SemanticSession getStatelessSession(
			SemanticContext semanticContext, KnowledgeDB knowledgeDB) {

		if (log.isDebugEnabled()) {
			log.debug("getSession (Stateless) required");
		}

		if (SemanticSessionUtil.sessionHolder.get() == null) {

			if (log.isDebugEnabled()) {
				log.debug("SemanticSessionUtil.sessionHolder == null");
			}

			final SemanticSession session = new StatelessSemanticSessionImpl(
					semanticContext, knowledgeDB);

			if (log.isDebugEnabled()) {
				log.debug("StatelessSemanticSession created: " + session);
			}

			SemanticSessionUtil.sessionHolder.set(session);
		}

		if (log.isDebugEnabled()) {
			log.debug("SemanticSessionUtil.sessionHolder != null "
					+ SemanticSessionUtil.sessionHolder.get());
		}

		return (SemanticSession) SemanticSessionUtil.sessionHolder.get();
	}

	/**
	 * <p>
	 * Gets an instance of the stateful session. If there is an instance bound
	 * to the thread, that instance is returned instead of creating a new one.<br>
	 * 
	 * @param semanticContext the semantic context
	 * @param knowledgeDB the knowledge db
	 * 
	 * @return the stateful session
	 */
	public static SemanticSession getStatefulSession(
			SemanticContext semanticContext, KnowledgeDB knowledgeDB) {

		if (log.isDebugEnabled()) {
			log.debug("getSession (Stateful) required: "
					+ "DroolsRuleSessionFactoryImpl");

		}

		if (SemanticSessionUtil.sessionHolder.get() == null) {

			final SemanticSession session = new StatefulSemanticSessionImpl(
					semanticContext, knowledgeDB);

			if (log.isDebugEnabled()) {
				log.debug("StatefulSemanticSession created: " + session);
			}

			SemanticSessionUtil.sessionHolder.set(session);
		}

		if (log.isDebugEnabled()) {
			log.debug("SemanticSessionUtil.sessionHolder != null "
					+ SemanticSessionUtil.sessionHolder.get());
		}

		return (SemanticSession) SemanticSessionUtil.sessionHolder.get();
	}

}
