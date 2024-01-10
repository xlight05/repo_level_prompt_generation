package com.googlecode.wicketbits.tests;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

import com.googlecode.wicketbits.examples.TrExample;
import com.googlecode.wicketbits.render.AutoCreateRenderListener;

public class TrTest extends TestCase {

	private WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
		tester.getApplication().addComponentOnBeforeRenderListener(
				new AutoCreateRenderListener());
	}

	public void testRender() {
		// start and render the test page
		tester.startPage(TrExample.class);

		// assert rendered page class
		tester.assertRenderedPage(TrExample.class);
	}

}
