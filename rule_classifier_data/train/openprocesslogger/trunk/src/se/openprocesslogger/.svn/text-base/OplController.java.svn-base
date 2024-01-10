package se.openprocesslogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import se.opendataexchange.common.AddressValue;
import se.opendataexchange.common.ErrorInfo;
import se.opendataexchange.common.IAddressValueChanged;
import se.opendataexchange.controller.OpenDataExchangeController;
import se.openprocesslogger.db.EventFetcher;
import se.openprocesslogger.db.LogTaskFetcher;
import se.openprocesslogger.db.LogTaskSaver;
import se.openprocesslogger.db.OPLDatabase;
import se.openprocesslogger.log.LoggingTask;
import se.openprocesslogger.log.Subscription;
import se.openprocesslogger.log.TrigEvent;

public class OplController implements IAddressValueChanged{
	
	private static Logger log = Logger.getLogger(OplController.class);
	
	/*
	 * Static method to get access to the OplController singleton.
	 */
	public static OplController getController(){
		if(controller == null) controller = new OplController();
		return controller;
	}
	/*
	 * Static member holding the OplController singleton.
	 */
	private static OplController controller=null;
	private static OpenDataExchangeController odeController = null;
	
	private HashMap<Long, LoggingTask> lstTasks;
	private Timer executionTimer = new Timer("OplTriggeringTimer");
	private ErrorInfo error = null;
	
	private OplController(){
		lstTasks = new HashMap<Long, LoggingTask>();
		if(odeController == null) odeController = OpenDataExchangeController.getController(OplProperties.getOdeName());
		if(odeController == null) log.error("Opl controller is null");
		AddressValue errVal = OpenDataExchangeController.getErrorValue();
		if(errVal != null) errVal.subscribe(this);
	}
	
	public OpenDataExchangeController getOdeController(){
		return odeController;
	}
	
	public TrigEvent[] getTriggers(){
		Event[] events = EventFetcher.instance().getLatestEvents();
		if (events == null){
			return null;
		}
		else{
			TrigEvent[] trigEvents = new TrigEvent[events.length];			
			for(int i=0 ; i<trigEvents.length ; i++){
				trigEvents[i] = (TrigEvent)events[i];
			}
			return trigEvents;
		}		
	}
	
	public TrigEvent[] getTriggers(String[] varNames){
		Event[] events = EventFetcher.instance().getLatestEvents(varNames);
		if (events == null){
			return null;
		}
		else{
			TrigEvent[] trigEvents = new TrigEvent[events.length];			
			for(int i=0 ; i<trigEvents.length ; i++){
				trigEvents[i] = (TrigEvent)events[i];
			}
			return trigEvents;
		}		
	}
	
	public TrigEvent[] getTriggers(long lStartDate, long lEndDate){
		Event[] events = EventFetcher.instance().getEvents(lStartDate, lEndDate);
		if (events == null){
			return null;
		}
		else{
			TrigEvent[] trigEvents = new TrigEvent[events.length];			
			for(int i=0 ; i<trigEvents.length ; i++){
				trigEvents[i] = (TrigEvent)events[i];
			}
			return trigEvents;
		}		
	}
	
	public boolean updateAddressValue(String strAddressValuePath, Object objValue){
		boolean blnResult = false;
		try{
			blnResult = odeController.updateAddressValue(strAddressValuePath, objValue);
			log.debug("updateAddressValue("+strAddressValuePath+", Object: "+objValue.toString()+") = "+blnResult);	
		}catch(Exception ex){
			log.debug("Exception: updateAddressValue("+strAddressValuePath+", Object: "+objValue.toString()+") = "+ex.toString());
		}
			
		return blnResult;
	}
	
	//public ArrayList<LoggingTask> getLstTasks() {
	public ArrayList<LoggingTask> getLstTasks() {
		ArrayList<LoggingTask> tasks = new ArrayList<LoggingTask>();
		Iterator<Entry<Long, LoggingTask>> taskEntries = lstTasks.entrySet().iterator();
		while(taskEntries.hasNext()){
			Entry<Long,LoggingTask> e = taskEntries.next();
			if(!e.getValue().isRunning()){ // Has stopped, should have stopped
				LogTaskSaver.instance().addLogTask(e.getValue());
				taskEntries.remove();
			}else{
				tasks.add(e.getValue());
			}
		}
		return tasks;
	}
	
	public Timer getExecutionTimer() {
		return executionTimer;
	}
	
	public void appendLoggingTask(LoggingTask task){
		LogTaskSaver.instance().addLogTask(task);
		//lstTasks.add(task);
		lstTasks.put(task.getTaskId(), task);
	}
	
	public Subscription getSubscription(String subId, long taskId) {
		LoggingTask task = lstTasks.get(taskId);
		return task.getSubscription(subId);
	}

	public LoggingTask getLogTask(String name) {
		for(LoggingTask l : this.lstTasks.values()){
			if(l.getName().equals(name)) return l;
		}
		return LogTaskFetcher.instance().findLogTask(name);
	}
	public LoggingTask getLogTask(long id) {
		LoggingTask task = lstTasks.get(id);
		if(task==null){
			task = LogTaskFetcher.instance().getLogTask(id);
		}
		return task;
	}

	public void deleteTask(long taskId) {
		LoggingTask task = getLogTask(taskId);
		if(task!=null){
			if(!LogTaskSaver.instance().deleteLogTask(task)){
				log.warn("Could not delete task "+taskId+" called "+task.getName());
			}
			lstTasks.remove(task);
		}else{
			log.error("Could not find log task: "+taskId);
		}
	}

	public void stopLogTask(String name) {
		LoggingTask task = getLogTask(name);
		log.debug("Got log task");
		task.doKill();
		OPLDatabase.instance().addLogTask(task);
		log.debug("Called stop");
		lstTasks.remove(task.getTaskId());
		log.debug("Task removed");
	}

	public ErrorInfo getError(){
		return error;
	}
	
	@Override
	public void valueHasChanged(String name, Object value, Date timestamp){
		if(value instanceof ErrorInfo){
			error = (ErrorInfo) value;
		}
	}

	public String[] getAddressValues() {
		return getOdeController().getAddressValues();
	}

}
