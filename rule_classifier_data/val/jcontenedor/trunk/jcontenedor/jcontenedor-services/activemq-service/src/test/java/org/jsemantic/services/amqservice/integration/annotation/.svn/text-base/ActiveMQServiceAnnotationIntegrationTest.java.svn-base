package org.jsemantic.services.amqservice.integration.annotation;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;
import org.jsemantic.services.amqservice.ActiveMQService;
import org.jsemantic.services.amqservice.annotation.ActiveMQServiceAnnotationProcessor;
import org.jsemantic.services.amqservice.annotation.ActiveMQServiceConfiguration;
import org.jsemantic.services.amqservice.common.ProducerTool;

import junit.framework.TestCase;

@org.jsemantic.services.amqservice.annotation.ActiveMQService
@ActiveMQServiceConfiguration(connector = "tcp://localhost:61666", jmx = "true")
public class ActiveMQServiceAnnotationIntegrationTest extends TestCase {

	private final static String configurationFile = "classpath:META-INF/activemq-service/test/jcontenedor.xml";

	private JContenedor contenedor = null;

	private ActiveMQService service = null;

	private ActiveMQServiceAnnotationProcessor processor = new ActiveMQServiceAnnotationProcessor();

	private ProducerTool producer = null;

	public ActiveMQServiceAnnotationIntegrationTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		producer = new ProducerTool();
		producer.setUrl("tcp://localhost:61666");
		contenedor = JContenedorFactory.getInstance(configurationFile);
		contenedor.start();
		service = (ActiveMQService) contenedor
				.getService(ActiveMQService.ACTIVEMQ_SERVICE_NAME);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		service.stop();

		contenedor.stop();
		contenedor.dispose();
		contenedor = null;
	}

	public void testProcessor() {
		processor.processAnnotation(service, this.getClass());
		service.start();

		assertNotNull(service);
		assertTrue(service.isStarted());
		assertNotNull(service.getBroker());
		assertTrue(service.isJMXUsed());
		
		producer.run();
	}

}
