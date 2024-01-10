package se.openprocesslogger.svg.utils.graph;


import java.io.Serializable;

public class ChartProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591303164030393412L;
	
	private double eventWidth;
	private double chartHeight;
	private double chartStart;
	
	private String startDate;
	private String endDate;
	private long startTime; // s since beginning of day
	private long endTime; // s since beginning of day

	private double timeUnit;
	
	private double xScalingFactor;
	
	private double[] data;
	
	public double getEventWidth() {
		return eventWidth;
	}
	public void setEventWidth(double eventWidth) {
		this.eventWidth = eventWidth;
	}
	public double getChartHeight() {
		return chartHeight;
	}
	public void setChartHeight(double chartHeight) {
		this.chartHeight = chartHeight;
	}
	public double getChartStart() {
		return chartStart;
	}
	public void setChartStart(double chartStart) {
		this.chartStart = chartStart;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public double getXScalingFactor() {
		return xScalingFactor;
	}
	public void setXScalingFactor(double scalingFactor) {
		xScalingFactor = scalingFactor;
	}
	public double[] getData() {
		return data;
	}
	public void setData(double[] data) {
		this.data = data;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public void setTimeUnit(double timeUnit) {
		this.timeUnit = timeUnit;
	}
	public double getTimeUnit(){
		return this.timeUnit;
	}
}
