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
package org.jsemantic.services.r4spring.annotations;

import java.lang.annotation.*;

/**
 * <p>
 * SemanticFactory annotation class.<br>
 * 
 * This is the main annotation class for the SemanticSessionFactory
 * configuration. It's compulsory to use this annotation before any other one
 * can be used ({@link SpringExternalContext} , {@link SessionVariables}..).
 * This annotation only can be used at a Field level.<br>
 * 
 * <p>
 * 
 * In order to configure a SemanticSessionFactory a rules file must be provided.
 * The SemanticSessionFactory is stateless by default. To configure a stateful
 * SemanticService the stateless parameter must be set to false.<br>
 * 
 * <p>
 * Example.<br>
 * 
 * <code>@SemanticFactory(rulesFile="org/jsemantic/support/examples/energy/tariff.drl")</code>
 *                                                                                <br>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface SemanticFactory {

	/**
	 * Rules file.
	 * 
	 * @return the string
	 */
	public String rulesFile();

	/**
	 * Stateless.
	 * 
	 * @return true, if successful
	 */
	public boolean stateless() default true;
}
