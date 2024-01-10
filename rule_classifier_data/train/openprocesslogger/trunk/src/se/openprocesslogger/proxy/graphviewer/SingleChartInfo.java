package se.openprocesslogger.proxy.graphviewer;

import java.io.Serializable;

public class SingleChartInfo implements Serializable {

	private static final long serialVersionUID = -7163647676685882594L;
	
	private String varName; 	// Title
	private String chartName; 	// Title
	private int dataCount;		// Total number of data items
	private String tagType;		// Name of the corresponding tag-class used for presentation
	
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	public int getDataCount() {
		return dataCount;
	}
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	
}
