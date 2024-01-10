package org.jsemantic.jcontenedor.standalone;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;

import junit.framework.TestCase;

public class StandaloneContenedorTest extends TestCase {
	
	private JContenedor contenedor = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		contenedor = JContenedorFactory
		.getInstance("META-INF/test/standalone/contenedor-configuration.xml");
		contenedor.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		contenedor.stop();
		contenedor.dispose();
		contenedor = null;
	}
	
	public void test() {
		super.assertNotNull(contenedor.getBean("businessService"));
	}
	
}
