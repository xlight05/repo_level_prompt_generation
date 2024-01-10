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
package org.jsemantic.core.session;

import java.util.Collection;

import org.jsemantic.core.context.SemanticContext;
import org.jsemantic.core.knowledgedb.KnowledgeDB;
import org.jsemantic.core.session.exception.SemanticException;

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
public interface SemanticSession {

	/**
	 * Invoke a set of rules using an object as a fact. The method fireAllRules
	 * will be executed automatically. Indicated for Stateless sessions.
	 * 
	 * @param fact the fact
	 * 
	 * @return the collection
	 * 
	 * @throws SemanticException the semantic exception
	 */
	public Collection<?> execute(Object fact) throws SemanticException;

	/**
	 * Invoke a set of rules using a collection as a fact. The method
	 * fireAllRules will be executed automatically. Indicated for Stateless
	 * sessions.
	 * 
	 * @param fact the fact
	 * 
	 * @return the collection
	 * 
	 * @throws SemanticException the semantic exception
	 */
	public Collection<?> execute(Collection<?> fact) throws SemanticException;

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
	public Object insert(Object fact) throws SemanticException;

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
	public Collection<?> insert(Collection<?> facts) throws SemanticException;

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
	public void modifyRetract(Object handle) throws SemanticException;

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
	public void modifyInsert(Object handle, Object arg)
			throws SemanticException;

	/**
	 * Fire the set of rules associated with the Working Memory. Usually it will
	 * be executed automatically in Stateless Sessions.
	 * 
	 * @throws SemanticException the semantic exception
	 */
	public void fireAllRules() throws SemanticException;

	/**
	 * Dispose the Semantic Session and free the resources.
	 */
	public void dispose();

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public SemanticContext getContext();
	
	/**
	 * Gets the knowledge db.
	 * 
	 * @return the knowledge db
	 */
	public KnowledgeDB getKnowledgeDB();
	
	
	/**
	 * Checks if is stateful.
	 * 
	 * @return true, if is stateful
	 */
	public boolean isStateful();
	
}
