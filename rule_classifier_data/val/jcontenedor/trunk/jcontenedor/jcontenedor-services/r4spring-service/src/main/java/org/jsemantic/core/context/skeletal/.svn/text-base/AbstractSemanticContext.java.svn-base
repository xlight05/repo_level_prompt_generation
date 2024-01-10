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
package org.jsemantic.core.context.skeletal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import org.jsemantic.core.context.SemanticContext;

/**
 * Parent class for creating a Semantic Context. Provides a default
 * implementation for Session Variables and results based on a WeakHashMap.<br>
 */

public abstract class AbstractSemanticContext implements SemanticContext,
		Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The results. */
	private Map <Object, Object>results = new WeakHashMap<Object, Object>();

	/** The variables. */
	private Map<Object, Object> variables = new WeakHashMap<Object, Object>();

	/**
	 * Instantiates a new abstract semantic context.
	 */
	public AbstractSemanticContext() {
	}

	/**
	 * Instantiates a new abstract semantic context.
	 * 
	 * @param sessionVariables the session variables
	 * @param ctx the ctx
	 */
	public AbstractSemanticContext(Map<?, ?> sessionVariables) {
		addSessionVariables(sessionVariables);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#getSemanticSession()
	 */
	public abstract Object getRuleSession();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#addResult(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void addResult(Object id, Object o) {
		results.put(id, o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#getResults()
	 */
	public Collection<?> getResults() {
		Collection<Object> values = this.results.values();
		Collection<Object> detachedValues = new ArrayList<Object>();
		detachedValues.addAll(values);
		return detachedValues;
	}
	
	/* (non-Javadoc)
	 * @see org.jsemantic.core.context.SemanticContext#getResultMap()
	 */
	public Map<?,?> getResultMap() {
		Map<Object, Object> detached = new WeakHashMap<Object, Object>();
		detached.putAll(this.results);
		return detached;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#getSessionVariable(java.lang.Object)
	 */
	public Object getSessionVariable(Object id) {
		return this.variables.get(id);
	}

	/*
	 * AbstractSemanticContext (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#getResult(java.lang.Object)
	 */
	public Object getResult(Object id) {
		return results.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#setSessionVariable(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void setSessionVariable(Object id, Object variable) {
		this.variables.put(id, variable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#removeVariable(java.lang.Object)
	 */
	public void removeVariable(Object id) {
		this.variables.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#dispose()
	 */
	public void dispose() {
		clear();
		this.results = null;
		this.variables.clear();
		this.variables = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jsemantic.core.context.SemanticContext#clear()
	 */
	public void clear() {
		this.results.clear();
	}

	/**
	 * Adds the session variables.
	 * 
	 * @param sessionVariables the session variables
	 */
	private void addSessionVariables(Map<?,?> sessionVariables) {

		if (sessionVariables != null) {
			Iterator i = sessionVariables.keySet().iterator();
			while (i.hasNext()) {
				Object key = i.next();
				Object value = sessionVariables.get(key);
				setSessionVariable(key, value);
			}
		}

	}
}
