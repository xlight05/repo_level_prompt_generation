package org.jsemantic.services.httpservice;


import org.jsemantic.services.httpservice.HttpService;
import org.jsemantic.services.httpservice.impl.HttpServiceImpl;

import junit.framework.TestCase;

public class HttpServiceTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test() throws Exception {
		HttpService server = null;
		try {
			server = new HttpServiceImpl();
			((HttpServiceImpl)server).afterPropertiesSet();
			assertNotNull(server);
			server.start();
		}
		catch (Throwable e) {
			fail(e.getMessage());
		}
		finally {
			server.stop();
			((HttpServiceImpl)server).destroy();
			server = null;
		}
	}
	
}
