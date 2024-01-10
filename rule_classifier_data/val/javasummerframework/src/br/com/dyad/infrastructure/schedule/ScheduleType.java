package br.com.dyad.infrastructure.schedule;

public enum ScheduleType {
		
	Interval,
	Once,
	Daily,
	Monthly,
	Weekly;
	
	public Integer getCode() {
		switch (this) {
		case Interval:
			return 1;
		case Once:
			return 2;
		case Daily:
			return 3;
		case Monthly:
			return 4;
		case Weekly:
			return 5;
		default:
			return null;
		}
	}
	
	private String getDescription() {
		return this.toString();
	}
}