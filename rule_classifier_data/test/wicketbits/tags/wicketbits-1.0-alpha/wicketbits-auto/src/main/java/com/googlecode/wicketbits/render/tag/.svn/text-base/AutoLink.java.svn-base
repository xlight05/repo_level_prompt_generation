/*  
 * Copyright (c) 2008 Edward Mostrom.
 * This file is licensed to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicketbits.render.tag;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicketbits.reflection.MemberInfo;

public class AutoLink extends AbstractAutoTag {

	public Component create(MarkupContainer container, ComponentTag tag,
			CompoundPropertyModel model, Object modelObject,
			MemberInfo memberInfo) {
		AbstractLink component = null;
		String classname = tag.getAttributes().getString("href");

		if (classname == null || "".equals(classname)) {
			return null;
		}

		try {
			final Class componentClass = container.getSession()
					.getClassResolver().resolveClass(classname);

			if (AbstractLink.class.isAssignableFrom(componentClass)) {
				// Create link component for anchor tag using the custom
				// class specified in the href
				final Constructor constructor = componentClass
						.getConstructor(new Class[] { String.class });
				component = (AbstractLink) constructor
						.newInstance(new Object[] { tag.getId() });

				// Set the model object so the link can use it to determine
				// what to do generically
				component.setModel(model);
				if (IFormSubmittingComponent.class
						.isAssignableFrom(componentClass)) {
					if (Form.class.isInstance(container)) {
						((Form) container)
								.setDefaultButton((IFormSubmittingComponent) component);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// If it is not a class name then ignore it because it is
			// probably just a regular link that LinkAutoResolver has
			// grabbed
			log.debug("Could not auto create link: class not found - "
					+ e.getMessage());
		} catch (SecurityException e) {
			throw new RuntimeException(
					"Could not instantiate component for wicket tag: "
							+ tag.getId(), e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(
					"Could not instantiate component for wicket tag: "
							+ tag.getId(), e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"Could not instantiate component for wicket tag: "
							+ tag.getId(), e);
		} catch (InstantiationException e) {
			throw new RuntimeException(
					"Could not instantiate component for wicket tag: "
							+ tag.getId(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					"Could not instantiate component for wicket tag: "
							+ tag.getId(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(
					"Could not instantiate component for wicket tag: "
							+ tag.getId(), e);
		}

		return component;
	}

}
