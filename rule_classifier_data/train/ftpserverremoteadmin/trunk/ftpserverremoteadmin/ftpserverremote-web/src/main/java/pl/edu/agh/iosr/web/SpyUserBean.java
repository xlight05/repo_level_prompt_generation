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
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataProvider;
import pl.edu.agh.iosr.web.utils.ErrorPopup;

/**
 *
 * @author Tomasz Jadczyk
 */
public class SpyUserBean {

    private SelectItem[] userLabels;
    private String userLabel;
    private List<ConnectionData> observedConnections;
    private ConnectionData currentConnection;
    private String spyInfo;

    private ErrorPopup errorPopup;
    
    public SpyUserBean() {
        errorPopup = new ErrorPopup();
    }
    
    public ConnectionData getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(ConnectionData currentConnection) {
        this.currentConnection = currentConnection;
    }

    public List<ConnectionData> getObservedConnections() {
        setObservedConnections(DefaultContext.getInstance().getObservedConnections());
        return observedConnections;
    }

    public void setObservedConnections(List<ConnectionData> observedConnections) {
        this.observedConnections = observedConnections;
    }

    public SelectItem[] getUserLabels() {
        getObservedConnections();
        userLabels = new SelectItem[observedConnections.size()];
        int i = 0;
        for (ConnectionData data : observedConnections) {
            userLabels[i++] = new SelectItem(
                    new String(data.getUserName() + " " + data.getServerHostname()));
        }
        return userLabels;
    }

    public void setUserLabels(SelectItem[] userLabels) {
        this.userLabels = userLabels;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
        //set current connection
        for (ConnectionData data : observedConnections) {
            if (userLabel.equalsIgnoreCase(
                    data.getUserName() + " " + data.getServerHostname())) {
                setCurrentConnection(data);
                break;
            }
        //TODO: handle error when connection wasn't found
        }
    }

    public String getSpyInfo() {
        return spyInfo;
    }

    public void setSpyInfo(String spyInfo) {
        this.spyInfo = spyInfo;
    }

    public void reload() {

        getUserLabels();

        if (currentConnection != null) {
            Server srv = DefaultContext.getInstance().
                    getServer(currentConnection.getServerHostname());
            List<SpyUserData> dataList = ServerDataProvider.getSpyUserData(srv, currentConnection);

            StringBuilder infoBuilder = new StringBuilder();

            for (SpyUserData data : dataList) {
                infoBuilder.append(data.getDate());
                infoBuilder.append(" ");
                infoBuilder.append(data.getMessage());
                infoBuilder.append("\n");
            }

            setSpyInfo(infoBuilder.toString());
        }
    }

    public void clear() {
        if (currentConnection != null) {
            Server srv = DefaultContext.getInstance().
                    getServer(currentConnection.getServerHostname());
            ServerDataProvider.clearSpyUserData(srv, currentConnection);
            setSpyInfo("");
        }
    }

    public void disconnect() {
        if (currentConnection != null) {
            Server server = DefaultContext.getInstance().getServer(currentConnection.getServerHostname());
            CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());
            try {
                Command command = commandFactory.getConnectionDisconnectCommand(server, currentConnection);
                command.execute();
            } catch (CommunicationException ex) {
                Logger.getLogger(SpyUserBean.class.getName()).log(Level.SEVERE, null, ex);
                errorPopup.showError("communication_error");
            } catch (ServerNotSupportedException ex) {
                DefaultContext.getInstance().removeServer(server);
                errorPopup.showError("communication_error");
            }
        }
    }

    public void close() {
        if (currentConnection != null) {
            DefaultContext.getInstance().removeObservedConnection(currentConnection);
            clear();//clear logs
            //Send info to remote
             Server server = DefaultContext.getInstance().getServer(currentConnection.getServerHostname());
            CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());
            try {
                Command command = commandFactory.getStopSpyingUserCommand(server, currentConnection);
                command.execute();
            } catch (CommunicationException ex) {
                Logger.getLogger(SpyUserBean.class.getName()).log(Level.SEVERE, null, ex);
                errorPopup.showError("communication_error");
            } catch (ServerNotSupportedException ex) {
                errorPopup.showError("communication_error");
                DefaultContext.getInstance().removeServer(server);
            }
            
            currentConnection = null;
            setSpyInfo("");
            reload();
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
