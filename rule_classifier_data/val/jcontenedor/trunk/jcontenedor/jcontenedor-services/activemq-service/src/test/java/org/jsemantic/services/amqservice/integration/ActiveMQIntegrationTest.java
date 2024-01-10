package org.jsemantic.services.amqservice.integration;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;
import org.jsemantic.services.amqservice.ActiveMQService;
import org.jsemantic.services.amqservice.common.ConsumerTool;
import org.jsemantic.services.amqservice.common.ProducerTool;

import junit.framework.TestCase;

public class ActiveMQIntegrationTest extends TestCase {

	private final static String configurationFile = "classpath:META-INF/activemq-service/test/jcontenedor.xml";
	
	private JContenedor contenedor = null;
	
	private ActiveMQService service = null;

	private ProducerTool producer = null;

	private ConsumerTool consumer = null;

	private final static String brokerUrl = "tcp://localhost:61616";

	public ActiveMQIntegrationTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		contenedor = JContenedorFactory.getInstance(configurationFile);
		contenedor.start();

		service = (ActiveMQService) contenedor
				.getService(ActiveMQService.ACTIVEMQ_SERVICE_NAME);
		service.start();

		consumer = new ConsumerTool();
		consumer.setUrl(brokerUrl);

		producer = new ProducerTool();
		producer.setUrl(brokerUrl);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		service.stop();

		contenedor.stop();
		contenedor.dispose();
		contenedor = null;
	}

	public void test() {
		assertNotNull(service);
		assertTrue(service.isStarted());
		assertNotNull(service.getBroker());
	}

	public void testProducer() {
		assertTrue(service.isStarted());
		producer.run();
	}

	public void testConsumer() {
		assertTrue(service.isStarted());
		producer.run();
		consumer.run();
	}

}
