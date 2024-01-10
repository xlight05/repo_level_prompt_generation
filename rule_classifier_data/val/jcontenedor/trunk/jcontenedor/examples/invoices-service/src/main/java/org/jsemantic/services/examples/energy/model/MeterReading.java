package org.jsemantic.services.examples.energy.model;

public class MeterReading {
	
	private String meterId = null;
	
	private Integer actualReading = null;
	
	private Integer previousReading = null;
	
	private Integer consume = null;

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public Integer getActualReading() {
		return actualReading;
	}

	public void setActualReading(Integer actualReading) {
		this.actualReading = actualReading;
	}

	public Integer getPreviousReading() {
		return previousReading;
	}

	public void setPreviousReading(Integer previousReading) {
		this.previousReading = previousReading;
	}

	public Integer getConsume() {
		return this.actualReading - this.previousReading;
	}

	public void setConsume(Integer consume) {
		this.consume = consume;
	}
}
