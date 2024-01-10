package com.googlecode.wicketbits.tests;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;

import com.googlecode.wicketbits.examples.AnnotationFormExample;
import com.googlecode.wicketbits.render.AutoCreateRenderListener;
import com.googlecode.wicketbits.render.annot.AnnotationHandler;

public class AnnotationFormTest extends TestCase {

	private WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
		tester.getApplication().addComponentOnBeforeRenderListener(
				new AutoCreateRenderListener(new AnnotationHandler()));
	}

	public void testRender() {
		// start and render the test page
		AnnotationFormExample page = new AnnotationFormExample();
		tester.startPage(page);

		// assert rendered page class
		tester.assertRenderedPage(AnnotationFormExample.class);
	}

	public void testRequired() {
		// start and render the test page
		AnnotationFormExample page = new AnnotationFormExample();
		tester.startPage(page);

		// assert null check
		FormTester formTester = tester.newFormTester("myForm");
		formTester.setValue("myValue", "");
		formTester.submit("mySubmit");
		tester
				.assertErrorMessages(new String[] { "Field 'myValue' is required." });
	}

	public void testMinimum() {
		// start and render the test page
		AnnotationFormExample page = new AnnotationFormExample();
		tester.startPage(page);

		// assert minimum check
		FormTester formTester = tester.newFormTester("myForm");
		formTester.setValue("myValue", "0");
		formTester.submit("mySubmit");
		tester
				.assertErrorMessages(new String[] { "'0' is smaller than the minimum of 2." });
	}

	public void testMaximum() {
		// start and render the test page
		AnnotationFormExample page = new AnnotationFormExample();
		tester.startPage(page);

		// assert minimum check
		FormTester formTester = tester.newFormTester("myForm");
		formTester.setValue("myValue", "9");
		formTester.submit("mySubmit");
		tester
				.assertErrorMessages(new String[] { "'9' is larger than the maximum of 7." });
	}

	public void testGood() {
		// start and render the test page
		AnnotationFormExample page = new AnnotationFormExample();
		tester.startPage(page);

		// assert minimum check
		FormTester formTester = tester.newFormTester("myForm");
		formTester.setValue("myValue", "5");
		formTester.submit("mySubmit");
		tester.assertNoErrorMessage();
	}

}
