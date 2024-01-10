package se.openprocesslogger.log;

import se.openprocesslogger.proxy.TriggerProxy;


public class TriggerBuilder {
	public enum Type{
		OTHER,
		ANALOG,
		DIGITAL
		
	}
	
	public static ITriggerEval getTrigger(TriggerProxy proxy){
		Type type = Type.valueOf(proxy.getProperty("type").toString());
		ITriggerEval eval = null;
		TriggerOptions option = TriggerOptions.valueOf(proxy.getTrigOption()); 
		switch(type){
		case ANALOG:
			eval = new AnalogTriggerEval(option);
			break;
		case DIGITAL:
			eval = new DigitalTriggerEval(option);
			break;
		default:
			eval = new TriggerEval(option);
			break;
		}
		eval.update(proxy);
		return eval;
	}
	
	public static void setTriggerProperty(TriggerProxy proxy, ITriggerEval eval){
		if(eval instanceof AnalogTriggerEval){
			proxy.setProperty("type", Type.ANALOG.toString());
		}else if(eval instanceof DigitalTriggerEval){
			proxy.setProperty("type", Type.DIGITAL.toString());
		}else{
			proxy.setProperty("type", Type.OTHER.toString());
		}
	}
}
