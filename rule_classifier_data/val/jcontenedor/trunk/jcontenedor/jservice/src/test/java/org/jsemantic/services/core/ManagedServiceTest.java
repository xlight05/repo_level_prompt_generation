package org.jsemantic.services.core;

import org.jsemantic.services.common.ManagedService;

import junit.framework.TestCase;

public class ManagedServiceTest extends TestCase {

	private ManagedService service = null;

	protected void setUp() throws Exception {
		super.setUp();
		service = new ManagedService();
		service.setAutoStarted(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		service.destroy();
		service = null;
	}

	public void test() throws Exception {
		service.afterPropertiesSet();
		super.assertTrue(service.isStarted());
		service.service();
		service.stop();
		super.assertTrue(service.isStopped());
	}
	
	public void restartTest() throws Exception {
		super.assertTrue(service.isStarted());
		service.service();
		service.restart();
		super.assertTrue(service.isStarted());
	}
	
	public void testError() throws Exception {
		super.assertFalse(service.isInitialized());
	}

}
