/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.agh.iosr.web;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.myfaces.tobago.model.SheetState;
import pl.edu.agh.iosr.CommandFactory;
import pl.edu.agh.iosr.CommandFactoryImpl;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.ServerNotSupportedException;
import pl.edu.agh.iosr.ftpserverremote.command.Command;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataProvider;

/**
 *
 * @author Tomasz Jadczyk
 */
public class ConnectionBean {

    private List<ConnectionData> connections;
    private SheetState sheetState;
    
    public ConnectionBean() {
        connections = ServerDataProvider.getConnectionDataList();
    }

    public List<ConnectionData> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectionData> connections) {
        this.connections = connections;
    }

    public SheetState getSheetState() {
        return sheetState;
    }

    public void setSheetState(SheetState sheetState) {
        this.sheetState = sheetState;
    }
    
    public void reload() {
        connections = ServerDataProvider.getConnectionDataList();
    }
    
    public void disconnect() {
        if(isRowSelected()) {
            ConnectionData connection = getSelectedConnection();
            Server server = DefaultContext.getInstance().getServer(connection.getServerHostname());
            CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());
            try {
                Command command = commandFactory.
                        getConnectionDisconnectCommand(server, connection);
                command.execute();
            } catch (CommunicationException ex) {
                Logger.getLogger(ConnectionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch(ServerNotSupportedException ex) {
                DefaultContext.getInstance().removeServer(server);
            }
        }
    }
    
    public void spyUser() {
        if(isRowSelected()) {
            ConnectionData connection = getSelectedConnection();
            Server server = DefaultContext.getInstance().getServer(connection.getServerHostname());
            CommandFactory commandFactory = CommandFactoryImpl.getInstance(DefaultContext.getInstance());
            try {
                Command command = commandFactory.
                        getSpyUserCommand(server, connection);
                command.execute();
                
                 DefaultContext.getInstance().addObservedConnection(connection);
            } catch (CommunicationException ex) {
                Logger.getLogger(ConnectionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch(ServerNotSupportedException ex) {
                DefaultContext.getInstance().removeServer(server);
            }
        }
    }
    
    private boolean isRowSelected() {
        return sheetState.getSelectedRows().size() > 0;
    }
    
    private ConnectionData getSelectedConnection() {
        ConnectionData connection = null;
        if(isRowSelected()) {
            int selectedRowIndex = sheetState.getSelectedRows().get(0).intValue();
            connection = connections.get(selectedRowIndex);
        }
        return connection;
    }
    
}
