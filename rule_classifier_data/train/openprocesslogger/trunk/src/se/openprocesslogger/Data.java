package se.openprocesslogger;

import java.io.Serializable;
import java.util.Date;

public class Data implements Comparable<Data>, Serializable{
	
	/*
	private static final int STARTLINE=1;
	private static final int ENDLINE=2;
	private static final int CONTINUELINE=3;
	*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -499504837887053734L;
	
	public static final int STARTPOINT=1;
	public static final int ENDPOINT=2;
	public static final int SINGLEPOINT=3;
	public static final int CONTINUEPOINT=0;
	
	private String name;
	private long equipment;
	private long loggingTask;
	private Object value;
	private Date timestamp;
	private long id;
	private long lineInfo;
	private int pointType;
	
	public Data(String name, long equipment, long loggingTask, Object value,
			Date timestamp) {
		super();
		this.name = name;
		this.equipment = equipment;
		this.loggingTask = loggingTask;
		this.value = value;
		this.timestamp = timestamp;
		this.pointType = CONTINUEPOINT;
	}

	public Data(long id, String name, long equipment, long loggingTask, Object value,
			Date timestamp,int lineInfo, int pointType) {
		super();
		this.id = id;
		this.name = name;
		this.equipment = equipment;
		this.loggingTask = loggingTask;
		this.value = value;
		this.timestamp = timestamp;
		this.lineInfo=lineInfo;
		this.pointType = pointType;
	}
		
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public long getEquipment() {
		return equipment;
	}

	public long getLoggingTask() {
		return loggingTask;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public long getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(long lineInfo) {
		this.lineInfo = lineInfo;
	}
		
	public int getPointType() {
		return pointType;
	}

	public void setPointType(int pointType) {
		this.pointType = pointType;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Data)) return false;
		Data d = (Data)obj;
						
		return (this.equipment == d.equipment &&
				this.loggingTask == d.loggingTask &&
				this.name.equals(d.name) &&
				this.timestamp.equals(d.timestamp) &&
				this.value.equals(d.value)) &&
				this.pointType == d.pointType;
	}

	@Override
	public int compareTo(Data arg0) {
		return this.getTimestamp().compareTo(arg0.timestamp);
	}
	
}
