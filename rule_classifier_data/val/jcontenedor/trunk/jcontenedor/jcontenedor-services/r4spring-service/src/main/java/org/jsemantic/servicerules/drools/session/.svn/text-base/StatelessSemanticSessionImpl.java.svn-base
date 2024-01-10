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

import org.drools.StatelessSession;
import org.jsemantic.core.context.SemanticContext;
import org.jsemantic.core.knowledgedb.KnowledgeDB;
import org.jsemantic.core.session.SemanticSession;
import org.jsemantic.core.session.exception.OperationNotSupported;
import org.jsemantic.core.session.exception.SemanticException;
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
public class StatelessSemanticSessionImpl implements SemanticSession {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(StatelessSemanticSessionImpl.class);

	/** The ctx. */
	private SemanticContext ctx = null;

	/** The knowledge db. */
	private KnowledgeDB knowledgeDB = null;

	/**
	 * Instantiates a new stateless semantic session impl.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param knowledgeDB
	 *            the knowledge db
	 */
	public StatelessSemanticSessionImpl(SemanticContext ctx,
			KnowledgeDB knowledgeDB) {
		this.ctx = ctx;
		this.knowledgeDB = knowledgeDB;
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
	 * @throws SemanticException
	 *             the semantic exception
	 */
	public Collection execute(Object fact) {

		if (log.isDebugEnabled()) {
			log.debug("StatelessSemanticSessionImpl.execute. Fact: " + fact);
		}

		Collection facts = createFacts(fact);

		ctx.clear();

		if (log.isDebugEnabled()) {
			log.debug("ctx cleared. " + ctx);
		}

		((StatelessSession) ctx.getRuleSession()).executeWithResults(facts);

		if (log.isDebugEnabled()) {
			log.debug("executeWithResults. Result: " + ctx.getResults());
		}

		facts.clear();
		facts = null;

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
	public Collection execute(Collection fact) {

		if (log.isDebugEnabled()) {
			log
					.debug("StatelessSemanticSessionImpl.execute. Facthrows HandleExceptiont collection: "
							+ fact);
		}

		ctx.clear();

		if (log.isDebugEnabled()) {
			log.debug("ctx cleared. " + ctx);
		}

		Collection facts = createFacts(fact);

		((StatelessSession) ctx.getRuleSession()).executeWithResults(facts);

		if (log.isDebugEnabled()) {
			log.debug("executeWithResults. Result: " + ctx.getResults());
		}

		facts.clear();
		facts = null;

		return ctx.getResults();
	}

	/**
	 * Dispose the Semantic Session and free the resources. Also the semantic
	 * session is unbound from the thread.
	 */
	public void dispose() {

		ctx.dispose();
		ctx = null;

		if (knowledgeDB != null) {
			knowledgeDB.dispose();
		}

		if (log.isDebugEnabled()) {
			log.debug("StatelessSemanticSession disposed");
		}
		SemanticSessionUtil.removeSession();
	}

	/**
	 * Insert a fact in the Working Memory. If the Session is Stateless
	 * fireAllRules() will be invoked. Returns a fact handle.
	 * 
	 * @param fact
	 *            the fact
	 * 
	 * @return the object
	 * 
	 * @throws SemanticException
	 *             the semantic exception the
	 */
	public Object insert(Object fact) {
		return this.execute(fact);
	}

	/**
	 * Insert a collection of facts in the Working Memory. If the Session is
	 * Stateless fireAllRules() will be invoked. Returns a collection of facts
	 * handles.
	 * 
	 * @param facts
	 *            the facts
	 * 
	 * @return the collection
	 * 
	 * @throws SemanticException
	 *             the semantic exception
	 */
	public Collection insert(Collection facts) {
		return this.execute(facts);
	}

	/**
	 * Operation Not Supported for StatelessSemanticSessions.
	 * 
	 * 
	 * @throws OperationNotSupported
	 *             the semantic exception
	 */
	public void fireAllRules() {
		throw new OperationNotSupported(
				"Operation not supporter in a Stateless Session. Use execute instead");
	}

	/**
	 * Operation Not Supported for StatelessSemanticSessions.
	 * 
	 * @param arg
	 *            the arg
	 * 
	 * @throws OperationNotSupported
	 *             the semantic exception
	 */
	public void modifyRetract(Object arg0) throws SemanticException {
		throw new OperationNotSupported(
				"Operation not supporter in a Stateless Session.");
	}

	/**
	 * Operation Not Supported for StatelessSemanticSessions.
	 * 
	 * @param handle
	 *            the handle
	 * @param arg
	 *            the arg
	 * 
	 * @throws OperationNotSupported
	 *             the semantic exception
	 */
	public void modifyInsert(Object arg0, Object arg1) throws SemanticException {
		throw new OperationNotSupported(
				"Operation not supporter in Stateless Session.");
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
		return knowledgeDB;
	}

	/**
	 * Creates the facts.
	 * 
	 * @param o
	 *            the o
	 * 
	 * @return the collection
	 */
	private Collection createFacts(Object o) {
		Collection facts = new ArrayList();

		if (knowledgeDB != null) {
			Iterator i = knowledgeDB.getKnowledge();
			while (i.hasNext()) {
				facts.add(i.next());
			}
		}

		if (o instanceof Collection) {
			facts.addAll((Collection) o);
		} else {
			facts.add(o);
		}
		return facts;
	}

	/**
	 * Checks if is stateful.
	 * 
	 * @return true, if is stateful
	 */
	public boolean isStateful() {
		return false;
	}

}
