package pl.edu.agh.iosr.ftpserverremote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataListener;

/**
 * Server facade remote interface.
 * ServerFacade is used to manage FTPServer through RMI. It should be located on a server side.
 * 
 * @author Agnieszka Janowska
 */
public interface ServerFacade extends Remote {

    /**
     * Default name to register and lookup facade in rmiregistry
     */
    public static final String SERVERFACADENAME = "ServerSideFacade";

    /**
     * Disconnect user from ftp server.
     * @param connectionData Connection description, it is mapped onto Connection object
     * used by FtpServer
     * @throws java.rmi.RemoteException
     */
    public void closeConnection(ConnectionData connectionData) throws RemoteException;
    
    /**
     * Gather data about user actions on ftp server and pass it to Web Interface
     * @param connectionData Connection to observe description
     * @throws java.rmi.RemoteException
     */
    public void spyUser(ConnectionData connectionData) throws RemoteException;
    
    /**
     * Calling this method ends spying user actions.
     * @param connectionData Observed connection description
     * @throws java.rmi.RemoteException
     */
    public void stopSpyingUser(ConnectionData connectionData) throws RemoteException;
    
    /**
     * Returns true if server is stopped (according to criteria from original FTPServer)
     * @return true if server is stopped; false otherwise
     * @throws java.rmi.RemoteException
     */
    public boolean isServerStopped() throws RemoteException;
    
    /**
     * Returns true if server is suspended (according to criteria from original FTPServer)
     * @return true if server is suspended; false otherwise
     * @throws java.rmi.RemoteException
     */
    public boolean isServerSuspended() throws RemoteException;

    /**
     * Resumes FTP Server
     * @throws java.rmi.RemoteException
     */
    public void resumeServer() throws RemoteException;

    /**
     * Starts FTP Server
     * @throws java.rmi.RemoteException
     */
    public void startServer() throws RemoteException;

    /**
     * Stops FTP Server
     * @throws java.rmi.RemoteException
     */
    public void stopServer() throws RemoteException;

    /**
     * Suspends FTP Server
     * @throws java.rmi.RemoteException
     */
    public void suspendServer() throws RemoteException;

    /**
     * Calling this method is required to obtain data from server, such as 
     * connections lists, statistics, files and dirs list and it must be called
     * before calling spyUser method
     * @param dataListener Remote data listener object 
     * @throws java.rmi.RemoteException
     */
    public void assignServerDataListener(ServerDataListener dataListener) throws RemoteException;
    
    /**
     * Performs cleanup after disconnect Web interface from remote facade.
     * @throws java.rmi.RemoteException
     */
    public void removeServerDataListener() throws RemoteException;
    
    /**
     * Change acceptable log level, which are passed to Web Interface
     * @param level Level description
     * @throws java.rmi.RemoteException
     */
    public void setLoggingLevel(String level) throws RemoteException;
}
