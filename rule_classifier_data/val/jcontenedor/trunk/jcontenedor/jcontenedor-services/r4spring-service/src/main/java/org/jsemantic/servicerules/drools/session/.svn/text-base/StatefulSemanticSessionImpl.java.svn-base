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
package org.jsemantic.servicerules.drools.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.drools.FactHandle;
import org.drools.StatefulSession;
import org.jsemantic.core.context.SemanticContext;
import org.jsemantic.core.knowledgedb.KnowledgeDB;
import org.jsemantic.core.session.SemanticSession;
import org.jsemantic.servicerules.drools.session.factory.SemanticSessionUtil;

/**
 * <p>
 * The SemanticSession is a central concept within the jSemantic
 * frameworks. It expands the concept of the Working Memory (or Rules Session)
 * implementing a few new features: <br>
 * <p>
 * <ul>
 * <li>Knowledge Database</li>
 * <li>Semantic Context</li>
 * <li>Session Variables</li>
 * <li>External Context</li>
 * </ul>
 * <br>
 * <p>
 * Also it reduces the interface of a WorkingMemory focusing in the most useful
 * methods.<br>
 * 
 * <p>
 * These features make easier the use of a rules engine in a standard business
 * application, helping to interact with the programming or application
 * environment (internal or external). Also it gets closer to the concept of an
 * "expert system" because of the Knowledge Database.<br>
 * 
 * <p>
 * There are two types of Semantic Sessions: Stateless and Stateful.<br>
 * 
 * Stateless Sessions are oriented to a static evaluation of the facts over the
 * time, getting always the same response for the same set of inputs. No state
 * is saved and the previous facts and results are discarded after each
 * invocation. Also the facts and objects can't be modified/retracted.<br>
 * 
 * <p>
 * On the contrary, the Stateful Session "store the state" of previous
 * invocations allowing to retract/modify facts. So the response could be
 * different over the time for the same set of facts.<br>
 * 
 * <p>
 * There is only one interface for both types of sessions, so the methods will
 * behave according to their type. It is a small interface, only with the most
 * useful methods you would need to handle your rules. Usually only 2 methods
 * wouldn't be working in aStateless Session: modifyInsert and
 * modifyRetract.<br>
 */

public class StatefulSemanticSessionImpl implements SemanticSession {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(StatefulSemanticSessionImpl.class);

	/** The ctx. */
	private SemanticContext ctx = null;

	/** The knowledge db. */
	private KnowledgeDB knowledgeDB = null;

	/**
	 * Instantiates a new stateful semantic session impl.
	 * 
	 * @param ctx the ctx
	 * @param db the db
	 */
	public StatefulSemanticSessionImpl(SemanticContext ctx, KnowledgeDB db) {
		this.ctx = ctx;
		this.knowledgeDB = db;
	}
	
	/**
	 * Insert a fact in the Working Memory. If the Session is Stateless
	 * fireAllRules() will be invoked. Returns a fact handle.
	 * 
	 * @param fact the fact
	 * 
	 * @return the object
	 * 
	 * @throws SemanticException the semantic exception the
	 */
	public Object insert(Object fact) {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.insert. Fact: " + fact);
		}

