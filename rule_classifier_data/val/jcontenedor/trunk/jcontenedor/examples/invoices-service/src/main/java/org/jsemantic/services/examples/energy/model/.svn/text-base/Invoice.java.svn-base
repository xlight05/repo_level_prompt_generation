package org.jsemantic.services.examples.energy.model;

import java.math.BigDecimal;
import java.util.Date;

public class Invoice {

	private Long number = null;

	private Date date = null;

	private Customer customer = null;

	private BigDecimal total = null;

	public Invoice() {

	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public BigDecimal calculate(Tariff tariff, MeterReading reading) {

		BigDecimal totalPower = (tariff.getPpower().multiply(tariff.getPower()))
				.multiply(new BigDecimal(2));
		totalPower = totalPower.setScale(2, BigDecimal.ROUND_DOWN);

		BigDecimal totalConsume = tariff.getKwh().multiply(
				new BigDecimal(reading.getConsume()));

		totalConsume = totalConsume.setScale(2, BigDecimal.ROUND_DOWN);

		BigDecimal total = totalPower.add(totalConsume);
		total.setScale(2, BigDecimal.ROUND_DOWN);
		this.setTotal(total);
		return total;
	}
	
	public String toString() {
		String invoice = "Invoice Number: " + number + "\n" +  customer
				+ "\nTotal: " + total;
		return invoice;
	}

}
