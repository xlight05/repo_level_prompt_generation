package com.xiaonei.api;

/**
 */
public enum ProfileField {
	NAME("name"), 
	SEX("sex"), 
	STAR("star"), 
	BIRTHDAY("birthday"), 
	TINYURL("tinyurl"), 
	HEADURL("headurl"), 
	MAINURL("mainurl"), 
	HOMETOWN_LOCATION("hometown_location"),
	WORK_HISTORY("work_history"), 
	UNIVERSITY_HISTORY("university_history"), 
	HS_HISTORY("hs_history"),
	ZIDOU("zidou")
	;

	private String fieldName;

	ProfileField(String name) {
		this.fieldName = name;
	}

	/**
	 * @return the name of the field
	 */
	public String fieldName() {
		return this.fieldName;
	}

	public String toString() {
		return fieldName();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean isName(String name) {
		return toString().equals(name);
	}
}
