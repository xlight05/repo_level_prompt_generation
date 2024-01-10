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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * SessionVariables annotation class. <br>
 * 
 * <p>
 * This annotation class must be used in combination either of
 * {@link SemanticService} or {@link SemanticFactory}. <br>
 * 
 * It represents a Map of variables that can be passed to the
 * SemanticSession. The parameter needed is an array of {@link Variable} instances.<br>
 * 
 * <p>
 * Example.<br>
 * 
 * <code>@SessionVariables(variables = { @Variable(key = "1", value = "10") })</code>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE})
public @interface SessionVariables {

	/**
	 * Variables.
	 * 
	 * @return the variable[]
	 */
	Variable[] variables();

}
