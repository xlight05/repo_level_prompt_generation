package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Class provides static methods to change level from java.util.logging to
 * log4j level. Static maps are used for faster search data. Some of 
 * java.util.logging levels are mapped to the same log4j level to keep
 * similar log level names.
 * @author Tomasz Jadczyk
 */
public class ServerLogsLevelProvider {
    private static String[] logLevels = {"ALL", "TRACE", "DEBUG",
        "INFO", "WARN", "ERROR", "FATAL", "OFF"
    };
    private static java.util.logging.Level[] utilLoggingLevels = {
        java.util.logging.Level.ALL,
        //java.util.logging.Level.FINEST, this level is omitted for equal levels number
        java.util.logging.Level.FINE,
        java.util.logging.Level.CONFIG,
        java.util.logging.Level.INFO,
        java.util.logging.Level.WARNING,
        java.util.logging.Level.SEVERE, 
        java.util.logging.Level.SEVERE, //same to get correct levels
        java.util.logging.Level.OFF
    };
    
    private static Map<String, java.util.logging.Level> nameToUtilLevel;
    private static Map<java.util.logging.Level, String> utilLevelToName;
    private static Map<String, org.apache.log4j.Level> nameToLog4jLevel;
    
    static {
        nameToUtilLevel = new HashMap<String, java.util.logging.Level>();
        nameToLog4jLevel = new HashMap<String, org.apache.log4j.Level>();
        utilLevelToName = new HashMap<java.util.logging.Level, String>();
        for(int i = 0; i < 8; i++) {
            nameToUtilLevel.put(logLevels[i], utilLoggingLevels[i]);
            utilLevelToName.put(utilLoggingLevels[i], logLevels[i]);
            nameToLog4jLevel.put(logLevels[i], org.apache.log4j.Level.toLevel(i));
        }
        utilLevelToName.put(java.util.logging.Level.SEVERE, "ERROR");
        utilLevelToName.put(java.util.logging.Level.FINER, "TRACE");
        utilLevelToName.put(java.util.logging.Level.FINEST, "TRACE");
    }
    

    /**
     * Get available logging levels.
     * @return
     */
    public static String[] getLogLevels() {
        return logLevels;
    }

    /**
     * Map String to log4j level
     * @param levelName
     * @return
     */
    public static org.apache.log4j.Level getLog4jLevel(String levelName) {
        return nameToLog4jLevel.get(levelName);
    }

    /**
     * Map String to java.util.logging level
     * @param levelName
     * @return
     */
    public static java.util.logging.Level getUtilLoggingLevel(String levelName) {
        return nameToUtilLevel.get(levelName);
    }

    /**
     * Checks currently set logging level and returns true if level passed in argument
     * should be logged
     * @param currentLevel
     * @param level
     * @return
     */
    public static boolean isActiveLevel(String currentLevel, java.util.logging.Level level) {
        
        if(currentLevel == null || level == null)
            return false;

        final java.util.logging.Level crLevel = nameToUtilLevel.get(currentLevel);
        
        return crLevel.intValue() <= level.intValue();
    }

    /**
     * Checks currently set logging level and returns true if level passed in argument
     * should be logged
     * @param currentLevel
     * @param level
     * @return
     */
    public static boolean isActiveLevel(String currentLevel, org.apache.log4j.Level level) {
        
        if(currentLevel == null || level == null) {
            return false;
        }
        
        org.apache.log4j.Level crLevel = org.apache.log4j.Level.toLevel(currentLevel);
        return level.toInt() >= crLevel.toInt();
    }
    
    /**
     * Map java.util.logging.Level to log4j level name
     * @param level
     * @return
     */
    public static String getLoggingLevelName(java.util.logging.Level level) {
        return utilLevelToName.get(level);
    }
}
