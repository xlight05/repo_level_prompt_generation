package pl.edu.agh.iosr;

import java.util.List;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataContainer;

/**
 * 
 * Holds servers and facades (and other information) to servers being currently configured.
 * It should be accessible from every system's module and updated each time the server list has been changed. 
 * 
 * @author Agnieszka Janowska
 * 
 */
public interface Context {
    
    /**
     * Returns list of server currently being configured
     * @return list of working servers
     */
    public List<Server> getServers();
    
    /**
     * Returns Server based on string representation of server's address (hostname:port)
     * @param address String representation of server's address
     * @return Server on given address
     */
    public Server getServer(String address);
    
    /**
     * Adds server to list
     * @param server
     * @return true if there is connection to server; false otherwise
     */
    public boolean addServer(Server server);
    
    /**
     * Adds servers to list
     * @param servers
     * @return list of servers actually added to list (those to which connection suceeded)
     */
    public List<Server> addServers(List<Server> servers);
    
    /**
     * Creates new servers list that contains all objects which was in context before unchanged.
     * @param servers
     * @return list of servers actually in context
     */
    public List<Server> setAllServers(List<Server> servers);
    
    /**
     * Removes server from list
     * @param server
     */
    public void removeServer(Server server);
    
    /**
     * Removes servers from list
     * @param servers
     */
    public void removeServers(List<Server> servers);
    
    /**
     * Returns facade working remotely (on server's side)
     * @param server
     * @return remote facade to server
     */
    public ServerFacade getServerSideFacade(Server server);
    
    /**
     * Returns data container which contains data received from remote server
     * (Connections, Files, Directories, Logs and Statistics)
     * @param server
     * @return data container for this server
     */
    public ServerDataContainer getServerDataContainer(Server server);
    
    /**
     * Adds observed connection to list
     * @param connection
     */
    public void addObservedConnection(ConnectionData connection);
    
    /**
     * Removes connection from observed connections list
     * @param connection
     */
    public void removeObservedConnection(ConnectionData connection);
    
    /**
     * Returns all observed connections in current context
     * @return observed connections list
     */
    public List<ConnectionData> getObservedConnections();
    
    /**
     * Releases all the components
     */
    public void dispose();
}
