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
package org.jsemantic.core.knowledgedb;

import java.util.Collection;
import java.util.Iterator;

/**
 * The Class KnowledgeDBImpl.
 */
public class KnowledgeDBImpl implements KnowledgeDB {

	/** The knowledge. */
	private Collection knowledge = null;

	/**
	 * Instantiates a new knowledge db impl.
	 * 
	 * @param knowledge the knowledge
	 */
	public KnowledgeDBImpl(Collection knowledge) {
		this.knowledge = knowledge;
	}

	/* (non-Javadoc)
	 * @see org.jsemantic.core.knowledgedb.KnowledgeDB#executeQuery(java.lang.String)
	 */
	public Iterator executeQuery(String query) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jsemantic.core.knowledgedb.KnowledgeDB#getKnowledge()
	 */
	public Iterator getKnowledge() {
		return this.knowledge.iterator();
	}

	/* (non-Javadoc)
	 * @see org.jsemantic.core.knowledgedb.KnowledgeDB#dispose()
	 */
	public void dispose() {
		if (knowledge != null) {
			this.knowledge.clear();
			this.knowledge = null;
		}

	}
}
