package se.openprocesslogger.templates;

import se.openprocesslogger.log.ITriggerEval;

public class ValueTemplate {
	
	private String valueName;
	private ITriggerEval trigger;
	
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public ITriggerEval getTrigger() {
		return trigger;
	}
	public void setTrigger(ITriggerEval trigger) {
		this.trigger = trigger;
	}
	
	
}
