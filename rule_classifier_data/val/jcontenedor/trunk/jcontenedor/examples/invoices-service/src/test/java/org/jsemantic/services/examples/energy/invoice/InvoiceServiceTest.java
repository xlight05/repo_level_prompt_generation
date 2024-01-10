package org.jsemantic.services.examples.energy.invoice;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;
import org.jsemantic.services.examples.energy.MockDataGenerator;
import org.jsemantic.services.examples.energy.model.Customer;
import org.jsemantic.services.examples.energy.model.Invoice;
import org.jsemantic.services.examples.energy.model.MeterReading;

import junit.framework.TestCase;

public class InvoiceServiceTest extends TestCase {

	private JContenedor contenedor = null;

	protected void setUp() throws Exception {
		super.setUp();
		contenedor = JContenedorFactory
				.getInstance("META-INF/test/contenedor-configuration.xml");
		contenedor.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		contenedor.stop();
		contenedor.dispose();
		contenedor = null;
	}

	public void test() {
		assertNotNull(contenedor.getService("invoiceService"));
		assertNotNull(contenedor.getBean("generator"));
		assertNotNull(contenedor.getBean("printerService"));

		InvoiceService invoiceService = (InvoiceService) contenedor
				.getService("invoiceService");
		MockDataGenerator generator = (MockDataGenerator) contenedor
				.getBean("generator");

		Customer customer = generator.getCustomer();
		MeterReading reading = generator.getMeterReading();
		Invoice invoice = invoiceService.generateInvoice(customer, reading);

		assertNotNull(invoice);

	}

}
