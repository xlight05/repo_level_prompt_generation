package org.jsemantic.services.amqservice;

import java.util.Set;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.jmx.BrokerView;
import org.jsemantic.jservice.core.service.Service;

public interface ActiveMQService extends Service {
	
	public final static String ACTIVEMQ_SERVICE_NAME = "activeMQService"; 
	
	/**
	 * 
	 * @return
	 */
	public Broker getBroker();
	/**
	 * 
	 * @return
	 */
	public BrokerView getBrokerView();
	
	/**
	 * 
	 * @param isJMXUsed
	 */
	public void setJmxUsed(boolean isJMXUsed);
	/**
	 * 
	 * @param connector
	 */
	public void setConnector(String connector);
	/**
	 * 
	 * @param connectors
	 */
	public void setConnectors(Set<String> connectors);
	/**
	 * 
	 * @return
	 */
	public boolean isJMXUsed();
}
