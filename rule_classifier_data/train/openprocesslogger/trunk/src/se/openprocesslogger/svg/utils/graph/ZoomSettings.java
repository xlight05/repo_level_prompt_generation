package se.openprocesslogger.svg.utils.graph;


import java.io.Serializable;

public class ZoomSettings implements Serializable{

	private String polylinePoints = "";
	private double xScalingFactor = 1;
	
	public ZoomSettings(GraphBuilder current, double[] data) {
		double[] dataTemp = current.getData();
		current.setData(data); // adjusts scaling
		polylinePoints = current.getPolylinePoints();
		xScalingFactor = current.xScalingFactor;
		current.setData(dataTemp);
	}
	
	public String getPolylinePoints(){
		return polylinePoints;
	}
	
	public double getXScalingFactor() {
		return xScalingFactor;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1846781801149938733L;

	
}
