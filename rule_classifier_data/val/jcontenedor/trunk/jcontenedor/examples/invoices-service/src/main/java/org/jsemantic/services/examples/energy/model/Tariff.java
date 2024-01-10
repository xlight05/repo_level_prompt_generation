package org.jsemantic.services.examples.energy.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tariff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type = "";
	private String powerMode = "";
	private BigDecimal power = null;

	private BigDecimal kwh = null;
	private BigDecimal ppower = null;

	private Integer months = null;

	public Tariff() {

	}

	public Tariff(String powerMode, String power) {

	}

	public String toString() {
		return "Type: " + type + "	Power Mode: " + powerMode + "	Hired Power: "
				+ power;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPowerMode() {
		return powerMode;
	}

	public void setPowerMode(String powerMode) {
		this.powerMode = powerMode;
	}

	public BigDecimal getPower() {
		return power;
	}

	public void setPower(BigDecimal power) {
		this.power = power;
	}

	public BigDecimal getKwh() {
		return kwh;
	}

	public void setKwh(BigDecimal kwh) {
		this.kwh = kwh;
	}

	public BigDecimal getPpower() {
		return ppower;
	}

	public void setPpower(BigDecimal ppower) {
		this.ppower = ppower;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

}
