package org.jsemantic.services.common;

import org.jsemantic.jservice.core.exception.CycleException;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractService;

public class UnManagedService extends AbstractService {
	
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

	@Override
	protected void postConstruct() throws CycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void release() throws CycleException {
		// TODO Auto-generated method stub
		
	}
}
