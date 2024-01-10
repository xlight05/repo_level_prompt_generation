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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicketbits.reflection.MemberInfo;

public class AutoSelect extends AbstractAutoTag {

	public Component create(MarkupContainer container, ComponentTag tag,
			CompoundPropertyModel model, Object modelObject,
			MemberInfo memberInfo) {
		FormComponent component = null;

		if (memberInfo != null) {
			int size = tag.getAttributes().getInt("size", 1);
			boolean multiple = tag.getAttributes().getBoolean("multiple");
			if (model != null) {
				if (size == 1) {
					// Drop down choices
					component = new DropDownChoice(tag.getId(), model
							.bind(getValuePropertyName(tag.getId())), model
							.bind(getListPropertyName(tag.getId())));
				} else if (multiple) {
					component = new ListMultipleChoice(tag.getId(), model
							.bind(getValuePropertyName(tag.getId())), model
							.bind(getListPropertyName(tag.getId())));
				} else {
					// List Box
					component = new ListChoice(tag.getId(), model
							.bind(getValuePropertyName(tag.getId())), model
							.bind(getListPropertyName(tag.getId())))
							.setNullValid(false);

				}
			}
		}

		return component;
	}

}
