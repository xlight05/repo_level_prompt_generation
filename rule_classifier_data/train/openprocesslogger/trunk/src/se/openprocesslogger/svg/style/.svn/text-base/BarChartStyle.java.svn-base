/**
 * 
 */
package se.openprocesslogger.svg.style;

import java.io.Serializable;

public class BarChartStyle implements Serializable{
	private static final long serialVersionUID = 542327184640824717L;
	public double TEXT_MARGIN = 10;
	public double WIDTH = 0;
	public double HEIGHT = 20;
	public double TITLE_X_DIST = 50;
	public double TITLE_Y_DIST = 5;
	public double MARGIN_TOP = 30;
	public double MARGIN_BOTTOM = 50;
	public double MARGIN_LEFT = 0;
	public double TOTAL_HEIGHT = HEIGHT + MARGIN_TOP + MARGIN_BOTTOM;
	public double TEXT_Y_DIST = 50; // Below x axis
	public double TEXT_X_DIST = 10; // Left of y axis
	
	public BarChartStyle(double l, double ew) {
		MARGIN_LEFT = l;
		WIDTH = ew;
	}
	
	public void init(){
		WIDTH = Style.defaultStyle.EVENT_WIDTH;
	}
	
	public double getTEXT_MARGIN() {
		return TEXT_MARGIN;
	}
	public void setTEXT_MARGIN(double text_margin) {
		TEXT_MARGIN = text_margin;
	}
	public double getWIDTH() {
		return WIDTH;
	}
	public void setWIDTH(double width) {
		WIDTH = width;
	}
	public double getHEIGHT() {
		return HEIGHT;
	}
	public void setHEIGHT(double height) {
		HEIGHT = height;
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

	public double getMARGIN_TOP() {
		return MARGIN_TOP;
	}

	public void setMARGIN_TOP(double margin_top) {
		MARGIN_TOP = margin_top;
	}

	public double getMARGIN_BOTTOM() {
		return MARGIN_BOTTOM;
	}

	public void setMARGIN_BOTTOM(double margin_bottom) {
		MARGIN_BOTTOM = margin_bottom;
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
	
}