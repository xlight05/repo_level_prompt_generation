package org.jsemantic.jservice.core.service.skeletal;

import org.jsemantic.jservice.core.entity.skeletal.AbstractEntity;
import org.jsemantic.jservice.core.exception.CycleException;
import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.service.exception.ServiceException;

public abstract class AbstractService extends AbstractEntity implements Service {

	public AbstractService() {
	}

	@Override
	protected void postConstruct() throws CycleException {
	}

	@Override
	protected void release() throws CycleException {

	}

	public boolean isAutoStarted() {
		throw new ServiceException(
				"Unavailable Operation. This is not a managed service");
	}

	public void setAutoStarted(boolean autoStart) {
		throw new ServiceException(
				"Unavailable Operation. This is not a managed service");
	}

	public void start() throws CycleException {
		if (isStarted()) {
			throw new ServiceException("Service already started.");
		}
		startService();
		super.setState(STATE.STARTED);
	}

	public void stop() throws CycleException {
		stopService();
		super.setState(STATE.STOPPED);
	}

	public void restart() throws CycleException {
		stop();
		start();
	}

	/**
	 * 
	 * @throws ServiceException
	 */
	protected abstract void startService() throws ServiceException;

	/**
	 * 
	 * @throws ServiceException
	 */
	protected abstract void stopService() throws ServiceException;

}
