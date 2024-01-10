package org.jsemantic.services.examples.energy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsemantic.services.examples.energy.MockDataGenerator;
import org.jsemantic.services.examples.energy.invoice.InvoiceService;
import org.jsemantic.services.examples.energy.model.Customer;
import org.jsemantic.services.examples.energy.model.Invoice;
import org.jsemantic.services.examples.energy.model.MeterReading;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class InvoiceController extends AbstractController {

	private InvoiceService invoiceService = null;

	private MockDataGenerator generator = null;

	public void setGenerator(MockDataGenerator generator) {
		this.generator = generator;
	}

	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		Customer customer = generator.getCustomer();
		MeterReading reading = generator.getMeterReading();

		Invoice invoice = invoiceService.generateInvoice(customer, reading);

		ModelAndView mv = new ModelAndView("invoice");
		mv.addObject(invoice);
		return mv;
	}

}
