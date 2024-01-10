package com.googlecode.wicketbits.tests;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;

import com.googlecode.wicketbits.examples.FormExample;
import com.googlecode.wicketbits.render.AutoCreateRenderListener;

public class FormTest extends TestCase {

	private WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
		tester.getApplication().addComponentOnBeforeRenderListener(
				new AutoCreateRenderListener());
	}

	public void testRender() {
		// start and render the test page
		FormExample page = new FormExample();
		tester.startPage(page);

		// assert rendered page class
		tester.assertRenderedPage(FormExample.class);

		// assert form submit works
		FormTester formTester = tester.newFormTester("myForm");
		formTester.setValue("myText", "hello");
		formTester.submit("mySubmit");
		assertEquals("myResult from form submit", "hello", page.myResult);
	}

}
