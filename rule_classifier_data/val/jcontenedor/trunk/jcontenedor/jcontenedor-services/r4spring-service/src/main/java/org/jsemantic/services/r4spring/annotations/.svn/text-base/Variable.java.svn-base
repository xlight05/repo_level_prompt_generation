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
 * Variable annotation class. <br>
 * 
 * <p>
 * This annotation class must be used as an element of the
 * {@link SessionVariables} annotation class.. <br>
 * 
 * It represents a variable that can be passed to the SemanticSession.<br>
 * 
 * <p>
 * Example.<br>
 * 
 * <code>@Variable(key = "1", value = "10")</code>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.FIELD })
public @interface Variable {

	/**
	 * Key.
	 * 
	 * @return the string
	 */
	public String key();

	/**
	 * Value.
	 * 
	 * @return the string
	 */
	public String value();

}
