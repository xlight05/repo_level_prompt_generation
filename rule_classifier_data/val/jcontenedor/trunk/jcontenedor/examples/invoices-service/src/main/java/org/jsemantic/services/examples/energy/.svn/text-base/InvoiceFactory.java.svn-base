package org.jsemantic.services.examples.energy;

import java.util.Date;
import java.util.Random;

import org.jsemantic.services.examples.energy.model.Customer;
import org.jsemantic.services.examples.energy.model.Invoice;
import org.jsemantic.services.examples.energy.model.MeterReading;
import org.jsemantic.services.examples.energy.model.Tariff;

public class InvoiceFactory {
	
	private InvoiceFactory() {
		//
	}

	public static Invoice getInstance(Customer customer, MeterReading reading,
			Tariff tariff) {

		Invoice invoice = new Invoice();
		invoice.setNumber(generateInvoiceId());

		invoice.setDate(new Date());
		invoice.setCustomer(customer);
		invoice.calculate(tariff, reading);
		return invoice;
	}

	private static Long generateInvoiceId() {
		Random seed = new Random();
		Long id = new Long(seed.nextLong());
		if (id < 0) {
			id *= -1;
		}
		return id;
	}
}
