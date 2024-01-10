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

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.AbstractRepeater;

import com.googlecode.wicketbits.render.annot.AbstractAnnotationHandler;

/**
 * Allows quick Wicket page development through the auto creation of components
 * by convention and helps separate page design from Java code functionality.
 * Add the listener to the applications list of onBeforeRender listeners:
 * 
 * <p>
 * 
 * <pre>
 * public void init() {
 * 	this.addComponentOnBeforeRenderListener(new AutoCreateRenderListener());
 * }
 * </pre>
 * 
 * </p>
 * 
 * The AutoCreateRenderListner will try to auto-create any components that are
 * not specifically instantiated in code by matching them to a property on a
 * parent CompoundPropertyModel.
 * 
 * <p>
 * HTML:
 * 
 * <pre>
 * &lt;html&gt;
 *   ...
 *   &lt;body&gt;
 *     &lt;span wicket:id=&quot;mylabel&quot;&gt;&lt;/span&gt;
 *   &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 * 
 * Java:
 * 
 * <pre>
 * public class MyPage extends WebPage {
 * 	public String mylabel = &quot;Hello World&quot;;
 * 
 * 	public MyPage() {
 * 		setModel(new CompoundPropertyModel(this));
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * The AutoCreateRenderListner looks at the tag type to determine what type of
 * Wicket component to create.
 * 
 * <ul>
 * <li>span - Label or ListView depending on if the bound property is a
 * java.util.List</li>
 * <li>tr - ListView if bound property is a java.util.List</li>
 * <li>form - Form</li>
 * <li>input - various Wicket input types depending on the type html attribute.</li>
 * <li>select - DropDownChoice or ListChoice if size attribute is greater than
 * 1</li>
 * <li>a - AbstractLink subclass where the href attribute specifies the class
 * to use</li>
 * </ul>
 * 
 * Certain elements (input type="radio", and select) require two models. One for
 * the object that will hold the selected value and one that holds the list of
 * possible choices. This is handled by setting the
 * wicket:id="valuemodel@choicelistmodel".
 * 
 * <p>
 * HTML:
 * 
 * <pre>
 * &lt;html&gt;
 *   ...
 *   &lt;body&gt;
 *     &lt;form wicket:id=&quot;myform&quot;&gt;
 *       &lt;input wicket:id=&quot;selectedname@names&quot; type=&quot;radio&quot;&gt;&lt;/input&gt;
 *       &lt;input wicket:id=&quot;phone&quot; type=&quot;text&quot;&gt;&lt;/input&gt;
 *       &lt;a wicket:id=&quot;submit&quot; href=&quot;mypagkage.MyPage$MySubmit&quot;&gt;Submit Form&lt;/a&gt;
 *     &lt;/form&gt;
 *   &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 * 
 * Java:
 * 
 * <pre>
 * public class MyPage extends WebPage {
 * 	protected String selectedname;
 * 	protected List&lt;String&gt; names;
 * 	protected String phone;
 * 
 * 	public MyPage() {
 * 		setModel(new CompoundPropertyModel(this));
 * 		names = new ArrayList&lt;String&gt;();
 * 		names.add(&quot;Edward&quot;);
 * 		names.add(&quot;Michael&quot;);
 * 
 * 		add(new TextField(&quot;phone&quot;).setRequired(true));
 * 	}
 * 
 * 	public static class MySubmit extends AjaxSubmitLink {
 * 
 * 		public MySubmit(String id) {
 * 			super(id);
 * 		}
 * 
 * 		private static final long serialVersionUID = 1L;
 * 
 * 		protected void onSubmit(AjaxRequestTarget target, Form form) {
 * 			MyPage page = (MyPage) this.getPage();
 * 			// Do Something
 * 		}
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * Components that you wish to work differently from the default auto-created
 * versions or components that can't be auto created can be instantiated by hand
 * and added to the parent page (or panel.) Before any components are auto
 * created the parent page (or panel) is searched for a matching component and
 * if found that component is used and re-parented to the correct place in the
 * hierarchy. The custom TextField in the example above will be placed into the
 * auto-created form.
 * 
 * @author Edward Mostrom
 * 
 */
public class AutoCreateRenderListener implements
		IComponentOnBeforeRenderListener {

	private static final long serialVersionUID = 1L;

	public AutoCreateRenderListener() {
	}

	public AutoCreateRenderListener(AbstractAnnotationHandler handler) {
		AbstractAnnotationHandler.setCurrentHandler(handler);
	}

	public void onBeforeRender(Component component) {
		if (WebPage.class.isInstance(component)
				|| Panel.class.isInstance(component)) {
			MarkupStream markup = ((MarkupContainer) component)
					.getAssociatedMarkupStream(true);

			AutoResolver resolver = new AutoResolver(
					(MarkupContainer) component, markup);
			resolver.resolve();
		} else if (AbstractRepeater.class.isInstance(component)) {
			MarkupContainer parent = component.findParentWithAssociatedMarkup();
			MarkupStream markup = parent.getAssociatedMarkupStream(true);
			String path = component.getPath().replaceFirst(
					parent.getPath() + ":", "");
			AutoResolver resolver = new AutoResolver(
					(MarkupContainer) component, markup);
			resolver.resolveRepeater(markup.findComponentIndex("", path));

		}
	}
}
