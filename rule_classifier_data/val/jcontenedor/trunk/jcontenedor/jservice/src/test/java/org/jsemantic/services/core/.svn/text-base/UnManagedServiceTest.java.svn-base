package org.jsemantic.services.core;

import org.jsemantic.services.common.UnManagedService;

import junit.framework.TestCase;

public class UnManagedServiceTest extends TestCase {

	private UnManagedService service = null;

	protected void setUp() throws Exception {
		super.setUp();
		service = new UnManagedService();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		service.dispose();
		service = null;
	}

	public void test() throws Exception {
		service.start();
		super.assertTrue(service.isStarted());
		service.service();
		service.stop();
		super.assertTrue(service.isStopped());
	}
	
	public void restartTest() throws Exception {
		service.start();
		super.assertTrue(service.isStarted());
		service.service();
		service.restart();
		super.assertTrue(service.isStarted());
		service.stop();
	}
}
