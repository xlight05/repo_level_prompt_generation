package org.jsemantic.services.logging.impl;

import org.apache.commons.logging.Log;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractManagedService;
import org.jsemantic.services.logging.LoggingService;
import org.jsemantic.services.logging.exception.LoggingServiceException;
import org.jsemantic.services.logging.factory.LoggingFactory;

public class LoggingServiceImpl extends AbstractManagedService implements LoggingService {

	private LoggingFactory loggingFactory = null;

	public final static String DEFAULT_LOG_TYPE = "simple";

	public final static String LOG4J_LOG_TYPE = "log4j";

	private String logType = DEFAULT_LOG_TYPE;

	private String logProvider = LoggingFactory.LOG_PROVIDER_SIMPLE;

	public LoggingServiceImpl() {
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public Log getInstance() {
		if (this.isStarted()) {
			return this.loggingFactory.getLogInstance();
		}
		throw new ServiceException("Service not Initialized.");
	}

	@Override
	protected void stopService() throws ServiceException {
		if (this.loggingFactory != null) {
			try {
				this.loggingFactory.dispose();
			} catch (Exception e) {
				throw new LoggingServiceException(e);
			}
			this.loggingFactory = null;
		}
	}

	@Override
	protected void startService() throws ServiceException {
		checkLogType();
		this.loggingFactory = new LoggingFactory();
		this.loggingFactory.setLogType(logProvider);
	}

	private String checkLogType() {
		if (this.logType.equalsIgnoreCase(LOG4J_LOG_TYPE)) {
			logProvider = LoggingFactory.LOG_PROVIDER_LOG4J;
		} else if (this.logType.equalsIgnoreCase(DEFAULT_LOG_TYPE)) {
			logProvider = LoggingFactory.LOG_PROVIDER_SIMPLE;
		} else {
			throw new LoggingServiceException(
					"Configuration Log error: type not valid.");
		}
		return logProvider;
	}


}
