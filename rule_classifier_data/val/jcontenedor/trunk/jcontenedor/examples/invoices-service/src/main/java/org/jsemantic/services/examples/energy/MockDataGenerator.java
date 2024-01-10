package org.jsemantic.services.examples.energy;

import java.util.Random;

import org.jsemantic.services.examples.energy.model.Customer;
import org.jsemantic.services.examples.energy.model.MeterReading;

public class MockDataGenerator {

	public Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(generateCustomerId());
		customer.setTariff(generateTariff());
		return customer;
	}

	public MeterReading getMeterReading() {
		MeterReading reading = new MeterReading();
		reading.setMeterId(generateMeterId());
		reading = generateReadings(reading);
		return reading;
	}

	private MeterReading generateReadings(MeterReading reading) {

		Random seed = new Random();

		int previous = seed.nextInt() % 200000;
		if (previous < 0) {
			previous *= -1;
		}

		int actual =  (seed.nextInt() % 10000);
		if (actual < 0) {
			actual *= -1;
		}
		
		actual+=previous;
		
		reading.setActualReading(new Integer(actual));
		reading.setPreviousReading(new Integer(previous));

		return reading;
	}

	private String generateCustomerId() {
		Random seed = new Random();

		long id = seed.nextLong() % 999999999999L;
		if (id < 0) {
			id *= -1;
		}

		return new Long(id).toString();

	}

	private String generateMeterId() {
		Random seed = new Random();

		long id = seed.nextLong() % 999999999L;
		if (id < 0) {
			id *= -1;
		}

		return new Long(id).toString();
	}

	private String generateTariff() {
		Random seed = new Random();

		int type = seed.nextInt() % 3;

		if (type <= 0) {
			type = 1;
		}

		String tariff = "2.0." + new Integer(type);
		return tariff;
	}

}
