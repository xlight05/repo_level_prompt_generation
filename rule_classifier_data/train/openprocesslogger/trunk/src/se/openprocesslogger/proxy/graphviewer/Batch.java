package se.openprocesslogger.proxy.graphviewer;


import se.openprocesslogger.svg.data.DataPoint;

public class Batch {

	private static final long serialVersionUID = 1889075180283645231L;
	DataPoint[] data;
	
	public DataPoint[] getData() {
		return data;
	}
	public void setData(DataPoint[] data) {
		this.data = data;
	}
}
