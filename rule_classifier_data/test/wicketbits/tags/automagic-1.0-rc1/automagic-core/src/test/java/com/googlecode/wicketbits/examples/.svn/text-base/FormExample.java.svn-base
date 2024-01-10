package com.googlecode.wicketbits.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Example of auto created form.
 * 
 * @author Edward
 * 
 */
public class FormExample extends WebPage {

	private static final long serialVersionUID = 1L;

	protected String myText = "something";

	protected Boolean myCheck = Boolean.TRUE;

	protected String myRadio = null;

	protected String myDropDown = null;

	protected String myList = null;

	protected List myMultiList = new ArrayList();

	/**
	 * List of values used in radio and select input types
	 */
	protected List names = new ArrayList();

	public String myResult = null;

	public FormExample() {

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
				System.out.println("myText = " + myText);
				myResult = myText;
			}
		};

		add(submit);

		// No need to add any other components... they are auto created
	}
}
