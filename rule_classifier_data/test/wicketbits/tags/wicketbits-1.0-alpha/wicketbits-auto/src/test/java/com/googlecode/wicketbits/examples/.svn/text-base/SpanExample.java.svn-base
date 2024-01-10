package com.googlecode.wicketbits.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Example of span tags with Labels and one as a ListView.
 * 
 * The span tag using wicket:id="myText" creates a Label using the myTest field.
 * 
 * The span tag using wicket:id="names" creates a ListView looping over each
 * value in the list.
 * 
 * @author Edward
 * 
 */
public class SpanExample extends WebPage {

	private static final long serialVersionUID = 1L;

	protected String myText = "something";

	protected List names = new ArrayList();

	public SpanExample() {

		// Initialize the list
		names.add("Edward");
		names.add("Tami");
		names.add("Kenzi");
		names.add("Lexi");
		names.add("Kolby");

		// Use a CompoundPropertyModel to get the fields of this class
		setModel(new CompoundPropertyModel(this));

		// No need to add the ListView or Labels... they are auto created
	}
}
