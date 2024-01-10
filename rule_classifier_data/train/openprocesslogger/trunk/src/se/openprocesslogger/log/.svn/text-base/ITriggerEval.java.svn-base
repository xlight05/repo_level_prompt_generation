package se.openprocesslogger.log;

import se.openprocesslogger.proxy.TriggerProxy;



public interface ITriggerEval{

	public abstract Object getLimit();

	public abstract void setLimit(Object limit);

	public abstract Object getTopScope();

	public abstract void setTopScope(Object topScope);

	public abstract Object getBottomScope();

	public abstract void setBottomScope(Object bottomScope);
	
	public abstract Object getEqualityValue();
	
	public abstract TriggerOptions getTriggerOption();
	
	public abstract boolean eval(Byte value) throws TriggerEvalException;

	public abstract boolean eval(Character value) throws TriggerEvalException;

	public abstract boolean eval(Short value) throws TriggerEvalException;

	public abstract boolean eval(Integer value) throws TriggerEvalException;

	public abstract boolean eval(Long value) throws TriggerEvalException;

	public abstract boolean eval(Float value) throws TriggerEvalException;

	public abstract boolean eval(Double value) throws TriggerEvalException;

	public abstract boolean eval(Object value) throws TriggerEvalException;
	
	public abstract boolean eval(Boolean value) throws TriggerEvalException;
	
	public abstract boolean eval(String value) throws TriggerEvalException;

	public abstract void update(TriggerProxy trigger);

	public abstract TriggerProxy proxify();

	public abstract String getDescription();
}