		Object results =  ((StatefulSession) ctx.getRuleSession()).insert(fact);

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.insert. Result: " + results);
		}
		return results;
	}

	/**
	 * Insert a collection of facts in the Working Memory. If the Session is
	 * Stateless fireAllRules() will be invoked. Returns a collection of facts
	 * handles.
	 * 
	 * @param facts the facts
	 * 
	 * @return the collection
	 * 
	 * @throws SemanticException the semantic exception
	 */
	public Collection insert(Collection facts) {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.insert. Facts: " + facts);
		}

		List temp = new ArrayList();

		Iterator i = facts.iterator();
		while (i.hasNext()) {
			Object handle = ((StatefulSession) ctx.getRuleSession()).insert(i.next());

			if (log.isDebugEnabled()) {
				log
						.debug("StatefulSemanticSessionImpl.insert. Executed. Handle: "
								+ handle);
			}

			temp.add(handle);
		}

		if (log.isDebugEnabled()) {
			log
					.debug("StatefulSemanticSessionImpl.insert. All Executed. Results: "
							+ temp);
		}

		return temp;
	}

	/**
	 * the
	 * Retracts a fact in the Working Memory using a fact handle to locate it.
	 * This handle is provided by the insertion method. Only works with Stateful
	 * Sessions.
	 * 
	 * @param handle the handle
	 * 
	 * @throws SemanticException the semantic exception
	 */
	public void modifyRetract(Object handle) {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.modifyRetract. Handle: "
					+ handle);
		}

		((StatefulSession) ctx.getRuleSession()).modifyRetract((FactHandle) handle);

		if (log.isDebugEnabled()) {
			log
					.debug("StatefulSemanticSessionImpl.modifyRetract.Executed, handle= "
							+ handle);
		}

	}

	/**
	 * Modifies a fact in the Working Memory using a fact handle to locate it.
	 * This handle is provided by the insertion method. Only works with Stateful
	 * Sessions.
	 * 
	 * @param handle the handle
	 * @param arg the arg
	 * 
	 * @throws SemanticException the semantic exception
	 */
	public void modifyInsert(Object handle, Object arg) {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.modiFyInsert. Handle: "
					+ handle + " Value: " + arg);
		}

		((StatefulSession) ctx.getRuleSession()).modifyInsert((FactHandle) handle, arg);

		if (log.isDebugEnabled()) {
			log
					.debug("StatefulSemanticSessionImpl.modiFyInsert. Executed, handle= "
							+ handle);
		}

	}

	/**
	 * Fire the set of rules associated with the Working Memory. Usually it will
	 * be executed automatically in Stateless Sessions.
	 * 
	 * @throws SemanticException the semantic exception
	 */
	public void fireAllRules() {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.fireAllRules.");
		}

		((StatefulSession) ctx.getRuleSession()).fireAllRules();

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.fireAllRules. Executed.");
		}
	}

	/**
	 * Invoke a set of rules using an object as a fact. The method fireAllRules
	 * will be executed automatically. Indicated for Stateless sessions.
	 * 
	 * @param fact
	 *            the fact
	 * 
	 * @return the collection
	 * 
	 * @throws SemanticExcep	tion
	 *             the semantic exception
	 */
	public Collection execute(Object fact) {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.execute. Fact collection: "
					+ fact);
		}

		this.ctx.clear();

		if (log.isDebugEnabled()) {
			log.debug("ctx cleared. " + ctx);
		}

		Object handle = ((StatefulSession) ctx.getRuleSession()).insert(fact);
		this.fireAllRules();

		((StatefulSession) ctx.getRuleSession()).modifyRetract((FactHandle) handle);

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.execute Result: "
					+ ctx.getResults());
		}

		return ctx.getResults();
	}

	/**
	 * Invoke a set of rules using a collection as a fact. The method
	 * fireAllRules will be executed automatically. Indicated for Stateless
	 * sessions.
	 * 
	 * @param fact
	 *            the fact
	 * 
	 * @return the collection
	 * 
	 * @throws SemanticException
	 *             the semantic exception
	 */
	public Collection execute(Collection facts) {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.execute. Fact collection: "
					+ facts);
		}

		this.ctx.clear();

		if (log.isDebugEnabled()) {
			log.debug("ctx cleared. " + ctx);
		}

		List temp = new ArrayList();

		Iterator i = facts.iterator();
		while (i.hasNext()) {
			Object fact = ((StatefulSession) ctx.getRuleSession()).insert(i.next());
			temp.add(fact);
		}

		this.fireAllRules();
		retractFacts(temp);

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.execute Result: "
					+ ctx.getResults());
		}

		return ctx.getResults();
	}

	/**
	 * Dispose the Semantic Session and free the resources. Also the semantic
	 * session is unbound from the thread.
	 */
	public void dispose() {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.dispose");
		}
				
		if (knowledgeDB != null) {
			knowledgeDB.dispose();
		}
		
		((StatefulSession) ctx.getRuleSession()).dispose();
		SemanticSessionUtil.removeSession();
		this.ctx.dispose();
		this.ctx = null;
		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.dispose executed.");
		}
	}

	
	private void retractFacts(List facts) {

		if (log.isDebugEnabled()) {
			log.debug("StatefulSemanticSessionImpl.retractFacts : " + facts);
		}

		Iterator j = facts.iterator();
		while (j.hasNext()) {
			Object fact = j.next();
			try {
				((StatefulSession) ctx.getRuleSession()).modifyRetract((FactHandle) fact);

				if (log.isDebugEnabled()) {
					log
							.debug("StatefulSemanticSessionImpl.retractFact. Exceuted. Fact:  "
									+ fact);
				}

			} catch (Exception e) {
				log.error(e);
				continue;
			}
		}
	}

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public SemanticContext getContext() {
		return this.ctx;
	}

	/**
	 * Gets the knowledge db.
	 * 
	 * @return the knowledge db
	 */
	public KnowledgeDB getKnowledgeDB() {
		return this.knowledgeDB;
	}
	
	/**
	 * Checks if is stateful.
	 * 
	 * @return true, if is stateful
	 */
	public boolean isStateful() {
		return true;
	}
}
