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
package org.jsemantic.core.context.impl;

import java.io.Serializable;
import java.util.Map;

import org.jsemantic.core.context.skeletal.AbstractSemanticContext;

/**
 * The Class SemanticContextImpl.
 */
@SuppressWarnings("unchecked")
public class SemanticContextImpl extends AbstractSemanticContext implements
		Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant log. */
	public static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SemanticContextImpl.class);

	/** The rule session. */
	private Object ruleSession = null;

	/**
	 * Instantiates a new semantic context impl.
	 * 
	 * @param session the session
	 * @param ctx the ctx
	 * @param sessionVariables the session variables
	 */
	public SemanticContextImpl(Object session,
			Map<?, ?> sessionVariables) {
		super(sessionVariables);
		this.ruleSession = session;
	}

	/* (non-Javadoc)
	 * @see org.jsemantic.core.context.AbstractSemanticContext#dispose()
	 */
	public void dispose() {
		super.dispose();
		ruleSession = null;
	}

	/* (non-Javadoc)
	 * @see org.jsemantic.core.context.AbstractSemanticContext#getRuleSession()
	 */
	public Object getRuleSession() {
		return this.ruleSession;
	}
}
