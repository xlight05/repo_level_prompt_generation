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

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicketbits.reflection.MemberInfo;

public class AutoDefault extends AbstractAutoTag {

	public Component create(MarkupContainer container, ComponentTag tag,
			CompoundPropertyModel model, Object modelObject,
			MemberInfo memberInfo) {
		Component component = null;

		if (memberInfo != null) {
			if (memberInfo.isOfType(java.util.List.class)) {
				// Model is a list so create a repeater
				component = new AutoListView(tag.getId());
			} else {
				// Model is not a list so create a Label
				component = new Label(tag.getId());
			}
		}

		return component;
	}

	/**
	 * Custom auto-generated list view.
	 * 
	 * @author Edward
	 * 
	 */
	private static class AutoListView extends PropertyListView {

		public AutoListView(String id) {
			super(id);
		}

		private static final long serialVersionUID = 1L;

		protected void populateItem(ListItem item) {
			// Do nothing... children will be handled when the repeater is
			// resolved
		}
	}
}
