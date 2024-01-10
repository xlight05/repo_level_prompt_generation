package com.googlecode.wicketbits.tests;

import org.apache.wicket.util.tester.WicketTester;

import com.googlecode.wicketbits.examples.SpanExample;
import com.googlecode.wicketbits.render.AutoCreateRenderListener;

import junit.framework.TestCase;

public class SpanTest extends TestCase {

	private WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
		tester.getApplication().addComponentOnBeforeRenderListener(
				new AutoCreateRenderListener());
	}

	public void testRender() {
		// start and render the test page
		tester.startPage(SpanExample.class);

		// assert rendered page class
		tester.assertRenderedPage(SpanExample.class);
	}

}
