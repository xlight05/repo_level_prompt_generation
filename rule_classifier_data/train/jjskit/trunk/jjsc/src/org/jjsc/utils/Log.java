package org.jjsc.utils;

import java.io.PrintStream;
/**
 * Common logger implementation. Provides logging abstraction for JJS subsystems.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public final class Log {
	private static Log log;
	private static LogLevel defaultLevel = LogLevel.INFO;
	
	private static Log get(){
		if(log==null){
			log = new Log(defaultLevel);
		}
		return log;
	}
	/**
	 * Defines allowed levels for logging subsystem.
	 * @author alex.bereznevatiy@gmail.com
	 */
	public static enum LogLevel {
		
		DEBUG(0, "\t\t"), 
		
		INFO(5, "\t"),
		
		WARNING(10, "\tWARNING! "),
		
		ERROR(15,"ERROR!"),
		
		NONE(Integer.MAX_VALUE,"");
		
		private int value;
		private String prefix;
		
		private LogLevel(int  value, String prefix){
			this. value =  value;
			this.prefix = prefix;
		}
	}
	/**
	 * Prints debug message to logger. Any message is allowed but <code>null</code> will be ignored.
	 * @param message
	 */
	public static void debug(Object message){
		get().log(LogLevel.DEBUG, message, null);
	}
	/**
	 * Prints info message to logger. Any message is allowed but <code>null</code> will be ignored.
	 * @param message
	 */
	public static void info(Object message) {
		get().log(LogLevel.INFO, message, null);
	}
	/**
	 * Prints warning message to logger. Any message is allowed but <code>null</code> will be ignored.
	 * @param message
	 */
	public static void warn(Object message) {
		get().log(LogLevel.WARNING, message, null);
	}
	/**
	 * Prints error message to logger. Any message is allowed but <code>null</code> will be ignored.
	 * @param message
	 */
	public static void error(Object message) {
		get().log(LogLevel.ERROR, message, null);
	}
	/**
	 * Prints warning message and excepting traces to logger. Any message and exception are allowed
	 * but <code>null</code>s will be ignored.
	 * @param message
	 * @param exception
	 */
	public static void error(Object message, Throwable ex) {
		get().log(LogLevel.ERROR, message, ex);
	}
	/**
	 * Prints exception to logger. Any exception is allowed but <code>null</code> will be ignored.
	 * @param ex
	 */
	public static void error(Throwable ex) {
		get().log(LogLevel.ERROR, null, ex);
	}
	/**
	 * Sets log level default value to parameter passed.
	 * @param level to set
	 */
	public static void setDefaultLevel(LogLevel level){
		AssertUtils.assertNotNull(level, "Level is NULL");
		if(defaultLevel.equals(level)){
			return;
		}
		defaultLevel = level;
		if(log!=null){
			log = new Log(defaultLevel);
		}
	}
	/**
	 * Sets log level default value to parameter passed.
	 * If parameter cannot be unambiguously cast to {@link LogLevel}
	 * then log level will not be changed and warning message will be logged.
	 * @param level to set
	 */
	public static void setDefaultLevel(String level) {
		if (level == null) {
			warn("Trying to set log level to NULL - skipping");
			return;
		}
		try {
			setDefaultLevel(LogLevel.valueOf(level.toUpperCase()));
		} catch (IllegalArgumentException ex) {
			warn("Trying to set log level to unknown level ("+level+") - skipping");
		}
	}
	/**
	 * Current log level.
	 * @return
	 */
	public static LogLevel getDefaultLevel(){
		return defaultLevel;
	}
	/**
	 * @return <code>true</code> if current log level is {@link LogLevel#DEBUG}.
	 */
	public static boolean isDebug() {
		return defaultLevel.value <= LogLevel.DEBUG.value;
	}
	
	private PrintStream out = System.out;
	private LogLevel level;
	
	private Log(LogLevel level){
		this.level = level;
	}
	
	private void log(LogLevel msgLevel, Object message, Throwable th){
		synchronized(out){
			if(level.value>msgLevel.value){
				return;
			}
			printMessage(msgLevel, message, th);
			printThrowable(th, null);
		}
	}
	
	private void printMessage(LogLevel msgLevel, Object message, Throwable th) {
		StringBuilder result = new StringBuilder();
		result.append(msgLevel.prefix);
		if(message!=null){
			result.append(message).append(' ');
		}
		if(th!=null){
			result.append(th.toString());
		}
		out.println(result);
	}
	
	private void printThrowable(Throwable th, StackTraceElement[] causedTrace) {
		if(th==null)return;
        StackTraceElement[] trace = th.getStackTrace();
        if (causedTrace == null) {
        	causedTrace = new StackTraceElement[0];
        } else {
        	out.println("Caused by: " + th);
        }
        int m = trace.length-1;
        int n = causedTrace==null?0:causedTrace.length-1;
        while (m >= 0 && n >=0 && trace[m].equals(causedTrace[n])) {
            m--; n--;
        }
        int framesInCommon = trace.length - 1 - m;
        
        for (int i=0; i <= m; i++) {
        	out.println("\tat " + trace[i]);
        }
        if (framesInCommon != 0) {
            out.println("\t... " + framesInCommon + " more");
        }
        
        printThrowable(th.getCause(), trace);
	}
}
