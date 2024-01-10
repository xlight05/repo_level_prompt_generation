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
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicketbits.reflection.ClassInfo;
import com.googlecode.wicketbits.reflection.MemberInfo;
import com.googlecode.wicketbits.render.AutoCreateRenderListener;
import com.googlecode.wicketbits.render.annot.AbstractAnnotationHandler;

public abstract class AbstractAutoTag {

	protected static final Logger log = LoggerFactory
			.getLogger(AutoCreateRenderListener.class);

	public abstract Component create(MarkupContainer container,
			ComponentTag tag, CompoundPropertyModel model, Object modelObject,
			MemberInfo memberInfo);

	public final Component create(MarkupContainer container, ComponentTag tag) {
		CompoundPropertyModel model = getModel(container);
		Object modelObject = getModelObject(model);
		ClassInfo ci = ClassInfo.parse(modelObject.getClass());
		MemberInfo mi = ci
				.getBeanMember(this.getValuePropertyName(tag.getId()));

		Component c = create(container, tag, model, modelObject, mi);

		if (FormComponent.class.isInstance(c)) {
			// Check for annotations
			this.checkAnnotations((FormComponent) c, tag, mi);

			// Set a nice label for errors if the html title attribute is set
			this.setLabel((FormComponent) c, tag);
		}

		return c;
	}

	/**
	 * Search up the hierarchy for the closest CompoundPropertyModel.
	 * 
	 * @param container
	 * @return
	 */
	private CompoundPropertyModel getModel(MarkupContainer container) {
		MarkupContainer parent = container;
		while (!CompoundPropertyModel.class.isInstance(parent.getModel())) {
			parent = parent.getParent();
			if (parent == null)
				return null;
		}

		return (CompoundPropertyModel) parent.getModel();
	}

	private Object getModelObject(CompoundPropertyModel containerModel) {
		IModel model = containerModel;

		// Search for the final object that has the properties on it
		while (IChainingModel.class.isInstance(model)) {
			IModel chained = ((IChainingModel) model).getChainedModel();
			if (chained != null)
				model = chained;
			else
				break;
		}

		return model.getObject();
	}

	protected String getValuePropertyName(String tagId) {
		String valuePropertyName = tagId;
		int index = valuePropertyName.lastIndexOf("@");
		if (index > 0)
			valuePropertyName = valuePropertyName.substring(0, index);
		return valuePropertyName;
	}

	protected String getListPropertyName(String tagId) {
		String listPropertyName = tagId;
		int index = listPropertyName.lastIndexOf("@");
		if (index > 0)
			listPropertyName = listPropertyName.substring(index + 1);
		return listPropertyName;
	}

	private void checkAnnotations(FormComponent component, ComponentTag tag,
			MemberInfo mi) {

		if (mi.numAnnotations() > 0) {

			AbstractAnnotationHandler handler = AbstractAnnotationHandler
					.getCurrentHandler();
			if (handler != null) {
				handler.checkAnnotations(component, tag, mi);
			}
		}
	}

	private void setLabel(FormComponent component, ComponentTag tag) {
		if (tag.getAttributes().containsKey("name"))
			component
					.setLabel(new Model(tag.getAttributes().getString("name")));
		else if (tag.getAttributes().containsKey("title"))
			component
					.setLabel(new Model(tag.getAttributes().getString("title")));
	}
}
