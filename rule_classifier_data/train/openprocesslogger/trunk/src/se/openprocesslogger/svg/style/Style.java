package se.openprocesslogger.svg.style;

import java.io.Serializable;

import se.openprocesslogger.svg.utils.SvgAppender;


public class Style implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725698326242889318L;
	
	public double graphWidth = 1300;
	public double MARGIN_LEFT = 70;
	public double MARGIN_RIGHT = 70;
	public double EVENT_WIDTH = graphWidth - MARGIN_LEFT - MARGIN_RIGHT;
	public double SUB_CHART_HEIGHT = 15; // A sub chart is below an analog or digital graph, just above the x-axis. It is a link to an existing bar chart and scaled to this size.
		
	public String chartAxesStyle = "stroke:blue; stroke-width: 2; fill : none;";
	public String valLineStyle = "stroke:blue; stroke-width: 1; fill : none;";
	public String lineStyle = "stroke:red; stroke-width: 1; fill : none;";
	public String titleTextSize = "15";
	public String titleTextStyle = SvgAppender.defaultTextStyle+"font-weight:bold;";
	
	public static Style defaultStyle = new Style();
	
	public AnalogChartStyle analogChart = new AnalogChartStyle(MARGIN_LEFT, MARGIN_RIGHT, EVENT_WIDTH);
	public DigitalChartStyle digitalChart = new DigitalChartStyle(MARGIN_LEFT, MARGIN_RIGHT, EVENT_WIDTH);
	public BarChartStyle barChart = new BarChartStyle(MARGIN_LEFT, EVENT_WIDTH);
	public EventChartStyle eventChart = new EventChartStyle(MARGIN_LEFT, EVENT_WIDTH);
	
	public MouseMarkerStyle mouseMarker = new MouseMarkerStyle();
	public ZoomSquareStyle zoomSquare = new ZoomSquareStyle();
	
	public double getGraphWidth() {
		return graphWidth;
	}

	public void setGraphWidth(double graphWidth) {
		this.graphWidth = graphWidth;
	}

	public double getMARGIN_LEFT() {
		return MARGIN_LEFT;
	}

	public void setMARGIN_LEFT(double margin_left) {
		MARGIN_LEFT = margin_left;
	}

	public double getMARGIN_RIGHT() {
		return MARGIN_RIGHT;
	}

	public void setMARGIN_RIGHT(double margin_right) {
		MARGIN_RIGHT = margin_right;
	}

	public double getEVENT_WIDTH() {
		return EVENT_WIDTH;
	}

	public void setEVENT_WIDTH(double event_width) {
		EVENT_WIDTH = event_width;
	}

	public String getChartAxesStyle() {
		return chartAxesStyle;
	}

	public void setChartAxesStyle(String chartAxesStyle) {
		this.chartAxesStyle = chartAxesStyle;
	}

	public String getValLineStyle() {
		return valLineStyle;
	}

	public void setValLineStyle(String valLineStyle) {
		this.valLineStyle = valLineStyle;
	}

	public String getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(String lineStyle) {
		this.lineStyle = lineStyle;
	}

	public String getTitleTextSize() {
		return titleTextSize;
	}

	public void setTitleTextSize(String titleTextSize) {
		this.titleTextSize = titleTextSize;
	}

	public String getTitleTextStyle() {
		return titleTextStyle;
	}

	public void setTitleTextStyle(String titleTextStyle) {
		this.titleTextStyle = titleTextStyle;
	}

	public AnalogChartStyle getAnalogChart() {
		return analogChart;
	}

	public void setAnalogChart(AnalogChartStyle analogChart) {
		this.analogChart = analogChart;
	}

	public DigitalChartStyle getDigitalChart() {
		return digitalChart;
	}

	public void setDigitalChart(DigitalChartStyle digitalChart) {
		this.digitalChart = digitalChart;
	}

	public BarChartStyle getBarChart() {
		return barChart;
	}

	public void setBarChart(BarChartStyle barChart) {
		this.barChart = barChart;
	}

	public MouseMarkerStyle getMouseMarker() {
		return mouseMarker;
	}

	public void setMouseMarker(MouseMarkerStyle mouseMarker) {
		this.mouseMarker = mouseMarker;
	}

	public EventChartStyle getEventChart() {
		return eventChart;
	}

	public void setEventChart(EventChartStyle eventChart) {
		this.eventChart = eventChart;
	}

	public ZoomSquareStyle getZoomSquare() {
		return zoomSquare;
	}

	public void setZoomSquare(ZoomSquareStyle zoomSquare) {
		this.zoomSquare = zoomSquare;
	}

	public double getSUB_CHART_HEIGHT() {
		return SUB_CHART_HEIGHT;
	}

	public void setSUB_CHART_HEIGHT(double sub_chart_height) {
		SUB_CHART_HEIGHT = sub_chart_height;
	}
	
}	