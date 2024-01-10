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
package org.jsemantic.core.context;

import java.util.Collection;
import java.util.Map;
/**
 * Context that represents the current state of the Semantic Session and it's
 * relationship with the internal/external execution environment.<br>
 * 
 * The communication with the internal environment is made through the Session
 * Variables and the Results Objects. On the other hand, the communication with
 * the external environment is made possible by the External Context
 * {@link ExternalContext}.<br>
 * 
 * 
 * <p>
 * The Session Variables are not facts so they can not be evaluated by the rules
 * engine, but can be very useful as temporal objects, helpers, ... The Session
 * Variables can be added using the Semantic Context interface or adding a
 * Collection to the Semantic Session Factory.<br>
 * 
 * <p>
 * The results are stored in a <code>Map<Object, Object></code>, and can be
 * retrieved as a Collection of values or just one result using a Id key. Every
 * time a rule fires, one or more results can be added to the Context:
 * <code>addResult(Object id, Object result)</code></br>
 * 
 * <p>
 * The External Context is not related to the Semantic Session (so is created
 * outside of it). It can be just a collection of objects, services, etc.. For
 * example, when a rule fires a service can be accessed through the External
 * Context and execute a business method.<br>
 */
public interface SemanticContext {

	/** The SESSION_CONTEXT. */
	public static String SESSION_CONTEXT = "ctx";

	/**
	 * Adds the result from the execution of a rule.
	 * 
	 * @param id the id
	 * @param result the result the
	 */
	public void addResult(Object id, Object result);

	/**
	 * Gets the result from the execution of a rule.
	 * 
	 * @param id the id
	 * 
	 * @return the result
	 */
	public Object getResult(Object id);

	/**
	 * Gets all the results from the execution of all rules.<br>
	 * <p>
	 * Returns a detached collection of results.
	 * 
	 * @return the results
	 */
	public Collection<?> getResults();
	
	/**
	 * Gets all the map result from the execution of all rules.<br>
	 * <p>
	 * Returns a detached Map of results.
	 * 
	 * @return the results
	 */
	
	public Map<?, ?> getResultMap();
	
	
	/**
	 * Set a session variable.
	 * 
	 * @param id the id
	 * @param variable the variable
	 */
	public void setSessionVariable(Object id, Object variable);

	/**
	 * Get a session variable.
	 * 
	 * @param id the id
	 * 
	 * @return the session variable
	 */
	public Object getSessionVariable(Object id);

	/**
	 * Removes the variable.
	 * 
	 * @param id the id
	 */
	public void removeVariable(Object id);

	/**
	public void setExternalContext(ExternalContext ctx);

	
	public ExternalContext getExternalContext();
	*/

	/**
	 * Gets the rule session.
	 * 
	 * @return the rule session
	 */
	public Object getRuleSession();

	/**
	 * Clears the properties and results.
	 */
	public void clear();

	/**
	 * Dispose the context.
	 */
	public void dispose();

}
