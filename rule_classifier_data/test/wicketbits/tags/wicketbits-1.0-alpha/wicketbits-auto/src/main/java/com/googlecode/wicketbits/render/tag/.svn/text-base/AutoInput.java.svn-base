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
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.googlecode.wicketbits.reflection.MemberInfo;

public class AutoInput extends AbstractAutoTag {

	public Component create(MarkupContainer container, ComponentTag tag,
			CompoundPropertyModel model, Object modelObject,
			MemberInfo memberInfo) {
		FormComponent component = null;

		if (memberInfo != null) {
			String tagType = tag.getAttributes().getString("type");
			if (tagType != null) {
				tagType = tagType.trim().toLowerCase();
			}

			if ("text".equals(tagType)) {
				component = new TextField(tag.getId());
			} else if ("password".equals(tagType)) {
				component = new PasswordTextField(tag.getId());
			} else if ("checkbox".equals(tagType)) {
				component = new CheckBox(tag.getId());
			} else if ("radio".equals(tagType)) {
				if (model != null) {
					component = new AutoRadioChoice(tag.getId(), model
							.bind(getValuePropertyName(tag.getId())), model
							.bind(getListPropertyName(tag.getId())));
				}
			}
		}

		return component;
	}

	/**
	 * Override that changes the input tag of the radio markup to a span tag for
	 * the group. The input radio tags will be auto added by the Radio Choice
	 * class.
	 * 
	 * @author Edward
	 * 
	 */
	private static class AutoRadioChoice extends RadioChoice {
		private static final long serialVersionUID = 1L;

		public AutoRadioChoice(String id, IModel model, IModel choices) {
			super(id, model, choices);
		}

		protected void onComponentTag(ComponentTag tag) {
			super.onComponentTag(tag);
			tag.setName("span");
		}
	}
}
