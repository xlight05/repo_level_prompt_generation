package org.jsemantic.services.examples.energy.invoice.impl;

import org.apache.commons.logging.Log;
import org.jsemantic.services.examples.energy.model.Invoice;

public class PrinterService {

	private Log log = null;

	public void setLog(Log log) {
		this.log = log;
	}

	public void printInvoice(Invoice invoice) {

		if (log.isInfoEnabled()) {
			log.info(invoice);
		}
	}

}
