package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import pl.edu.agh.iosr.ftpserverremote.data.LogData;
import pl.edu.agh.iosr.ftpserverremote.data.SpyUserData;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;

/**
 * Remote accessibly interface to gather informations from server facades.
 * 
 * @author Tomasz Jadczyk
 */
public interface ServerDataListener extends Remote {

    //TODO: remove this method
    public void addConnection(ConnectionData data) throws RemoteException;

    //TODO: remove this method
    public void removeConnection(ConnectionData data) throws RemoteException;
    
    //TODO: remove this method
    public void updateConnection(ConnectionData data) throws RemoteException;
    
    /**
     * Replace current connections list with this passed in argument. 
     * @param dataList Connections 
     * @throws java.rmi.RemoteException
     */
    public void setAllConections(List<ConnectionData> dataList) throws RemoteException;
    
    /**
     * Add file informations to data container
     * @param data
     * @throws java.rmi.RemoteException
     */
    public void addFileData(FileData data) throws RemoteException;
    
    /**
     * Replace statistic information with passed in argument
     * @param data
     * @throws java.rmi.RemoteException
     */
    public void setServerStat(StatisticsData data) throws RemoteException;
    
    /**
     * Add logs information to data container
     * @param data
     * @throws java.rmi.RemoteException
     */
    public void addLogData(LogData data) throws RemoteException;
    
    /**
     * Add observed connection informations to data container.
     * @param data
     * @throws java.rmi.RemoteException
     */
    public void addSpyUserData(SpyUserData data) throws RemoteException;
}
