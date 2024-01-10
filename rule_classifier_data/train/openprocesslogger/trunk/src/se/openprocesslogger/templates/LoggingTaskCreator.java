package se.openprocesslogger.templates;

import java.util.HashMap;
import java.util.Map.Entry;

import se.openprocesslogger.log.LoggingTask;
import se.openprocesslogger.log.Subscription;

public class LoggingTaskCreator {
	private LoggingTask task;
	
	public LoggingTaskCreator(LoggingTask task){
		this.task = task;
	}
	
	public void setSubscriptions(HashMap<String, Subscription> subscriptions){
		for(Entry<String, Subscription> e : subscriptions.entrySet()){
			task.addSubscription(e.getValue());
		}
	}

	public LoggingTask getLoggingTask() {
		return this.task;
	}
}
