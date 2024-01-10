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

/**
 * A factory interface for creating RuleSession objects.
 * <p>
 * This factory interface provides the underlying WorkingMemory (or RuleSession)
 * object.<br>
 * <p>
 * The WM usually comes in two flavours, stateless and stateful.<br>
 * <p>
 * Stateless WM are "one execution" oriented, some facts are provided and then
 * some rules are fired. No state is saved and the previous facts and the
 * results are discarded if it is executed again. Also the
 * facts and objects can't be modified/retracted<br>
 * <p>On the contrary, the Stateful WM object "store the state"
 * of previous executions allowing to retract/modify facts and objects.
 */
public interface RuleSessionFactory {

	/**
	 * Gets the stateless rule session.
	 * 
	 * @return the stateless rule session
	 */
	public Object getStatelessRuleSession();

	/**
	 * Gets the stateful rule session.
	 * 
	 * @return the stateful rule session
	 */
	public Object getStatefulRuleSession();

}
