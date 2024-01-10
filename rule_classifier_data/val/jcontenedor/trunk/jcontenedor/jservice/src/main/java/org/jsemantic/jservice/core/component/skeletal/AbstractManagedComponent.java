package org.jsemantic.jservice.core.component.skeletal;

import org.jsemantic.jservice.core.exception.CycleException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractManagedComponent extends AbstractComponent
		implements InitializingBean, DisposableBean {

	public AbstractManagedComponent() {

	}

	@Override
	public void init() throws CycleException {
		if (!isInitializing()) {
			throw new CycleException("Wrong State.");
		}
		super.setState(STATE.INITIALIZED);
		postConstruct();
	}

	@Override
	public void dispose() throws CycleException {
		if (!isDisposing()) {
			throw new CycleException("Wrong State.");
		}
		super.setState(STATE.DISPOSED);
		release();
	}

	public void afterPropertiesSet() throws Exception {
		initializing();
	}

	public void destroy() throws Exception {
		disposing();
	}

}
