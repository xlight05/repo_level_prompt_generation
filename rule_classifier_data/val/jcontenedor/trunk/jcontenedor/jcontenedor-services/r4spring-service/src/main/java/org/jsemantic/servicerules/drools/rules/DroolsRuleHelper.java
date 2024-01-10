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
package org.jsemantic.servicerules.drools.rules;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.jsemantic.core.RuleException;
import org.jsemantic.core.RuleUtils;

/**
 * <p>
 * Helper class to get a Drools RuleBase from a rules file or a collection of rules.
 * Each set of rules becomes a package that defines the RuleBase.
 */
public class DroolsRuleHelper {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(DroolsRuleHelper.class);

	/**
	 * Gets the rule base from a Map. The map contains a set of rules collection. Each
	 * collection becomes a rules package.
	 * 
	 * @param packages the packages
	 * 
	 * @return the rule base
	 * 
	 * @throws RuleException the rule exception
	 */
	public RuleBase getRuleBase(Map packages) throws RuleException {

		if (log.isDebugEnabled()) {
			log.debug("Packages = " + packages);
		}

		RuleBase ruleBase = RuleBaseFactory.newRuleBase();

		if (log.isDebugEnabled()) {
			if (ruleBase == null) {
				log.debug("new RuleBase = null");
			} else {
				log.debug("RuleBase = " + ruleBase);
			}
		}

		Iterator keys = packages.keySet().iterator();
		while (keys.hasNext()) {
			List rules = (List) packages.get((String) keys.next());
			Package pack = getPackage(rules);
			if (log.isDebugEnabled()) {
				if (pack == null) {
					log.debug("getPackage == null, " + "Rules = " + rules);
				} else {
					log.debug("getPackage = " + pack);
				}
			}

			try {
				ruleBase.addPackage(pack);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				if (log.isDebugEnabled()) {
					log.debug(e.getMessage(), e);
				}
				throw new RuleException(e.getMessage());
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("RuleBase = " + ruleBase);
		}
		if (log.isInfoEnabled()) {
			log.debug("RuleBase = " + ruleBase);
		}

		return ruleBase;
	}

	/**
	 * Gets the rule base from a collection of rules. Each collection becomes a rules
	 * package that defines the RuleBase.
	 * 
	 * @param rules the rules
	 * 
	 * @return the rule base
	 * 
	 * @throws RuleException the rule exception
	 */
	public RuleBase getRuleBase(List rules) throws RuleException {

		if (log.isDebugEnabled()) {
			log.debug("Rules = " + rules);
		}

		RuleBase ruleBase = RuleBaseFactory.newRuleBase();

		if (log.isDebugEnabled()) {
			if (ruleBase == null) {
				log.debug("new RuleBase = null");
			} else {
				log.debug("RuleBase = " + ruleBase);
			}
		}

		try {
			ruleBase.addPackage(getPackage(rules));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new RuleException(e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("RuleBase = " + ruleBase);
		}
		if (log.isInfoEnabled()) {
			log.debug("RuleBase = " + ruleBase);
		}

		return ruleBase;
	}

	/**
	 * Gets the rule base from a rules file. Each rules file becomes a rules
	 * package that defines the RuleBase.
	 * 
	 * @param rule the rule
	 * 
	 * @return the rule base
	 * 
	 * @throws RuleException the rule exception
	 */
	public RuleBase getRuleBase(String rule) throws RuleException {

		if (log.isDebugEnabled()) {
			log.debug("Rule = " + rule);
		}

		RuleBase ruleBase = RuleBaseFactory.newRuleBase();

		if (log.isDebugEnabled()) {
			if (ruleBase == null) {
				log.debug("new RuleBase = null");
			} else {
				log.debug("RuleBase = " + ruleBase);
			}
		}

		PackageBuilder builder = new PackageBuilder();
		Reader source = null;
		InputStream is = null;
		try {
			is = RuleUtils.getInputStream(rule);
			source = new InputStreamReader(is);
			builder.addPackageFromDrl(source);
			ruleBase.addPackage(builder.getPackage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new RuleException(e.getMessage());
		} finally {
			if (source != null)
				try {
					source.close();
					if (is != null)
						is.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
					if (log.isDebugEnabled()) {
						log.debug(e.getMessage(), e);
					}
				}
		}

		if (log.isDebugEnabled()) {
			log.debug("RuleBase = " + ruleBase);
		}
		if (log.isInfoEnabled()) {
			log.debug("RuleBase = " + ruleBase);
		}

		return ruleBase;
	}

	/**
	 * Gets the package from a collection of rules.
	 * 
	 * @param rules the rules
	 * 
	 * @return the package
	 * 
	 * @throws RuleException the rule exception
	 */
	private Package getPackage(List rules) throws RuleException {

		if (log.isDebugEnabled()) {
			log.debug("Rules = " + rules);
		}

		PackageBuilder builder = new PackageBuilder();

		Iterator i = rules.iterator();

		while (i.hasNext()) {
			String rule = (String) i.next();
			Reader source = null;
			InputStream is = null;
			try {
				is = RuleUtils.getInputStream(rule);
				source = new InputStreamReader(RuleUtils.getInputStream(rule));

				if (log.isDebugEnabled()) {
					log.debug("Source InputStreamReader = " + source);
				}

				builder.addPackageFromDrl(source);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				if (log.isDebugEnabled()) {
					log.debug(e.getMessage(), e);
				}
				throw new RuleException(e.getMessage());
			} finally {
				if (source != null)
					try {
						source.close();
						if (is != null)
							is.close();
					} catch (IOException e) {
						log.error(e.getMessage(), e);
						if (log.isDebugEnabled()) {
							log.debug(e.getMessage(), e);
						}
					}
			}
		}

		return builder.getPackage();
	}
}
