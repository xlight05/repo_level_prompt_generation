package org.jsemantic.services.examples.energy.invoice.impl;

import org.apache.commons.logging.Log;
import org.jsemantic.core.session.SemanticSession;
import org.jsemantic.services.examples.energy.InvoiceFactory;
import org.jsemantic.services.examples.energy.invoice.InvoiceService;
import org.jsemantic.services.examples.energy.model.Customer;
import org.jsemantic.services.examples.energy.model.Invoice;
import org.jsemantic.services.examples.energy.model.MeterReading;
import org.jsemantic.services.examples.energy.model.Tariff;

import org.jsemantic.services.r4spring.annotations.SemanticService;
import org.jsemantic.services.r4spring.annotations.SessionVariables;
import org.jsemantic.services.r4spring.annotations.Variable;

import org.jsemantic.services.r4spring.impl.R4SpringServiceSkeletal;

@SemanticService(rulesFile = "META-INF/invoices-service/rules/tariff.drl", stateless = false)
@SessionVariables(variables = { @Variable(key = "kwh", value = "0.091939"),@Variable(key = "ppower", value = "1.618345"), @Variable(key = "months", value = "2")})
public class InvoiceServiceImpl extends R4SpringServiceSkeletal implements
		InvoiceService {

	private PrinterService printerService = null;

	private Log log = null;
	
	public Invoice generateInvoice(Customer customer, MeterReading reading) {

		log.info("Entering createInvoce");

		SemanticSession session = null;
		Invoice invoice = null;

		try {
			session = super.getInstance();
			session.execute(customer.getTariff());
			Tariff tariff = (Tariff) session.getContext().getResult("tariff");
			invoice = InvoiceFactory.getInstance(customer, reading, tariff);
			printerService.printInvoice(invoice);
		} catch (Throwable e) {
			log.error(e.getMessage());
		} finally {
			session.dispose();
			session = null;
		}
		return invoice;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setPrinterService(PrinterService printerService) {
		this.printerService = printerService;
	}
}
