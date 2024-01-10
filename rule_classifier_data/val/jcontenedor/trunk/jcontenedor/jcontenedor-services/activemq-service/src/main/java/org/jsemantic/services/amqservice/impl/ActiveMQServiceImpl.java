package org.jsemantic.services.amqservice.impl;

import java.util.Set;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerView;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractManagedService;
import org.jsemantic.services.amqservice.ActiveMQService;
import org.jsemantic.services.amqservice.exception.ActiveMQServiceException;
import org.springframework.util.StringUtils;

public class ActiveMQServiceImpl extends AbstractManagedService implements
		ActiveMQService {

	private BrokerService broker = null;

	private boolean isJMXUsed = false;

	private Set<String> connectors = null;

	private String connector = null;

	@Override
	protected void startService() throws ServiceException {
		broker = new BrokerService();
		broker.setUseJmx(isJMXUsed);
		if (StringUtils.hasText(connector)) {
			try {
				broker.addConnector(connector);
			} catch (Exception e) {
				throw new ActiveMQServiceException(e);
			}
		} else if (connectors != null) {
			broker.setTransportConnectorURIs((String[]) connectors.toArray());
		} else {
			throw new ActiveMQServiceException(
					"At least one connector must be specified.");
		}

		try {
			broker.start();
		} catch (Exception e) {
			throw new ActiveMQServiceException(e);
		}

	}

	@Override
	protected void stopService() throws ServiceException {
		try {
			broker.stop();
		} catch (Exception e) {
			throw new ActiveMQServiceException(e);
		}
	}

	public Broker getBroker() {
		try {
			return broker.getBroker();
		} catch (Exception e) {
			throw new ActiveMQServiceException(e);
		}
	}

	public BrokerView getBrokerView() {
		try {
			return broker.getAdminView();
		} catch (Exception e) {
			throw new ActiveMQServiceException(e);
		}
	}
	
	public boolean isStarted() {
		if (super.isStarted()) {
			if (broker.isStarted()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isJMXUsed() {
		return isJMXUsed;
	}

	public void setJmxUsed(boolean isJMXUsed) {
		this.isJMXUsed = isJMXUsed;
	}

	public void setConnectors(Set<String> connectors) {
		this.connectors = connectors;
	}
	
	public void setConnector(String connector) {
		this.connector = connector;
	}


}
