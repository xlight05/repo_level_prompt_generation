package se.openprocesslogger.proxy;

import java.util.Date;
import java.util.HashMap;

import se.opendataexchange.common.IAddressValueChanged;
import se.opendataexchange.common.InvalidAddressException;
import se.opendataexchange.controller.OpenDataExchangeController;
import se.openprocesslogger.OplController;

public class Diagnostics implements IAddressValueChanged {
	private HashMap<String, Object> values;
	private boolean started = false;
	private static Diagnostics diag;
	
	private Diagnostics(){
		values = new HashMap<String, Object>();
	}

	public static Diagnostics instance(){
		if(diag == null)
			diag = new Diagnostics();
		return diag;
	}
	
	public void startPolling(){
		started = true;
		OpenDataExchangeController control = OplController.getController().getOdeController();
		values = control.getAllValues();
		for(String val : control.getAddressValues()){
			try {
				control.subscribeForAddressValue(val, this);
			} catch (InvalidAddressException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void stopPolling(){
		started = false;
		OpenDataExchangeController control = OplController.getController().getOdeController();
		for(String val : control.getAddressValues()){
			try {
				control.unsubscribeForAddressValue(val, this);
			} catch (InvalidAddressException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Object getValue(String addressName){
		return values.get(addressName);
	}
	
	public HashMap<String, Object> getAllValues(){
		return values;
	}

	@Override
	public void valueHasChanged(String name, Object value, Date timestamp) {
		if(started){
			if(value != null)
				values.put(name, value);
		}
	}
}
