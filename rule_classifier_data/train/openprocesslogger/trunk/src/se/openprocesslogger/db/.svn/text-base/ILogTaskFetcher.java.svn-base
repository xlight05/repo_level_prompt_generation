package se.openprocesslogger.db;

import java.util.List;

import se.openprocesslogger.log.LoggingTask;
import se.openprocesslogger.proxy.LoggingTaskData;

public interface ILogTaskFetcher {
	/***
	 * 
	 * @param name Log task name
	 * @return Log task id. -1 if it does not exist.
	 */
	//long getLogTaskId(String name);
	
	long logTaskExists(String name);
	public String findLogTaskName(long loggingTask);
	LoggingTaskData findLogTask(long id);
	public LoggingTask findLogTask(String name);
	public LoggingTask getLogTask(long id);
	public List<LoggingTaskData> getFinishedLogTasks();

}
