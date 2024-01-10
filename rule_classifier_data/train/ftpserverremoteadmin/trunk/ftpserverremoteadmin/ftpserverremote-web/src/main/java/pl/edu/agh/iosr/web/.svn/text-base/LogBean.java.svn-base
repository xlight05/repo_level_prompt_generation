/*
 * LogBean.java
 *
 * Created on 2007-12-01, 16:01:52
 *
 */
package pl.edu.agh.iosr.web;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import pl.edu.agh.iosr.CommandFactory;
import pl.edu.agh.iosr.CommandFactoryImpl;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.ServerNotSupportedException;
import pl.edu.agh.iosr.ftpserverremote.command.Command;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataProvider;
import pl.edu.agh.iosr.web.provider.LogProvider;
import pl.edu.agh.iosr.web.provider.ServerProvider;
import pl.edu.agh.iosr.web.utils.ErrorPopup;

/**
 *
 * Class for handling data in page with logging
 *
 * @author Monika Nawrot, Tomasz Jadczyk
 */
public class LogBean {

    private SelectItem[] logLevelOptions;
    private String logLevelOption;
    private String serverLabel;
    private SelectItem[] serverLabels;
    private String areaMessage;
    private ErrorPopup errorPopup;

    /**
     * Creates a new instance of LogBean
     */
    public LogBean() {

        errorPopup = new ErrorPopup();

        /* set log level options default level: NONE*/
        logLevelOptions = LogProvider.getSelectableLevels();
        logLevelOption = logLevelOptions[logLevelOptions.length - 1].getLabel();
        areaMessage = "";
        setServerLabels(ServerProvider.getCurrentContextServers());
    }

    /**
     * Clear text area with log messages
     *
     */
    public void clear() {
        setAreaMessage("");
        if (serverLabel != null) {
            String srvName = serverLabel;
            Server server = DefaultContext.getInstance().getServer(srvName);
            ServerDataProvider.clearLogs(server);
        }
    }

    /**
     * Change shown log level
     *
     */
    public void logLevelChanged() {

        Server server = DefaultContext.getInstance().getServer(serverLabel);
        CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());
        try {
            Command command = commandFactory.getChangeLoggingLevelCommand(server, logLevelOption);
            command.execute();
        } catch (CommunicationException ex) {
            Logger.getLogger(LogBean.class.getName()).log(Level.SEVERE, null, ex);
            errorPopup.showDataError();
        } catch (ServerNotSupportedException ex) {
            DefaultContext.getInstance().removeServer(server);
            errorPopup.showError("unsupported_logging");
        }
    }

    public SelectItem[] getLogLevelOptions() {
        return logLevelOptions;
    }

    public void setLogLevelOptions(SelectItem[] logLevelOptions) {
        this.logLevelOptions = logLevelOptions;
    }

    public String getLogLevelOption() {
        return logLevelOption;
    }

    public void setLogLevelOption(String logLevelOption) {
        this.logLevelOption = logLevelOption;
    }

    public String getAreaMessage() {
        return areaMessage;
    }

    public void setAreaMessage(String areaMessage) {
        this.areaMessage = areaMessage;
    }

    public String getServerLabel() {
        return serverLabel;
    }

    public void setServerLabel(String serverLabel) {
        this.serverLabel = serverLabel;
    }

    public SelectItem[] getServerLabels() {
        return ServerProvider.getCurrentContextServers();
    }

    public void setServerLabels(SelectItem[] serverLabels) {
        this.serverLabels = serverLabels;
    }

    /**
     * Reload page. Refresh labels with servers and add logged messages to
     * the text area
     *
     */
    public void reload() {
        setServerLabels(ServerProvider.getCurrentContextServers());

        StringBuilder messageAreaBuilder = new StringBuilder();

        if (serverLabel != null) {
            String srvName = serverLabel;
            Server server = DefaultContext.getInstance().getServer(srvName);
            List<LogData> messages = ServerDataProvider.getServerLogs(server);
            for (LogData log : messages) {
                String message = log.getMessage();
                if (message != null) {
                    messageAreaBuilder.append(message);
                }
                String stackTrace = log.getStackTrace();

                if (stackTrace != null) {
                    messageAreaBuilder.append(stackTrace);
                }
                messageAreaBuilder.append("\n");
            }

            setAreaMessage(messageAreaBuilder.toString());
        }
    }

    /* error methods */
    public void closeErrorPopup() {
        errorPopup.hideError();
    }

    public void setErrorMessage(String errorMessageId) {
        errorPopup.showError(errorMessageId);
    }

    public String getErrorMessage() {
        return errorPopup.getErrorMessage();
    }

    public Boolean getShowErrorPopup() {
        return errorPopup.getShowErrorPopup();
    }

    public String getErrorHeader() {
        return errorPopup.getErrorHeader();
    }
}