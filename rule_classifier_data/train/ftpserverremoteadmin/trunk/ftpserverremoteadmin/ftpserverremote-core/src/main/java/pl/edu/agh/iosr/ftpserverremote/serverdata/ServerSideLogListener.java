package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.logging.LogRecord;

import org.apache.ftpserver.util.IoUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;

import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;

/**
 * Apache Ftp Server uses slf4j system, which provides uniform access to log
 * informations but doesn't support adding logger objects and manage logging level.
 * This class creates two log handlers(appenders) and is responsible for set up this
 * handlers, passed data to dataListener and clean up when logging is finished.
 * Default logging level is NONE. 
 * @author Tomasz Jadczyk
 */
public class ServerSideLogListener {

    private ServerDataListener dataListener;
    private Appender appender;
    private LogsHandler handler;
    private String currentLoggingLevel;

    /**
     * Inner class to handle Log4j logging system.
     */
    private class LoggerAppender extends AppenderSkeleton {

        protected void append(LoggingEvent event) {
            if (ServerLogsLevelProvider.isActiveLevel(currentLoggingLevel, event.getLevel())) {

                Throwable throwable = null;
                if(event.getThrowableInformation() != null) {
                    throwable = event.getThrowableInformation().getThrowable();
                }
                
                  sendLogMessage(
                        event.getLevel().toString(),
                        event.getMessage().toString(), throwable);
            }
        }

        public void close() {
            // do nothing
        }

        public boolean requiresLayout() {
            return false;
        }
    }

    /**
     * Inner class to handle java.util.logging logging system
     */
    private class LogsHandler extends java.util.logging.Handler {

        public LogsHandler() {
            setLevel(java.util.logging.Level.INFO);
        }

        @Override
        public void publish(LogRecord record) {
            if (ServerLogsLevelProvider.isActiveLevel(currentLoggingLevel, record.getLevel())) {
                sendLogMessage(
                        ServerLogsLevelProvider.getLoggingLevelName(record.getLevel()),
                        record.getMessage(), record.getThrown());
            }
        }

        /**
         * Not used
         */
        @Override
        public void flush() {
        //throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Not used
         * @throws java.lang.SecurityException
         */
        @Override
        public void close() throws SecurityException {
            //throw new UnsupportedOperationException("Not supported yet.");
            return;
        }
    }

    /**
     * Construct both log handlers types objects, setup them with data listener,
     * set currentLogging level as None and register handlers in log managers.
     * @param dataListener
     */
    public ServerSideLogListener(ServerDataListener dataListener) {
        
        this.dataListener = dataListener;

        //default logging level is NONE (last in table log levels):
        currentLoggingLevel = ServerLogsLevelProvider.getLogLevels()[
                ServerLogsLevelProvider.getLogLevels().length - 1];
        
        appender = new LoggerAppender();
        LogManager.getRootLogger().addAppender(appender);

        handler = new LogsHandler();
        Enumeration<String> enumer =
                java.util.logging.LogManager.getLogManager().getLoggerNames();

        while (enumer.hasMoreElements()) {
            java.util.logging.LogManager.getLogManager().
                    getLogger(enumer.nextElement()).
                    addHandler(handler);
        }

    }

    /**
     * Change current accepted logging level to passed in arguments
     * @param logLevel New logging level.
     */
    public void setLogLevel(String logLevel) {
        currentLoggingLevel = logLevel;
        if (appender != null) {
            LogManager.getRootLogger().setLevel(
                    ServerLogsLevelProvider.getLog4jLevel(logLevel));
        }
        if (handler != null) {
            handler.setLevel(
                    ServerLogsLevelProvider.getUtilLoggingLevel(logLevel));
        }
    }

    /**
     * Clean up when logging is stopped (i.e. WebInterface disconnect from this
     * server and passing logs is not needed). Remove handler and appender from
     * logging managers from both logging systems.
     */
    public void stopLogging() {
        LogManager.getRootLogger().removeAppender(appender);
        Enumeration<String> enumer =
                java.util.logging.LogManager.getLogManager().getLoggerNames();

        while (enumer.hasMoreElements()) {
            java.util.logging.LogManager.getLogManager().
                    getLogger(enumer.nextElement()).
                    removeHandler(handler);
        }
    }
    
    /**
     * Method used in both logger objects. Create and format message from 
     * passed data and send it to remote data receiver.
     * @param level Logging level
     * @param message Log message
     * @param throwable Throwable object
     */
    private void sendLogMessage(String level, String message, Throwable throwable) {
        LogData logData = new LogData();

                logData.setLevel(level);
                String msg = '[' + level + "] " + message + '\n';
                logData.setMessage(msg);

                if (throwable != null) {
                    logData.setStackTrace(IoUtils.getStackTrace(throwable));
                }

                try {
                    dataListener.addLogData(logData);
                } catch (RemoteException e) {
                    //do not logging, if error occures - it calls the same method
                    //and causes stack overflow
                }
    }
    
    
}
