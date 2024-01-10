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
package org.jsemantic.core;

import java.io.InputStream;

/**
 * This class is used to get the InputStream of the rules file.<br>
 * <p>
 * It works in both "stand alone" (
 * <code>RuleUtils.getResourceAsStream()</code>) and "enterprise" environments
 * (<code>Thread.currentThread().getContextClassLoader()</code>).</br>
 * 
 * <p>
 * The rules file should be located using the full class path notation:
 * <code>org/jsemantic/drl/test.drl</code>
 */

public class RuleUtils {

	/** The Constant log. */
	public static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(RuleUtils.class);

	/**
	 * Gets the input stream.
	 * 
	 * @param ruleFile the rules file location: <code>org/jsemantic/drl/test.drl</code>
	 * 
	 * @return the input stream
	 * 
	 * @throws RuleException the rule exception
	 */
	public static InputStream getInputStream(String ruleFile)
			throws RuleException {

		InputStream is = null;
		ClassLoader ctxLoader = Thread.currentThread().getContextClassLoader();

		if (ctxLoader != null) {
			is = ctxLoader.getResourceAsStream(ruleFile);
			if (log.isDebugEnabled()) {
				log
						.debug("CtxLoader present. Loading resources from classloader: "
								+ is);
			}

		} else {
			is = RuleUtils.class.getResourceAsStream(ruleFile);
			if (log.isDebugEnabled()) {
				log
						.debug("CtxLoader is not present. Loading resources from Class: "
								+ is);
			}
		}

		if (is == null) {
			String errorMsg = "Error Rules file: " + ruleFile
					+ " is not being loaded.  \n" + " ClassLoader: "
					+ ctxLoader + "	InputStream: " + is;
			log.error(errorMsg);
			throw new RuleException(errorMsg);
		}
		return is;

	}

}
