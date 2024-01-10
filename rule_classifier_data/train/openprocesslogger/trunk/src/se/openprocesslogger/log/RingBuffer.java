package se.openprocesslogger.log;

import java.util.LinkedList;

import se.openprocesslogger.Data;

public class RingBuffer {
	
	private long countBefore=0;
	private long countAfter=0;
	private boolean usingSeconds=false;
	private boolean triggered = false;
	
	private LinkedList<Data> buffer = new LinkedList<Data>();
	private int gotAfter;
	private long triggeredAt;

	public long getCountBefore() {
		return countBefore;
	}

	public void setCountBefore(long countBefore) {
		this.countBefore = countBefore;
	}

	public long getCountAfter() {
		return countAfter;
	}

	public void setCountAfter(long countAfter) {
		this.countAfter = countAfter;
	}

	public boolean isUsingSeconds() {
		return usingSeconds;
	}

	public void setUsingSeconds(boolean usingSeconds) {
		this.usingSeconds = usingSeconds;
	}
	
	public boolean addData(Data data){
		buffer.addLast(data);
		if(!triggered){
			if(!usingSeconds){
				while(buffer.size()>countBefore){
					buffer.removeFirst();					
				}
			}else{
				long now = System.currentTimeMillis();
				while(((now - buffer.getFirst().getTimestamp().getTime()))>countBefore*1000){
					buffer.removeFirst();
				}
			}
			return false;
		}else{
			++gotAfter;
			if(!usingSeconds){
				while(gotAfter > countAfter){
					buffer.removeLast(); // Too many in buffer
					--gotAfter;
				}
				if(countAfter == gotAfter){
					return true;
				}
			}else{
				boolean bufferFilled = false;
				while(buffer.getLast().getTimestamp().getTime() - triggeredAt > (countAfter*1000)){
					bufferFilled = true;
					buffer.removeLast();
				}
				return bufferFilled;
			}
			return false;
		}
	}
	
	public boolean trigger(){
		this.triggered=true;
		triggeredAt = System.currentTimeMillis();
		if(countAfter == 0){
			return true;
		}
		return false;
	}
	
	public Data[] getData(){
		Data[] datas = new Data[buffer.size()];
		datas = buffer.toArray(datas);
		if(buffer.size() >=2){
			datas[0].setPointType(Data.STARTPOINT);
			datas[datas.length-1].setPointType(Data.ENDPOINT);
		}else if(buffer.size() == 1){
			datas[0].setPointType(Data.SINGLEPOINT);
		}
		buffer.clear();
		triggered=false;
		return datas;
	}

	public boolean hasTriggered() {
		return this.triggered;
	}
}
