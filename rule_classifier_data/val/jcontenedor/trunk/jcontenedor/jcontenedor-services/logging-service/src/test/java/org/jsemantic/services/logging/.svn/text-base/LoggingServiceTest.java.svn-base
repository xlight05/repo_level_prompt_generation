package org.jsemantic.services.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.commons.logging.impl.SimpleLog;
import org.jsemantic.services.logging.LoggingService;
import org.jsemantic.services.logging.exception.LoggingServiceException;
import org.jsemantic.services.logging.impl.LoggingServiceImpl;

import junit.framework.TestCase;

public class LoggingServiceTest extends TestCase {
	
	private LoggingService service = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		service = new LoggingServiceImpl();
		((LoggingServiceImpl)service).afterPropertiesSet();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		service.stop();
		((LoggingServiceImpl)service).destroy();
		service = null;
	}
	
	public void testNoConfiguration() {
		service.start();
		Log log = service.getInstance();
		super.assertNotNull(log);
		super.assertTrue(log instanceof SimpleLog);
	}
	
	public void testLog4j() {
		((LoggingServiceImpl)(this.service)).setLogType("log4j");
		service.start();
		Log log = service.getInstance();
		super.assertNotNull(log);
		super.assertTrue(log instanceof Log4JLogger);
	}
	
	public void testSimpleLog() {
		((LoggingServiceImpl)(this.service)).setLogType("log4j");
		service.start();
		Log log = service.getInstance();
		super.assertNotNull(log);
		super.assertTrue(log instanceof Log4JLogger);
	}
	
	public void testInitError() {
		((LoggingServiceImpl)(this.service)).setLogType("log4");
		try {
			service.start();
		}
		catch (LoggingServiceException e) {
			super.assertTrue(true);
			return;
		}
		super.fail();
	}
	
}
