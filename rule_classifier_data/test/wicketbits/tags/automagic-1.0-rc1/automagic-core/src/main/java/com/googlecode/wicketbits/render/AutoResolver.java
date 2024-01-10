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
package com.googlecode.wicketbits.render;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicketbits.render.tag.AbstractAutoTag;
import com.googlecode.wicketbits.render.tag.AutoDefault;
import com.googlecode.wicketbits.render.tag.AutoForm;
import com.googlecode.wicketbits.render.tag.AutoInput;
import com.googlecode.wicketbits.render.tag.AutoLink;
import com.googlecode.wicketbits.render.tag.AutoSelect;
import com.googlecode.wicketbits.render.tag.AutoTextArea;

/**
 * Handles the auto creation of a MarkupContainer's children based on its
 * MarkupStream.
 * 
 * @author Edward
 * 
 */
public class AutoResolver {
	private MarkupStream markup;
	private MarkupContainer parent;
	private static final Logger log = LoggerFactory
			.getLogger(AutoCreateRenderListener.class);
	private static Map autoTagMap = new HashMap();

	static {
		autoTagMap.put("form", new AutoForm());
		autoTagMap.put("input", new AutoInput());
		autoTagMap.put("select", new AutoSelect());
		autoTagMap.put("textarea", new AutoTextArea());
		autoTagMap.put("a", new AutoLink());
		autoTagMap.put("span", new AutoDefault());
		autoTagMap.put("div", new AutoDefault());
		autoTagMap.put("tr", new AutoDefault());
		autoTagMap.put("td", new AutoDefault());
	}

	public AutoResolver(MarkupContainer parent, MarkupStream markup) {
		this.parent = parent;
		this.markup = markup;
	}

	public void resolve() {
		if (markup != null)
			resolveContainerChildren(parent);
	}

	public void resolveRepeater(int index) {
		if (index == -1) {
			// We don't currently handle auto-creation of components inside
			// repeaters inside fragments. Their markup index isn't easily
			// found.

			log.debug("Could not find markup index for reapeater: "
					+ parent.getPath());
			return;
		}

		Iterator iterator = parent.iterator();
		while (iterator.hasNext()) {
			Component component = (Component) iterator.next();
			markup.setCurrentIndex(index);
			resolveContainerChildren((MarkupContainer) component);
		}
	}

	private void resolveContainerChildren(MarkupContainer parent) {
		while (markup.hasMore()) {
			markup.next();

			// Keep moving until we get to the next tag
			while (markup.hasMore() && !markup.atTag())
				markup.next();

			// Found the close tag of this container so break out
			if (markup.atCloseTag())
				break;

			// Make sure we are not at the end of the stream
			if (markup.atTag())
				resolveTag(parent, markup.getTag());
		}
	}

	private void resolveTag(MarkupContainer container, ComponentTag tag) {
		if ("panel".equals(tag.getName())) {
			// Panels have a wicket:panel tag that needs to be ignored
			resolveContainerChildren(container);
			markup.skipToMatchingCloseTag(tag);

		} else if ("extend".equals(tag.getName())) {
			// Extends have a wicket:extend tag that needs to be ignored
			resolveContainerChildren(container);
			markup.skipToMatchingCloseTag(tag);

		} else {
			Component component = checkTag(container, tag);

			// Open tags are either containers and need to resolve their
			// children or need to skip to find the close tag
			if (tag.isOpen()) {
				if (AbstractRepeater.class.isInstance(component)) {
					// Skip repeaters as their children will be taken care
					// of when the repeaters onBeforeRender is called
					markup.skipToMatchingCloseTag(tag);

				} else if (Panel.class.isInstance(component)) {

					// Panels are basically sub pages so create a new
					// resolver and use it like the panel is a page
					MarkupStream panelMarkup = ((Panel) component)
							.getAssociatedMarkupStream(false);
					AutoResolver panelResolver = new AutoResolver(
							(Panel) component, panelMarkup);
					panelResolver.resolve();
					markup.skipToMatchingCloseTag(tag);

				} else if (MarkupContainer.class.isInstance(component)) {

					resolveContainerChildren((MarkupContainer) component);

				} else {
					try {
						markup.skipToMatchingCloseTag(tag);
					} catch (org.apache.wicket.markup.MarkupException e) {
						// Don't bomb out due to malformed html
						log.warn("Expected close tag for " + tag.toString()
								+ "\n" + markup.getResource().toString());
					}
				}
			}
		}
	}

	/**
	 * Check tag to see if component exists someplace or is one we can auto
	 * create.
	 * 
	 * @param container
	 * @param tag
	 * @return
	 */
	private Component checkTag(MarkupContainer container, ComponentTag tag) {

		// Skip Wicket auto tags
		if( tag.isAutoComponentTag())
			return null;
		
		// See if the component already exists in the container or page
		Component component = findExistingComponent(container, tag);
		if (component != null)
			return component;

		// Look for an auto-tag creator
		AbstractAutoTag autoTag = (AbstractAutoTag) autoTagMap.get(tag
				.getName().toLowerCase().trim());
		if (autoTag != null)
			component = autoTag.create(container, tag);

		if (component != null) {
			component.setOutputMarkupId(true);
			container.add(component);
		}
		return component;
	}

	private Component findExistingComponent(MarkupContainer container,
			ComponentTag tag) {

		Component component = null;
		component = container.get(tag.getId());

		if (component == null && !WebPage.class.isInstance(container)
				&& !Panel.class.isInstance(container)) {

			// Search for the component up the hierarchy on panels or pages
			MarkupContainer parent = container.getParent();
			while (parent != null && component == null) {
				if (Panel.class.isInstance(parent)
						|| WebPage.class.isInstance(parent)) {
					component = parent.get(tag.getId());
					// Don't go past a panel or parent to look for existing
					// children.
					break;
				}
				parent = parent.getParent();
			}

			// Re-parent component from page
			if (component != null) {
				container.add(component);

				// Check if we can set it as the default button
				if (Form.class.isInstance(container)
						&& IFormSubmittingComponent.class.isInstance(component)) {
					((Form) container)
							.setDefaultButton((IFormSubmittingComponent) component);
				}
			}
		}

		return component;
	}
}
