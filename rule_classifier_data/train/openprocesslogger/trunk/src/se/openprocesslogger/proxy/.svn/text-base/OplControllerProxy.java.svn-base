package se.openprocesslogger.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import se.opendataexchange.common.ErrorInfo;
import se.openprocesslogger.Data;
import se.openprocesslogger.OplController;
import se.openprocesslogger.OplProperties;
import se.openprocesslogger.db.DataFetcher;
import se.openprocesslogger.db.LogTaskFetcher;
import se.openprocesslogger.db.LogTaskSaver;
import se.openprocesslogger.db.OPLDatabase;
import se.openprocesslogger.log.LoggingTask;
import se.openprocesslogger.log.Subscription;
import se.openprocesslogger.log.TrigEvent;
import se.openprocesslogger.proxy.dojo.TreeBuilderUtil;
import se.openprocesslogger.proxy.dojo.TreeHolder;
import se.openprocesslogger.templates.OplTemplateCreator;
import se.openprocesslogger.utils.WebLogger;

public class OplControllerProxy implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1714078261315120995L;
	
	public OplControllerProxy(String configfile, boolean isAbsolutePath){
		OplProperties.setConfigFileName(configfile, isAbsolutePath);
		OPLDatabase.instance(); // Force loading of database
		OplTemplateCreator.init(); // Force loading of templates
	}
	
	public void addLoggingTask(LogTaskProxy task){
		WebLogger.debug("Saving logging task "+task.getName() +" id: "+task.getId());
		LoggingTask newTask = LoggingTask.getfromProxy(task);
		OplController.getController().appendLoggingTask(newTask);
		WebLogger.debug("Done.");
	}
		
	public void updateLoggingTask(LogTaskProxy taskProxy){
		WebLogger.debug("Updating logging task "+taskProxy.getName() +" id: "+taskProxy.getId());
		LoggingTask task = OplController.getController().getLogTask(taskProxy.getId());
		//LoggingTask task = OplController.getController().getLogTask(taskProxy.getName());
		if(task == null ){
			WebLogger.warn("Tried to update a removed task: "+taskProxy.getName() +" id: "+taskProxy.getId());
			// Task already removed. Do nothing.
			return;
		}
		task.updateWithProxy(taskProxy);
		LogTaskSaver.instance().addLogTask(task);
	}
	
	public void startLoggingTask(String name){
		OplController.getController().getLogTask(name).startLoggingTask();
	}
	public void stopLoggingTask(String name){
		WebLogger.debug("Stopping log task");
		OplController.getController().stopLogTask(name);
	}
	
	/**
	 * 
	 * @return 0=does not exist, 1=exists (may overwrite), 2=exists in database, 3=is started
	 */
	public int logTaskExists(String name, long id){
		WebLogger.debug("Checking logtaskexists");
		for(LoggingTask task : OplController.getController().getLstTasks()){
			if(task.getTaskId() == id && task.getName().equals(name)){
				return 1;
			}else if(task.getTaskId() == id){ // Old id, new name 
				return 4;
			}else if(name.equals(task.getName())){ // Same name, other id
				return 2;
			}
		}
		if(LogTaskFetcher.instance().logTaskExists(name) > 0){
			return 2;
		}
		return 0;
	}
	
	public int taskHasData(long taskId) {
		LoggingTask task = OplController.getController().getLogTask(taskId);
		if(task.isStarted()) return 1; // Won't analyze running task 
		long now = System.currentTimeMillis();
		if(DataFetcher.instance().hasData(taskId)){
			WebLogger.debug("Has data execution time: "+(System.currentTimeMillis()-now)+" ms");
			return 2;
		}
		return 3;
	}
	
	public LogTaskProxy getLogTask(String name){
		LoggingTask task  = OplController.getController().getLogTask(name);
		if(task!=null){
			LogTaskProxy proxy = task.proxify();
			return proxy;
		}else{
			return null;
		}
	}
	
	public LogTaskProxy[] getTaskTemplates(){
		return OplTemplateCreator.getLoggingTaskTemplates();
	}

	public SubscriptionProxy getSubscription(String subId, long taskId){
		//System.out.println("get subs for task");
		Subscription sub = OplController.getController().getSubscription(subId, taskId);
		return sub.proxify();
	}
	
	public void updateSubscription(SubscriptionProxy proxy){
		Subscription sub = OplController.getController().getSubscription(proxy.getValueAddress(), proxy.getLogTaskId());
		if(sub!=null){ // Null if task is non-updatable
			WebLogger.debug("\t updating subscription "+proxy.getValueAddress());
			WebLogger.debug("\t\t "+proxy.getDescription());
			sub.update(proxy);
		}else{
			WebLogger.error("Fatal: Could not find subscription");
		}
	}
	
	public TrigEvent[] getTriggers(){
		WebLogger.debug("getTriggers()");
		TrigEvent[] temp = OplController.getController().getTriggers();
		WebLogger.debug("Got triggers: "+temp.length);
		return temp;
	}
	
	public TrigEvent[] getTriggers(String[] varNames){
		WebLogger.debug("getTriggers(varNames)");
		TrigEvent[] temp = OplController.getController().getTriggers(varNames);
		WebLogger.debug("Got triggers: "+temp.length);
		return temp;
	}
	
	public boolean updateAddressValue(String strAddressValuePath, Object value){
		boolean blnResult = false;
		try{
			blnResult = OplController.getController().updateAddressValue(strAddressValuePath, value);
			WebLogger.debug("updateAddressValue("+strAddressValuePath+", Object: "+value.toString()+") = "+blnResult);	
		}catch(Exception ex){
			WebLogger.debug("Exception: updateAddressValue("+strAddressValuePath+", Object: "+value.toString()+") = "+ex.toString());
		}
			
		return blnResult;
		
	}
		
	public List<LoggingTaskData> getActiveLogTasks(){
		ArrayList<LoggingTask> tasksLst = OplController.getController().getLstTasks();
		ArrayList<LoggingTaskData> data = new ArrayList<LoggingTaskData>();
		for(LoggingTask task : tasksLst){
			LoggingTaskData taskData = new LoggingTaskData(task.getTaskId(),task.getName(), task.getVarNames());
			taskData.setStarted(task.isStarted());
			data.add(taskData);
		}
		return data;
	}
	
	public LoggingTaskData[] getLatestTasks(){
		WebLogger.debug("OPLControllerProxy: Getting finished log tasks");
		LoggingTaskData[] data = LogTaskFetcher.instance().getFinishedLogTasks().toArray(new LoggingTaskData[0]);
		WebLogger.debug("Got "+data.length +" tasks");
		return data;
	}

	public TreeHolder buildProxyTree(LogTaskProxy proxy){
		String[] varNames = new String[proxy.getSubscriptions().size()];
		int index = 0;
		for(String s : proxy.getSubscriptions().keySet()){
			varNames[index++] = s;
		}
		Arrays.sort(varNames);
		return TreeBuilderUtil.getTree(varNames);
	}
	
	public TreeHolder buildTree(LoggingTaskData info){
		String[] s = info.getVarNames();
		Arrays.sort(s);
		return TreeBuilderUtil.getTree(s);
	}
		
	public void deleteTask(long taskId){
		OplController.getController().deleteTask(taskId);
	}
	
	public ErrorInfo getError(){
		return OplController.getController().getError();
	}
	
	public String[] getAddressValues(){
		return OplController.getController().getAddressValues();
	}
	
	/***
	 * Returns the default chart types for the input variable names, depending on datatype in database
	 * @param varNames
	 * @return
	 */
	public String[] getDefaultChartTypes(String[] varNames, long logTaskId){
		String[] chartTypes = new String[varNames.length];
		OPLDatabase db = OPLDatabase.instance();
		for (int i=0; i<varNames.length; i++){
			Data d = db.getSampleData(varNames[i], logTaskId);
			if (d==null || d.getValue() == null){
				chartTypes[i] = "se.openprocesslogger.svg.taglib.AnalogChartShell";
			}else if (d.getValue() instanceof Boolean){
				chartTypes[i] = "se.openprocesslogger.svg.taglib.DigitalChartShell";
			}else if(
					d.getValue() instanceof Float || 
					d.getValue() instanceof Double ||
					d.getValue() instanceof Integer ||
					d.getValue() instanceof Long ||
					d.getValue() instanceof Short ||
					d.getValue() instanceof Byte ||
					d.getValue() instanceof Character
					){
				chartTypes[i] = "se.openprocesslogger.svg.taglib.AnalogChartShell";
			}else{
				chartTypes[i] = "se.openprocesslogger.svg.taglib.EventChartShell";
			}
			
		}
		return chartTypes;
	}
}
