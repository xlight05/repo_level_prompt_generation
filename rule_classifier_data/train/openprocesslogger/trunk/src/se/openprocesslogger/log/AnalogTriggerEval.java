package se.openprocesslogger.log;

import se.openprocesslogger.proxy.TriggerProxy;




public class AnalogTriggerEval extends TriggerEval{
	//private static Logger log = Logger.getLogger(AnalogTriggerEval.class);
	
	public static TriggerOptions[] getTriggerOptions() {
		TriggerOptions[] options= new TriggerOptions[8];
		options[0]=TriggerOptions.onChange;options[1]=TriggerOptions.onEqualTo;
		options[2]=TriggerOptions.onNotEqualTo;options[3]=TriggerOptions.onOverLimit;
		options[4]=TriggerOptions.onUnderLimit;options[5]=TriggerOptions.onPassingLimit;
		options[6]=TriggerOptions.onInScope;options[7]=TriggerOptions.onOutOfScope;
		return options;
	}

	private Object limit=null;
	private Object topScope = null;
	private Object bottomScope = null;
	

	public AnalogTriggerEval(TriggerOptions option) {
		super(option);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#getLimit()
	 */
	public Object getLimit() {
		return limit;
	}


	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#setLimit(java.lang.Object)
	 */
	public void setLimit(Object limit) {
		this.limit = limit;
	}


	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#getTopScope()
	 */
	public Object getTopScope() {
		return topScope;
	}


	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#setTopScope(java.lang.Object)
	 */
	public void setTopScope(Object topScope) {
		this.topScope = topScope;
	}


	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#getBottomScope()
	 */
	public Object getBottomScope() {
		return bottomScope;
	}


	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#setBottomScope(java.lang.Object)
	 */
	public void setBottomScope(Object bottomScope) {
		this.bottomScope = bottomScope;
	}


	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#eval(java.lang.Byte)
	 */
	public boolean eval(Byte value) throws TriggerEvalException{
		validateType(value);
		return eval((Object)value);
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#eval(java.lang.Character)
	 */
	public boolean eval(Character value) throws TriggerEvalException{
		validateType(value);
		return eval((Object)value);
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#eval(java.lang.Short)
	 */
	public boolean eval(Short value) throws TriggerEvalException{
		validateType(value);
		return eval((Object)value);
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#eval(java.lang.Integer)
	 */
	public boolean eval(Integer value) throws TriggerEvalException{
		validateType(value);
		return eval((Object)value);
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#eval(java.lang.Long)
	 */
	public boolean eval(Long value) throws TriggerEvalException{
		validateType(value);
		return eval((Object)value);
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#eval(java.lang.Float)
	 */
	public boolean eval(Float value) throws TriggerEvalException{
		validateType(value);
		return eval((Object)value);
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.ITriggerEval#eval(java.lang.Double)
	 */
	public boolean eval(Double value) throws TriggerEvalException{
		validateType(value);
		return eval((Object)value);
	}
	
	
	
	@Override
	public boolean eval(Object value) throws TriggerEvalException {
		// Handle types not supported by TriggerEval
		if(this.getTriggerOption() == TriggerOptions.onOverLimit){
			return evalOverlimit(value);
		}else if(this.getTriggerOption() == TriggerOptions.onPassingLimit){
			return evalPassingLimit(value);
		}else if(this.getTriggerOption() == TriggerOptions.onUnderLimit){
			return evalUnderLimit(value);
		}else if(this.getTriggerOption() == TriggerOptions.onInScope){
			return evalInScope(value);
		}else if(this.getTriggerOption() == TriggerOptions.onOutOfScope){
			return evalOutOfScope(value);
		}
		return super.eval(value);
	}

	protected void validateType(Object value) throws TriggerEvalException{
		if((this.getTriggerOption()==TriggerOptions.onInScope)||(this.getTriggerOption()==TriggerOptions.onOutOfScope)){
			if((!value.getClass().equals(bottomScope.getClass()))||(!value.getClass().equals(topScope.getClass()))){
				bottomScope = bottomScope.toString();
				topScope = topScope.toString();
				
				bottomScope = parse((String)bottomScope, value);
				topScope = parse((String)topScope, value);
				
			}
		}else if((this.getTriggerOption()==TriggerOptions.onOverLimit)||(this.getTriggerOption()==TriggerOptions.onUnderLimit)
				||(this.getTriggerOption()==TriggerOptions.onPassingLimit)){
			if(!value.getClass().equals(limit.getClass())){
				limit = limit.toString();
				limit = parse((String)limit, value);
			}
		}
	}
	
	private Object parse(String from, Object value) throws TriggerEvalException{
		if(value instanceof Byte){
			return Byte.parseByte(from);
		}else if(value instanceof Character){
			return new Character(from.charAt(0));
		}else if(value instanceof Short){
			return Short.parseShort(from);
		}else if(value instanceof Integer){
			return Integer.parseInt(from);
		}else if(value instanceof Long){
			return Long.parseLong(from);
		}else if(value instanceof Float){
			return Float.parseFloat(from);
		}else if(value instanceof Double){
			return Double.parseDouble(from);
		}
		throw new TriggerEvalException("Wrong data type used.");
	}
	
	protected boolean evalOverlimit(Object value)throws TriggerEvalException{
		if(limit==null)throw new TriggerEvalException("Limit variable not initialized.");
		if(value==null)throw new TriggerEvalException("Value to evaluate is not initialized.");

		boolean trigged = false;
		
		if(!(value instanceof Byte || value instanceof Character || value instanceof Double || value instanceof Float
				|| value instanceof Integer || value instanceof Long || value instanceof Short))
			throw new TriggerEvalException("Wrong data type used.");

		if(System.currentTimeMillis() - getLastTriggTime() < getTriggerDelay())
			return false;
		
		if(value instanceof Byte){
			if(((Byte)value)>((Byte)limit)){
				trigged = true;
			}
		}else if(value instanceof Character){
			if(((Character)value)>((Character)limit)){
				trigged = true;
			}
		}else if(value instanceof Short){
			if(((Short)value)>((Short)limit)){
				trigged = true;
			}
		}else if(value instanceof Integer){
			if(((Integer)value)>((Integer)limit)){
				trigged = true;
			}
		}else if(value instanceof Long){
			if(((Long)value)>((Long)limit)){
				trigged = true;
			}
		}else if(value instanceof Float){
			if(((Float)value)>((Float)limit)){
				trigged = true;
			}
		}else if(value instanceof Double){
			if(((Double)value)>((Double)limit)){
				trigged = true;
			}
		}
		if(trigged)
			setLastTriggTime(System.currentTimeMillis());
		return trigged;
	}
	
	protected boolean evalUnderLimit(Object value)throws TriggerEvalException{
		if(limit==null)throw new TriggerEvalException("Limit variable not initialized.");
		if(value==null)throw new TriggerEvalException("Value to evaluate is not initialized.");

		boolean trigged = false;
		
		if(!(value instanceof Byte || value instanceof Character || value instanceof Double || value instanceof Float
				|| value instanceof Integer || value instanceof Long || value instanceof Short))
			throw new TriggerEvalException("Wrong data type used.");

		if(System.currentTimeMillis() - getLastTriggTime() < getTriggerDelay())
			return false;
		
		if(value instanceof Byte){
			if(((Byte)value)<((Byte)limit)){
				trigged =  true;
			}
		}else if(value instanceof Character){
			if(((Character)value)<((Character)limit)){
				trigged = true;
			}
		}else if(value instanceof Short){
			if(((Short)value)<((Short)limit)){
				trigged = true;
			}
		}else if(value instanceof Integer){
			if(((Integer)value)<((Integer)limit)){
				trigged = true;
			}
		}else if(value instanceof Long){
			if(((Long)value)<((Long)limit)){
				trigged = true;
			}
		}else if(value instanceof Float){
			if(((Float)value)<((Float)limit)){
				trigged = true;
			}
		}else if(value instanceof Double){
			if(((Double)value)<((Double)limit)){
				trigged = true;
			}
		}
		if(trigged)
			setLastTriggTime(System.currentTimeMillis());
		return trigged;
	}	
	
	protected boolean evalPassingLimit(Object value) throws TriggerEvalException{
		if(limit==null)throw new TriggerEvalException("Limit variable not initialized.");
		if(value==null)throw new TriggerEvalException("Value to evaluate is not initialized.");
		
		boolean trigged = false;
		
		if(!(value instanceof Byte || value instanceof Character || value instanceof Double || value instanceof Float
				|| value instanceof Integer || value instanceof Long || value instanceof Short))
			throw new TriggerEvalException("Wrong data type used.");

		if(System.currentTimeMillis() - getLastTriggTime() >= getTriggerDelay() && this.getLastVal() != null){
			if(value instanceof Byte){
				if(((Byte)this.getLastVal() >= (Byte)limit && (Byte)value < (Byte)limit)
						|| ((Byte)this.getLastVal() <= (Byte)limit && (Byte)value > (Byte)limit)){
					trigged = true;
				}
			}else if(value instanceof Character){
				if(((Character)this.getLastVal() >= (Character)limit && (Character)value < (Character)limit)
						|| ((Character)this.getLastVal() <= (Character)limit && (Character)value > (Character)limit)){
					trigged = true;
				}
			}else if(value instanceof Short){
				if(((Short)this.getLastVal() >= (Short)limit && (Short)value < (Short)limit)
						|| ((Short)this.getLastVal() <= (Short)limit && (Short)value > (Short)limit)){
					trigged = true;
				}
			}else if(value instanceof Integer){
				if(((Integer)this.getLastVal() >= (Integer)limit && (Integer)value < (Integer)limit)
						|| ((Integer)this.getLastVal() <= (Integer)limit && (Integer)value > (Integer)limit)){
					trigged = true;
				}
			}else if(value instanceof Long){
				if(((Long)this.getLastVal() >= (Long)limit && (Long)value < (Long)limit)
						|| ((Long)this.getLastVal() <= (Long)limit && (Long)value > (Long)limit)){
					trigged = true;
				}
			}else if(value instanceof Float){
				if(((Float)this.getLastVal() >= (Float)limit && (Float)value < (Float)limit)
						|| ((Float)this.getLastVal() <= (Float)limit && (Float)value > (Float)limit)){
					trigged = true;
				}
			}else if(value instanceof Double){
				if(((Double)this.getLastVal() >= (Double)limit && (Double)value < (Double)limit)
						|| ((Double)this.getLastVal() <= (Double)limit && (Double)value > (Double)limit)){
					trigged = true;
				}
			}
		}
		this.setLastVal(value);
		if(trigged)
			setLastTriggTime(System.currentTimeMillis());
		return trigged;
	}	

	protected boolean evalInScope(Object value) throws TriggerEvalException{
		if(topScope==null||bottomScope==null)throw new TriggerEvalException("Scope variables not initialized.");
		if(value==null)throw new TriggerEvalException("Value to evaluate is not initialized.");
		
		boolean trigged = false;
		
		if(!(value instanceof Byte || value instanceof Character || value instanceof Double || value instanceof Float
				|| value instanceof Integer || value instanceof Long || value instanceof Short))
			throw new TriggerEvalException("Wrong data type used.");
			
		if(System.currentTimeMillis() - getLastTriggTime() < getTriggerDelay())
			return false;
		
		if(value instanceof Byte){
			if(((Byte)value)>=((Byte)bottomScope)){
				if(((Byte)value)<=((Byte)topScope)){
					trigged = true;
				}
			}
		}else if(value instanceof Character){
			if(((Character)value)>=((Character)bottomScope)){
				if(((Character)value)<=((Character)topScope)){
					trigged = true;
				}
			}
		}else if(value instanceof Short){
			if(((Short)value)>=((Short)bottomScope)){
				if(((Short)value)<=((Short)topScope)){
					trigged = true;
				}
			}
		}else if(value instanceof Integer){
			if(((Integer)value)>=((Integer)bottomScope)){
				if(((Integer)value)<=((Integer)topScope)){
					trigged = true;
				}
			}
		}else if(value instanceof Long){
			if(((Long)value)>=((Long)bottomScope)){
				if(((Long)value)<=((Long)topScope)){
					trigged = true;
				}
			}
		}else if(value instanceof Float){
			if(((Float)value)>=((Float)bottomScope)){
				if(((Float)value)<=((Float)topScope)){
					trigged = true;
				}
			}
		}else if(value instanceof Double){
			if(((Double)value)>=((Double)bottomScope)){
				if(((Double)value)<=((Double)topScope)){
					trigged = true;
				}
			}
		}
		if(trigged)
			setLastTriggTime(System.currentTimeMillis());
		return trigged;
	}	

	protected boolean evalOutOfScope(Object value)throws TriggerEvalException{
		if(topScope==null||bottomScope==null)throw new TriggerEvalException("Scope variables not initialized.");
		if(value==null)throw new TriggerEvalException("Value to evaluate is not initialized.");
		
		boolean trigged = false;
		
		if(!(value instanceof Byte || value instanceof Character || value instanceof Double || value instanceof Float
				|| value instanceof Integer || value instanceof Long || value instanceof Short))
			throw new TriggerEvalException("Wrong data type used.");
			
		if(System.currentTimeMillis() - getLastTriggTime() < getTriggerDelay())
			return false;
		
		if(value instanceof Byte){
			if(((Byte)value)<((Byte)bottomScope) || ((Byte)value)>((Byte)topScope)){
				trigged = true;
			}
		}else if(value instanceof Character){
			if(((Character)value)<((Character)bottomScope) || ((Character)value)>((Character)topScope)){
				trigged = true;
			}
		}else if(value instanceof Short){
			if(((Short)value)<((Short)bottomScope) || ((Short)value)>((Short)topScope)){
				trigged = true;
			}
		}else if(value instanceof Integer){
			if(((Integer)value)<((Integer)bottomScope) || ((Integer)value)>((Integer)topScope)){
				trigged = true;
			}
		}else if(value instanceof Long){
			if(((Long)value)<((Long)bottomScope) || ((Long)value)>((Long)topScope)){
				trigged = true;
			}
		}else if(value instanceof Float){
			if(((Float)value)<((Float)bottomScope) || ((Float)value)>((Float)topScope)){
				trigged = true;
			}
		}else if(value instanceof Double){
			if(((Double)value)<((Double)bottomScope) || ((Double)value)>((Double)topScope)){
				trigged = true;
			}
		}
		if(trigged)
			setLastTriggTime(System.currentTimeMillis());
		return trigged;
	}
	
	@Override
	public TriggerProxy proxify() {
		TriggerProxy proxy = super.proxify();
		proxy.setProperty("bottomScope", this.bottomScope);
		proxy.setProperty("topScope",this.topScope);
		proxy.setProperty("limit", this.limit);
		return proxy;
	}

	@Override
	public void update(TriggerProxy proxy) {
		super.update(proxy);
		this.bottomScope = proxy.getProperty("bottomScope");
		this.topScope = proxy.getProperty("topScope");
		this.limit = proxy.getProperty("limit");
	}
	
	@Override
	public String getDescription(){
		if(getTriggerOption() == TriggerOptions.onOverLimit){
			return "over limit " +limit;
		}else if(getTriggerOption() == TriggerOptions.onUnderLimit){
			return "under limit " +limit;
		}else if(getTriggerOption() == TriggerOptions.onPassingLimit){
			return "passing limit " +limit;
		}else if(getTriggerOption() == TriggerOptions.onInScope){
			return "in scope [" +bottomScope +"," +topScope+"]";
		}else if(getTriggerOption() == TriggerOptions.onOutOfScope){
			return "out of scope [" +bottomScope +"," +topScope+"]";
		}
		return super.getDescription();
	}
}
