package se.openprocesslogger.proxy.graphviewer;

import java.io.Serializable;

import se.openprocesslogger.svg.style.Style;

public class GraphMetadataResponse implements Serializable {

	private static final long serialVersionUID = -3838601396904293142L;
	
	private SingleChartInfo[] charts;
	private Style style;
	private long fromMillis;
	private long toMillis;
	private long logTaskId;
	
	public SingleChartInfo[] getCharts() {
		return charts;
	}

	public void setCharts(SingleChartInfo[] charts) {
		this.charts = charts;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public long getFromMillis() {
		return fromMillis;
	}

	public void setFromMillis(long fromMillis) {
		this.fromMillis = fromMillis;
	}

	public long getToMillis() {
		return toMillis;
	}

	public void setToMillis(long is) {
		this.toMillis = is;
	}

	public long getLogTaskId() {
		return logTaskId;
	}

	public void setLogTaskId(long logTaskId) {
		this.logTaskId = logTaskId;
	}
	
}
