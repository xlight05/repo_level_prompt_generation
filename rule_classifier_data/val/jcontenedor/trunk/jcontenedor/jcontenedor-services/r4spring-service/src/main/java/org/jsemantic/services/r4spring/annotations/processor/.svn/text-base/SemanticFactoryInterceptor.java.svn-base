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
package org.jsemantic.services.r4spring.annotations.processor;

import java.lang.reflect.Field;

import org.jsemantic.core.session.factory.SemanticSessionFactory;
import org.jsemantic.core.session.factory.exception.SemanticSessionCreationException;
import org.jsemantic.services.r4spring.annotations.SemanticFactory;
import org.jsemantic.services.r4spring.annotations.SessionVariables;
/**
 * <p>
 * Interceptor class that process the {@link SemanticFactory} annotation to
 * inject the SemanticSessionFactory.<br>
 * <p>
 * If the service is being created by a spring context the only action
 * needed is adding the class {@link SemanticSessionBeanPostProcessor} to the
 * context.<br>
 * <p>
 * <code>
 * 
 * 
 *<beans>
 *	<bean id="beanPostProcessor" class="org.jservicerules.support.spring.SemanticSessionBeanPostProcessor"/>
 *	<bean id="invoiceService" class="org.jsemantic.servicerules.examples.energy.services.InvoiceService"/>
 *</beans> 
 * </code>
 * <br>
 * 
 * <p> - If the service is being created just using code it would be needed to
 * call either {@link SemanticFactoryInterceptor} or
 * {@link SemanticServiceInterceptor} to process the annotations:<br>
 * 
 * <p>
 * 
 * <code>
 * 	MockService service= new MockService();
 * 
 *  SemanticServiceInterceptor interceptor = new SemanticServiceInterceptor();
 *
 *  interceptor.intercept(service);
 * </code> <br>
 */

public class SemanticFactoryInterceptor {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SemanticFactoryInterceptor.class);

	/** The helper. */
	private InterceptorHelper helper = new InterceptorHelper();

	/**
	 * Intercept the service injecting the SemanticSessionFactory using the
	 * {@link SemanticFactory} annotation.
	 * 
	 * @param service
	 *            the service
	 * 
	 * @throws SemanticSessionCreationException
	 *             the semantic session creation exception
	 */
	public void intercept(Object service)
			throws SemanticSessionCreationException {

		Class c = service.getClass();
		Field[] fields = c.getDeclaredFields();

		SemanticSessionFactory factory = null;
		Field annotatedField = null;
		for (Field field : fields) {
			if (field.isAnnotationPresent(SemanticFactory.class)) {
				SemanticFactory ann = field
						.getAnnotation(SemanticFactory.class);
				factory = helper.injectSemanticServiceFactory(ann, service,
						field);
				annotatedField = field;
				break;
			}
		}

		if (factory == null || annotatedField == null) {
			log.error("Error . Annotations haven't been found.");
			throw new SemanticSessionCreationException(
					"Error . Annotations haven't been found.");
		}

		if (annotatedField.isAnnotationPresent(SessionVariables.class)) {
			SessionVariables ann = (SessionVariables) annotatedField
					.getAnnotation(SessionVariables.class);
			helper.injectSessionVariables(factory, ann);
		}
	}
}
