/**
 * 
 */
package se.openprocesslogger.svg.style;

import java.io.Serializable;

public class AnalogChartStyle implements Serializable{

	private static final long serialVersionUID = -7181111585669651547L;
	
	public double VALUE_HEIGHT = 250;
	public double MARGIN_TOP = 50;
	public double MARGIN_BOTTOM = 50;
	public double MARGIN_LEFT = 0;
	public double MARGIN_RIGHT = 0;
	public double WIDTH = 0;
	public double ARROW_SPACE = 20;
	public double VAL_CROSS_LINE = 5;
	public double TITLE_X_DIST = 50;
	public double TITLE_Y_DIST = 5; // Above "value field"
	public double TEXT_Y_DIST = 50; // Below x axis
	public double TEXT_X_DIST = 10; // Left of y axis
	
	public double TOTAL_HEIGHT = VALUE_HEIGHT + MARGIN_TOP + MARGIN_BOTTOM;
		
	public AnalogChartStyle(double l, double r, double ew) {
		MARGIN_LEFT = l;
		MARGIN_RIGHT = r;
		WIDTH = ew;
	}
	
	public double getVALUE_HEIGHT() {
		return VALUE_HEIGHT;
	}
	public void setVALUE_HEIGHT(double value_height) {
		VALUE_HEIGHT = value_height;
	}
	public double getWIDTH() {
		return WIDTH;
	}
	public void setWIDTH(double width) {
		WIDTH = width;
	}
	public double getARROW_SPACE() {
		return ARROW_SPACE;
	}
	public void setARROW_SPACE(double arrow_space) {
		ARROW_SPACE = arrow_space;
	}
	public double getVAL_CROSS_LINE() {
		return VAL_CROSS_LINE;
	}
	public void setVAL_CROSS_LINE(double val_cross_line) {
		VAL_CROSS_LINE = val_cross_line;
	}
	public double getTITLE_X_DIST() {
		return TITLE_X_DIST;
	}
	public void setTITLE_X_DIST(double title_x_dist) {
		TITLE_X_DIST = title_x_dist;
	}
	public double getTITLE_Y_DIST() {
		return TITLE_Y_DIST;
	}
	public void setTITLE_Y_DIST(double title_y_dist) {
		TITLE_Y_DIST = title_y_dist;
	}
	public double getMARGIN_LEFT() {
		return MARGIN_LEFT;
	}
	public void setMARGIN_LEFT(double margin_left) {
		MARGIN_LEFT = margin_left;
	}
	public double getTOTAL_HEIGHT() {
		return TOTAL_HEIGHT;
	}
	public void setTOTAL_HEIGHT(double total_height) {
		TOTAL_HEIGHT = total_height;
	}
	public double getMARGIN_TOP() {
		return MARGIN_TOP;
	}
	public void setMARGIN_TOP(double margin_top) {
		MARGIN_TOP = margin_top;
	}	
	
}