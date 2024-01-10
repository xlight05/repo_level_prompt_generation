package se.openprocesslogger.log;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import se.openprocesslogger.db.OPLDatabase;
import se.openprocesslogger.proxy.LogTaskProxy;
import se.openprocesslogger.proxy.SubscriptionProxy;


public class LoggingTask implements Runnable{
	
	private static Logger log = Logger.getLogger(LoggingTask.class);
	
	private long taskId;
	private String name;
	private int started=0; // 0=before start, 1=running, 2=stopped
	private boolean running;
	private Thread runThread;
	private long startTime;
	private long stopTime;
	
	private HashMap<String,String> properties = new HashMap<String, String>();
	private HashMap<String,Subscription> subscriptions = new HashMap<String, Subscription>();
		
	public LoggingTask(String name, HashMap<String, String> properties) {
		super();
		this.name = name;
		this.properties = properties;
	}
	
	public LoggingTask(long taskId, String name,
			HashMap<String, String> properties) {
		super();
		this.taskId = taskId;
		this.name = name;
		this.properties = properties;
	}
	
	public void setStartTime(long time){
		startTime = time;
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	public void setStopTime(long time){
		stopTime = time;
	}
	
	public long getStopTime(){
		return stopTime;
	}
	
	public long getTaskId() {
		return taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
		for(Subscription s : subscriptions.values()){
			s.setLogTaskId(taskId);
		}
		if(runThread==null){
			runThread = new Thread(this, "Logging task "+name);
			runThread.start();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addProperty(String name,String value){
		synchronized (properties) {
			properties.put(name,value);
		}
	}
	
	public String getProperty(String name){
		if(!properties.containsKey(name)) return null;
		synchronized (properties) {
			return properties.get(name);
		}
	}
	
	public HashMap<String,String> getProperties(){
		return properties;
	}
	
	public void addSubscription(Subscription sub){
		synchronized (subscriptions) {
			subscriptions.put(sub.getValueAddress(), sub);
			sub.setLogTaskId(this.taskId);
		}
	}
	
	public void removeSubscription(Subscription sub){
		synchronized (subscriptions) {
			subscriptions.remove(sub);
			sub.setLogTaskId(-1);
		}
	}
	
	public Subscription getSubscription(String subId){
		return subscriptions.get(subId);
	}
	
	/***
	 * 
	 * @return If this task is currently logging
	 */
	public boolean isStarted(){
		return started==1;
	}
		
	public String[] getVarNames(){
		String[] s = new String[subscriptions.size()];
		int index = 0;
		for(String str : subscriptions.keySet()){
			s[index++] = str;
		}
		return s;
	}
	
	public void startLoggingTask(){
		if(this.taskId <= 0){
			log.error("Cannot start unsaved task");
			return;
		}
		startTime = System.currentTimeMillis();
		OPLDatabase.instance().addLogTask(this);
		synchronized (subscriptions) {
			for(Subscription sub:subscriptions.values()){
				sub.setLogTaskId(this.taskId);
				sub.startSubscription();
			}
		}
		started=1;
	}
	
	private void stopLoggingTask(){
		synchronized (subscriptions) {
			for(Subscription sub:subscriptions.values()){
				log.debug("\tStopping sub "+sub.getValueAddress());
				sub.stopSubscription();
				log.debug("\tSub stopped");
			}
		}
		stopTime = System.currentTimeMillis();
		started = 3;
	}
	
	public void doKill(){
		if (running && this.runThread != null) {		
			if(started<=1){
				log.debug(name+" stopping on command");
				stopLoggingTask();
			}
			running = false;
		} else {
			log.debug("doKill(): Tried to stop a not running task");
		}
	}
	
	/***
	 *  If startTime and stopTime are set, start and stop on appropriate times.
	 *  update every second.
	 */
	public void run(){
		running = true;
		log.debug("Log task ready to start "+this.getName());
		while(running){
			if(started == 0){
				if(startTime!=0){
					if(startTime<System.currentTimeMillis()){
						log.debug(this.getName()+" started logging");
						this.startLoggingTask();
					}
				}
			}
			if(stopTime!=0 && started==1){
				if(stopTime<System.currentTimeMillis()){
					log.debug(this.getName()+" stopping on timer");
					stopLoggingTask();
					running = false;
					started = 3;					
				}				
			}
			try{Thread.sleep(1000);}catch(InterruptedException e){
				log.warn("Log task thread interrupted");
				running=false; // Stop running if interrupted for some reason. Should not happen.
			}
		}
		OPLDatabase.instance().flush();
		OPLDatabase.instance().addLogTask(this);
		log.debug("Log task finished "+this.getName());
	}
	
	public String getTemplateName(){
		return properties.get("templateName");
	}
	public void setTemplateName(String name){
		addProperty("templateName", name);
	}
	
	public HashMap<String,Subscription> getSubscriptions(){
		return subscriptions;
	}
	
	public LogTaskProxy proxify(){
		LogTaskProxy proxy = new LogTaskProxy();
		proxy.setName(this.name);
		String info = properties.get("info");
		info = info==null ? "" : info;
		
		HashMap<String, SubscriptionProxy> subProxies = new HashMap<String, SubscriptionProxy>();
		for(Subscription s : subscriptions.values()){
			subProxies.put(s.getValueAddress(), s.proxify());
		}
		proxy.setSubscriptions(subProxies);
		
		proxy.setProperties(properties);
		proxy.setStarted(isStarted());
		proxy.setId(this.taskId);
		proxy.setStartTime(startTime);
		proxy.setStopTime(stopTime);
		return proxy;
	}
	
	public static LoggingTask getfromProxy(LogTaskProxy task){
		LoggingTask newTask = new LoggingTask(task.getName(),new HashMap<String, String>());
		newTask.updateWithProxy(task);
		return newTask;
	}
	
	public void updateWithProxy(LogTaskProxy task){
		setName(task.getName());
		setStartTime(task.getStartTime());
		setStopTime(task.getStopTime());
		for(Entry<String, String> p : task.getProperties().entrySet()){
			this.addProperty(p.getKey(), p.getValue());
		}
		for(SubscriptionProxy proxy : task.getSubscriptions().values()){
			Subscription sub = getSubscription(proxy.getValueAddress());
			if(sub==null){
				this.addSubscription(Subscription.getFromProxy(proxy));
			}else{
				sub.update(proxy);
			}
		}
	}
	
	/***
	 * 
	 * @return If the logging task thread is running
	 */
	public boolean isRunning() {
		return running;
	}
	
}
