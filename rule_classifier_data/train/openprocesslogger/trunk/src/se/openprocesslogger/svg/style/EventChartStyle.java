package se.openprocesslogger.svg.style;

public class EventChartStyle extends BarChartStyle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8971082156083686684L;

	public double EVENT_WIDTH = 3;
	
	public EventChartStyle(double l, double ew) {
		super(l, ew);	
	}

	public double getEVENT_WIDTH() {
		return EVENT_WIDTH;
	}

	public void setEVENT_WIDTH(double event_width) {
		EVENT_WIDTH = event_width;
	}
}
