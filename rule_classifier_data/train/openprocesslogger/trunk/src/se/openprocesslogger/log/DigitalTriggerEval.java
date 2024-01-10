package se.openprocesslogger.log;



public class DigitalTriggerEval extends TriggerEval {
	
	public static TriggerOptions[] getTriggerOptions(){
		TriggerOptions[] options = new TriggerOptions[5];
		options[0]=TriggerOptions.onChange;options[1]=TriggerOptions.onEqualTo;options[2]=TriggerOptions.onNotEqualTo;
		options[3]=TriggerOptions.onFallingEdge;options[4]=TriggerOptions.onRisingEdge;
		return options;
	}

	public DigitalTriggerEval(TriggerOptions option) {
		super(option);
	}
	
	public void setEqualityValue(Boolean value){
		super.setEqualityValue(value);
	}
	
	public Boolean getEqualityValue(){
		return (Boolean)super.getEqualityValue();
	}
	@Override
	public boolean eval(Boolean value) throws TriggerEvalException{
		if(this.getTriggerOption()==TriggerOptions.onChange){
			return evalChange(value);
		}else if(this.getTriggerOption()==TriggerOptions.onEqualTo){
			return evalEquality(value);
		}else if(this.getTriggerOption()==TriggerOptions.onNotEqualTo){
			return !evalEquality(value);
		}else if(this.getTriggerOption()==TriggerOptions.onFallingEdge){
			return evalFallingEdge(value);
		}else if(this.getTriggerOption()==TriggerOptions.onRisingEdge){
			return evalRisingEdge(value);
		}
		throw new TriggerEvalException("Invalid trigger option specified.");
	}
	
	protected boolean evalFallingEdge(Boolean value){
		boolean trigged = false;
		if(value != null){
			if(this.getLastVal() != null && (Boolean)this.getLastVal() && !value){
				if(System.currentTimeMillis() - getLastTriggTime() > getTriggerDelay()){
					setLastTriggTime(System.currentTimeMillis());
					trigged = true;
				}
			}
		}
		this.setLastVal(value);
		return trigged;
	}

	protected boolean evalRisingEdge(Boolean value){
		boolean trigged = false;
		long timeNow = System.currentTimeMillis();
		if(value != null){
			if(this.getLastVal() != null && !(Boolean)this.getLastVal() && value){
				if(timeNow - getLastTriggTime() > getTriggerDelay()){
					setLastTriggTime(timeNow);
					trigged = true;
				}
			}
		}
		this.setLastVal(value);
		return trigged;
	}
	
	@Override
	public String getDescription(){
		if(getTriggerOption() == TriggerOptions.onFallingEdge){
			return "edge is falling";
		}else if(getTriggerOption() == TriggerOptions.onRisingEdge){
			return "edge is rising";
		}
		return super.getDescription();
	}
}
