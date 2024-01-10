package org.jsemantic.services.amqservice;

import org.jsemantic.services.amqservice.common.ConsumerTool;
import org.jsemantic.services.amqservice.common.ProducerTool;
import org.jsemantic.services.amqservice.common.RequesterTool;
import org.jsemantic.services.amqservice.impl.ActiveMQServiceImpl;

import junit.framework.TestCase;

public class ActiveMQServiceTest extends TestCase {

	private ActiveMQService service = null;

	private ProducerTool producer = null;

	private ConsumerTool consumer = null;
	
	private RequesterTool requester = null;
	
	private final static String brokerUrl = "tcp://localhost:61616";

	protected void setUp() throws Exception {
		super.setUp();
		service = new ActiveMQServiceImpl();
		((ActiveMQServiceImpl) service).afterPropertiesSet();
		producer = new ProducerTool();
		producer.setUrl(brokerUrl);

		consumer = new ConsumerTool();
		consumer.setUrl(brokerUrl);
		
		requester = new RequesterTool();
		requester.setUrl(brokerUrl);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		service.stop();
	}

	public void testServiceCreationTest() {
		assertNotNull(service);
		assertTrue(service.isInitialized());

		service.setJmxUsed(false);
		service.setConnector(brokerUrl);
		service.start();
		
		assertTrue(service.isStarted());
		assertNotNull(service.getBroker());
	}

	public void testProducer() {
		service.setJmxUsed(false);
		service.setConnector(brokerUrl);
		service.start();
		assertTrue(service.isStarted());
		producer.run();
	}

	public void testConsumer() {
		service.setJmxUsed(false);
		service.setConnector(brokerUrl);
		service.start();
		assertTrue(service.isStarted());
		producer.run();
		consumer.run();
	}
	
}
