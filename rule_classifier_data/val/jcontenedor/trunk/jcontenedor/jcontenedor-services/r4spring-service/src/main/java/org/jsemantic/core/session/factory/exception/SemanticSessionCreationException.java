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
package org.jsemantic.core.session.factory.exception;

import org.jsemantic.core.session.exception.SemanticException;

/**
 * The Class SemanticSessionCreationException.
 */
public class SemanticSessionCreationException extends SemanticException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new semantic session creation exception.
	 * 
	 * @param msg the msg
	 */
	public SemanticSessionCreationException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new semantic session creation exception.
	 * 
	 * @param msg the msg
	 * @param e the e
	 */
	public SemanticSessionCreationException(String msg, Throwable e) {
		super(msg, e);
	}
}
