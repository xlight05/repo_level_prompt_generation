
package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.ServerData;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 * This remote objects are passed to server facades and all server information
 * observers objects operate on this remote data listener. 
 * For each connection to remote facade in current context, there is exactly one 
 * data listener object.
 * @author Tomasz Jadczyk
 */
public class ServerDataListenerImpl extends UnicastRemoteObject implements ServerDataListener,
    IServerDataListenerConfigurable{

    private ServerDataContainer dataContainer;
    private Server server;
     
    public ServerDataListenerImpl(Server server) throws RemoteException {
        this.server = server;
    }
    
    public void setServerDataContainer(ServerDataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }
    
    public void addConnection(ConnectionData data) throws RemoteException {
         if (this.dataContainer != null) {
            this.dataContainer.addConnectionData(data);
        }
    }

    public void removeConnection(ConnectionData data) throws RemoteException {
        if(this.dataContainer != null) {
                dataContainer.getConnectionDataList().remove(data);
        }
    }

    public void updateConnection(ConnectionData data) throws RemoteException {
        if(this.dataContainer != null) {
            int dataIndex = dataContainer.getConnectionDataList().indexOf(data);
            if(dataIndex != -1) {
                dataContainer.getConnectionDataList().set(dataIndex, data);
            } else {
                dataContainer.addConnectionData(data);
            }
        }
    }

    public void setAllConections(List<ConnectionData> dataList) throws RemoteException {
        if(this.dataContainer != null) {
            for(ConnectionData data : dataList) {
                addServerInfo(data);
            }
            dataContainer.setConnectionDataList(dataList);
        }
    }

    public void addFileData(FileData data) throws RemoteException {
        if(data != null && this.dataContainer != null) {
            addServerInfo(data);
            dataContainer.addFileData(data);
        }
    }

    public void setServerStat(StatisticsData data) throws RemoteException {
        if(this.dataContainer != null) {
            dataContainer.setServerStat(data);
        }
    }

    public void addLogData(LogData data) throws RemoteException {
        if(this.dataContainer != null) {
            addServerInfo(data);
            dataContainer.addLogData(data);
        }
    }

    public void addSpyUserData(SpyUserData data) throws RemoteException {
        if(this.dataContainer != null) {
            addServerInfo(data);
            dataContainer.addSpyUserData(data);
        }
    }

    /**
     * Adds printable server's informations to data.
     * @param data
     */
    private void addServerInfo(ServerData data) {
        data.setServerHostname(server.getHostname() + ":" + server.getPort());
    }
}
