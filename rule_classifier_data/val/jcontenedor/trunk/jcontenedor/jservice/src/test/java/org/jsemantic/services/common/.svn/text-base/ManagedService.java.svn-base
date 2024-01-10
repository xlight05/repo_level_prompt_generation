package org.jsemantic.services.common;

import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractManagedService;

public class ManagedService extends AbstractManagedService {
	/**
	 * 
	 */
	private SupportComponent component = null;
	
	@Override
	protected void startService() throws ServiceException {
		component = new SupportComponent();
	}

	@Override
	protected void stopService() throws ServiceException {
		component.dispose();
		component = null;
	}
	
	public void service() {
		component.support();
	}

}
