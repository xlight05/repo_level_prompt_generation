package se.openprocesslogger.log;

import se.openprocesslogger.proxy.TriggerProxy;




public class TriggerEval implements ITriggerEval{

	public static TriggerOptions[] getPossibleOptions(){
		TriggerOptions[] options = new TriggerOptions[3];
		options[0]=TriggerOptions.onChange; options[1]= TriggerOptions.onEqualTo; options[2]=TriggerOptions.onNotEqualTo;
		return options;
	}
	
	private TriggerOptions option = TriggerOptions.onChange;
	private Object lastValue = null;
	private Object equalityValue = null;
	private long triggerDelay = 0L;
	private long lastTriggTime = 0L;
	
	public static TriggerOptions[] getTriggerOptions() {
		TriggerOptions[] options= new TriggerOptions[8];
		options[0]=TriggerOptions.onChange;options[1]=TriggerOptions.onEqualTo;
		options[2]=TriggerOptions.onNotEqualTo;options[3]=TriggerOptions.onOverLimit;
		options[4]=TriggerOptions.onUnderLimit;options[5]=TriggerOptions.onPassingLimit;
		options[6]=TriggerOptions.onInScope;options[7]=TriggerOptions.onOutOfScope;
		return options;
	}
	
	public TriggerEval(TriggerOptions option){
		this.option=option;
	}
	
	public TriggerOptions getTriggerOption(){
		return this.option;
	}
	
	public void setTriggerOption(TriggerOptions option){
		this.option=option;
	}
	
	public Object getEqualityValue() {
		return equalityValue;
	}

	public void setEqualityValue(Object equalityValue) {
		this.equalityValue = equalityValue;
	}

	protected Object getLastVal(){
		return this.lastValue;
	}
	
	@Override
	public boolean eval(Boolean value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(Byte value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(Character value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(Double value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(Float value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(Integer value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(Long value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(Short value) throws TriggerEvalException {
		throw new TriggerEvalException("This class can't evaluate this type.");
	}

	@Override
	public boolean eval(String value) throws TriggerEvalException {
			return eval((Object)value);
	}

	@Override
	public Object getBottomScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLimit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTopScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBottomScope(Object bottomScope) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLimit(Object limit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTopScope(Object topScope) {
		// TODO Auto-generated method stub
		
	}

	protected void setLastVal(Object value){
		this.lastValue=value;
	}
	
	public boolean eval(Object value) throws TriggerEvalException{
		if(this.getTriggerOption()==TriggerOptions.onChange){
			return evalChange(value);
		}else if(this.getTriggerOption()==TriggerOptions.onEqualTo){
			return evalEquality(value);
		}else if(this.getTriggerOption()==TriggerOptions.onNotEqualTo){
			return !evalEquality(value);
		}
		throw new TriggerEvalException("Wrong trigger option specified.");
	}
	
	protected boolean evalChange(Object value){
		boolean trigged = false;
		
		if(this.getLastVal()==null)
			trigged = (value != null);
		else
			trigged = (!this.getLastVal().equals(value));
		
		if(trigged){
			if(System.currentTimeMillis() - getLastTriggTime() > getTriggerDelay())
				setLastTriggTime(System.currentTimeMillis());
			else
				trigged = false;
			this.setLastVal(value);
		}
		return trigged;
	}
	
	protected boolean evalEquality(Object value){
		boolean trigged = false;
		if(this.getEqualityValue()==null&&value==null){
			trigged = true;
		}
		else if(this.getEqualityValue()==null){
			trigged = false;
		}
		if(this.getEqualityValue().equals(value)){
			trigged = true;
		}
		if(trigged){
			if(System.currentTimeMillis() - getLastTriggTime() > getTriggerDelay())
				setLastTriggTime(System.currentTimeMillis());
			else
				trigged = false;
		}
		return trigged;
	}

	@Override
	public TriggerProxy proxify() {
		TriggerProxy proxy = new TriggerProxy();
		proxy.setTrigOption(option.toString());
		TriggerBuilder.setTriggerProperty(proxy, this);
		proxy.setProperty("equalityValue", this.equalityValue);
		proxy.setTriggerDelay(triggerDelay);
		return proxy;
	}
	
	@Override
	public void update(TriggerProxy proxy) {
		this.option = TriggerOptions.valueOf(proxy.getTrigOption());
		this.equalityValue = proxy.getProperty("equalityValue");
		this.triggerDelay = proxy.getTriggerDelay();
	}
	
	public String getDescription(){
		if(this.option == TriggerOptions.onEqualTo){
			return "equal to "+this.equalityValue;
		}else if(this.option == TriggerOptions.onNotEqualTo){
			return "not equal to " +this.equalityValue;
		}else{
			return "changed";
		}
	}

	public void setTriggerDelay(long triggerDelay) {
		this.triggerDelay = triggerDelay;
	}

	public long getTriggerDelay() {
		return triggerDelay;
	}

	protected void setLastTriggTime(long lastTriggTime) {
		this.lastTriggTime = lastTriggTime;
	}

	protected long getLastTriggTime() {
		return lastTriggTime;
	}
}
