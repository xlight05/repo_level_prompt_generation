package org.jsemantic.services.r4spring;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;
import org.jsemantic.services.r4spring.common.EvenOddService;

import junit.framework.TestCase;

public class EvenOddServiceTest extends TestCase {

	private JContenedor contenedor = null;

	private EvenOddService evenOddService = null;

	protected void setUp() throws Exception {
		super.setUp();
		contenedor = JContenedorFactory
				.getInstance("META-INF/evenodd/evenodd-configuration.xml");
		contenedor.start();
		evenOddService = (EvenOddService) contenedor
				.getBean("evenOddService");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		contenedor.stop();
		contenedor.dispose();
	}

	public void test() {
		super.assertTrue(evenOddService.checkOddNumber(10));
	}

}
