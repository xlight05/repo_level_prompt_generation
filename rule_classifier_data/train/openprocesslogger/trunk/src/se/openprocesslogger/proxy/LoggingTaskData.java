package se.openprocesslogger.proxy;

import java.io.Serializable;
import java.util.Arrays;

public class LoggingTaskData implements Serializable, Comparable<LoggingTaskData>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2636524267216351300L;
	
	private String name;
	private String[] varNames; //TODO: Replace with tree-description
	private long logTaskId;
	private boolean isStarted;
	
	public LoggingTaskData(){}
	
	public LoggingTaskData(long logTaskId, String name, String[] vars) {
		super();
		this.name = name;
		this.varNames = vars;
		Arrays.sort(varNames);
		this.logTaskId = logTaskId;
		this.isStarted = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getVarNames() {
		return varNames;
	}

	public void setVarNames(String[] varNames) {
		this.varNames = varNames;
	}

	public long getLogTaskId() {
		return logTaskId;
	}

	public void setLogTaskId(long logTaskId) {
		this.logTaskId = logTaskId;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	@Override
	public int compareTo(LoggingTaskData arg0) {
		return this.name.compareTo(arg0.name);
	}
}
