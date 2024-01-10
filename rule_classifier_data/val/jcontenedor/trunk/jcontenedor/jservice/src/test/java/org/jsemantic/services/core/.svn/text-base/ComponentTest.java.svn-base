package org.jsemantic.services.core;

import org.jsemantic.services.common.SupportComponent;

import junit.framework.TestCase;

public class ComponentTest extends TestCase {
	
	private SupportComponent component = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		component = new SupportComponent();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test() {
		super.assertTrue(component.isInitialized());
		component.dispose();
	}
	
	public void testDispose() {
		component.dispose();
		super.assertFalse(component.isInitialized());
	}
	
	
}
