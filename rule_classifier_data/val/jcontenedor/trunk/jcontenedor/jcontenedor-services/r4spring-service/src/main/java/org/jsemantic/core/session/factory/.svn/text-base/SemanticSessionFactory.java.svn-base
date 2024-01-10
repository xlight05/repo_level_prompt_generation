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
package org.jsemantic.core.session.factory;

import java.util.Map;

import org.jsemantic.core.knowledgedbfactory.KnowledgeDBFactory;
import org.jsemantic.core.session.SemanticSession;
import org.jsemantic.core.session.factory.exception.SemanticSessionCreationException;

/**
 * A factory for creating SemanticSession objects using the method
 * getInstance(). The factory must be provided at least with a rules file.<br>
 * <p>
 * Configuration options.<br>
 * <li>
 * <ul>
 * Rules files: setRules() = org/jsemantic/session/test.drl
 * </ul>
 * <ul>
 * Type of session: stateful=true/false.
 * </ul>
 * <ul>
 * Cache of knowledge objects: cache=true/false.
 * </ul>
 * <ul>
 * Knowledge Database Factory: setKnowledgeDBFactory.
 * </ul>
 * <ul>
 * External Context: setExternalContext.
 * </ul>
 * <ul>
 * Session Variables: setSessionVariables.
 * </ul>
 * </li>
 */
public interface SemanticSessionFactory {
	
	/**
	 * Sets the knowledge factory.
	 * 
	 * @param knowledgeFactory the new knowledge factory
	 */
	public void setKnowledgeDBFactory(KnowledgeDBFactory knowledgeFactory);

	/**
	 * Sets the session variables.
	 * 
	 * @param sessionVariables the new session variables
	 */
	public void setSessionVariables(Map<?,?> sessionVariables);

	/**
	 * Gets the single instance of SemanticSessionFactory.
	 * 
	 * @return single instance of SemanticSessionFactory
	 * 
	 * @throws SemanticSessionCreationException the semantic session creation exception
	 */
	public SemanticSession getInstance()
			throws SemanticSessionCreationException;
}
