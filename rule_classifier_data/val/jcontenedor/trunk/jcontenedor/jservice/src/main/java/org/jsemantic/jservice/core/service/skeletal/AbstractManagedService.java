package org.jsemantic.jservice.core.service.skeletal;

import org.jsemantic.jservice.core.exception.CycleException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractManagedService extends AbstractService implements
		InitializingBean, DisposableBean, BeanNameAware {

	private boolean autoStarted = false;

	@Override
	public void init() throws CycleException {
		if (!isInitializing())
			throw new CycleException();
		super.init();
	}

	@Override
	public void dispose() throws CycleException {
		if (!isDisposing())
			throw new CycleException();
		super.dispose();
	}

	public void afterPropertiesSet() throws Exception {
		super.setState(STATE.INITIALIZING);
		init();
		if (isAutoStarted()) {
			start();
		}
	}

	public void destroy() throws Exception {
		super.setState(STATE.DISPOSING);
		if (isStarted()) {
			stop();
		}
		dispose();
	}

	public void setBeanName(java.lang.String beanName) {
		super.setId(beanName);
	}

	@Override
	public boolean isAutoStarted() {
		return autoStarted == true;
	}

	@Override
	public void setAutoStarted(boolean autoStart) {
		autoStarted = autoStart;
	}

}
