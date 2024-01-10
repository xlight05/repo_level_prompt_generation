package se.openprocesslogger.proxy.graphviewer;

import java.io.Serializable;

public class BatchInfo implements Serializable {
	private static final long serialVersionUID = -3789984980303425052L;
	
	long from;
	long to;
	String varName;
	int batchSize;
	long logTaskId = -1;
	
	public long getFrom() {
		return from;
	}
	public void setFrom(long from) {
		this.from = from;
	}
	public long getTo() {
		return to;
	}
	public void setTo(long to) {
		this.to = to;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	public long getLogTaskId() {
		return logTaskId;
	}
	public void setLogTaskId(long logTaskId) {
		this.logTaskId = logTaskId;
	}	
}
