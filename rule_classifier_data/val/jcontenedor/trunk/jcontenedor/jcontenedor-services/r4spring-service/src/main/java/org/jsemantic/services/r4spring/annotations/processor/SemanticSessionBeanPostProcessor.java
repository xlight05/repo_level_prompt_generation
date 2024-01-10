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

import org.jsemantic.services.r4spring.annotations.SemanticService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Spring BeanPostProcessor that process the semantic annotations that a service
 * may have. If these annotations are found, it will inject a
 * SemanticSessionFactory in the service.<br>
 * 
 * <p>
 * 
 * <code>
 * <beans>
 * 	<bean id="beanPostProcessor" class="org.jservicerules.support.spring.SemanticSessionBeanPostProcessor"/>
 * 	<bean id="invoiceService" class="org.jsemantic.servicerules.examples.energy.services.InvoiceService"/>
 * </beans> 
 *</code>
 */

public class SemanticSessionBeanPostProcessor implements BeanPostProcessor {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {

		if (bean.getClass().isAnnotationPresent(SemanticService.class)) {
			SemanticServiceInterceptor interceptor = new SemanticServiceInterceptor();
			interceptor.intercept(bean);
			return bean;
		}
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
