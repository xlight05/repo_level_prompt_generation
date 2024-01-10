package se.openprocesslogger.proxy;

import java.io.Serializable;
import java.util.HashMap;

public class SubscriptionProxy implements Serializable{
	private String valueAddress;
	private boolean onChange=false;
	private boolean onTimer=false;
	private long timerInterval=0;
	private boolean useRingBuffer=false;
	private long logTaskId=0;
	private long equipmentId=0;
	private boolean useTrigger;
	// Buffer settings
	private long countBefore;
	private long countAfter;
	private boolean usingSeconds;
	private String description;
	private HashMap<String, TriggerProxy> triggers;
	private boolean useExternalTrigger=false;
	
	private TriggerProxy triggerProxy;
	
	private String[] triggerOptions;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6389784926278076582L;

	public SubscriptionProxy(){
		triggers = new HashMap<String, TriggerProxy>();
	}
	
	public void setCountBefore(long countBefore) {
		this.countBefore = countBefore;
	}

	public void setCountAfter(long countAfter) {
		this.countAfter = countAfter;
	}

	public void setUsingSeconds(boolean usingSeconds) {
		this.usingSeconds = usingSeconds;
	}

	public long getCountBefore() {
		return countBefore;
	}


	public long getCountAfter() {
		return countAfter;
	}


	public boolean isUsingSeconds() {
		return usingSeconds;
	}

	public boolean isOnChange() {
		return onChange;
	}

	public void setOnChange(boolean onChange) {
		this.onChange = onChange;
	}

	public boolean isOnTimer() {
		return onTimer;
	}

	public void setOnTimer(boolean onTimer) {
		this.onTimer = onTimer;
	}

	public long getTimerInterval() {
		return timerInterval;
	}

	public void setTimerInterval(long timerInterval) {
		this.timerInterval = timerInterval;
	}

	public boolean isUseRingBuffer() {
		return useRingBuffer;
	}

	public void setUseRingBuffer(boolean useRingBuffer) {
		this.useRingBuffer = useRingBuffer;
	}

	public long getLogTaskId() {
		return logTaskId;
	}

	public void setLogTaskId(long logtaskId) {
		this.logTaskId = logtaskId;
	}

	public long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(long equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public String[] getTriggerOptions(){
		return this.triggerOptions;
	}

	public void setTriggerOptions(String[] triggerOptions) {
		this.triggerOptions = triggerOptions;
	}

	public TriggerProxy getTrigger() {
		return triggerProxy;
	}

	public void setTrigger(TriggerProxy triggerProxy) {
		this.triggerProxy = triggerProxy;
	}

	public String getValueAddress() {
		return valueAddress;
	}

	public void setValueAddress(String valueAddress) {
		this.valueAddress = valueAddress;
	}

	public boolean isUseTrigger() {
		return useTrigger;
	}

	public void setUseTrigger(boolean useTrigger) {
		this.useTrigger = useTrigger;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTriggers(HashMap<String, TriggerProxy> map){
		triggers = map;
	}
	
	public HashMap<String, TriggerProxy> getTriggers(){
		return triggers;
	}

	public void setUseExternalTrigger(boolean useExternalTrigger) {
		this.useExternalTrigger = useExternalTrigger;
	}

	public boolean isUseExternalTrigger() {
		return useExternalTrigger;
	}
}
