package com.googlecode.wicketbits.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicketbits.render.annot.Maximum;
import com.googlecode.wicketbits.render.annot.Minimum;
import com.googlecode.wicketbits.render.annot.Required;

/**
 * Example of auto created form with annotations.
 * 
 * @author Edward
 * 
 */
public class AnnotationFormExample extends WebPage {

	private static final long serialVersionUID = 1L;

	protected final FeedbackPanel feedback = new FeedbackPanel("feedback");

	@Required
	@Minimum(2)
	@Maximum(7)
	protected Integer myValue = null;

	protected Boolean myCheck = Boolean.TRUE;

	protected String myRadio = null;

	protected String myDropDown = null;

	protected String myList = null;

	protected List<String> myMultiList = new ArrayList<String>();

	/**
	 * List of values used in radio and select input types
	 */
	protected List<String> names = new ArrayList<String>();

	public Integer myResult = null;

	public AnnotationFormExample() {

		// Initialize the list
		names.add("Edward");
		names.add("Tami");
		names.add("Kenzi");
		names.add("Lexi");
		names.add("Kolby");

		// Use a CompoundPropertyModel to get the fields of this class
		setModel(new CompoundPropertyModel(this));

		// The submit button will be automatically re-parented to the auto
		// created form
		Button submit = new Button("mySubmit") {
			private static final long serialVersionUID = 1L;

			public void onSubmit() {
				System.out.println("myValue = " + myValue);
				myResult = myValue;
			}
		};

		add(feedback);
		add(submit);

		// No need to add any other components... they are auto created
	}
}
