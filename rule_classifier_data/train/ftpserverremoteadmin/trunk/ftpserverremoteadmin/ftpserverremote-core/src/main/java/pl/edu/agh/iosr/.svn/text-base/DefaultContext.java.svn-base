package pl.edu.agh.iosr;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataContainer;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataContainerImpl;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataListenerImpl;

/**
 * Singleton implementation of Context interface.
 * This is currently the only implementation and is used by default by all the components.
 * 
 * @author Agnieszka Janowska
 */
public class DefaultContext implements Context {

    private static DefaultContext instance;
    protected List<Server> servers;
    protected List<ConnectionData> observedConnections;
    protected Map<Server, ServerFacade> remoteFacades;
    protected Map<Server, ServerDataContainer> dataContainers;
    private Logger logger = LoggerFactory.getLogger(DefaultContext.class);

    protected DefaultContext() {
	servers = new LinkedList<Server>();
	observedConnections = new LinkedList<ConnectionData>();
	remoteFacades = new HashMap<Server, ServerFacade>();
	dataContainers = new HashMap<Server, ServerDataContainer>();
    }

    public synchronized List<Server> getServers() {
	return servers;
    }

    public synchronized Server getServer(String address) {
	for (Server s : servers) {
	    if ((s.getHostname() + ":" + s.getPort()).equals(address)) {
		return s;
	    }
	}
	return null;
    }

    public synchronized ServerFacade getServerSideFacade(Server server) {
	return remoteFacades.get(server);
    }

    public synchronized ServerDataContainer getServerDataContainer(Server server) {
	return dataContainers.get(server);
    }

    public synchronized void dispose() {
	servers.clear();
	observedConnections.clear();
	remoteFacades.clear();
	dataContainers.clear();
    }

    public synchronized boolean addServer(Server server) {
	try {
	    initializeServer(server);
	    return true;
	} catch (NotBoundException ex) {
	    logger.error("addServer", ex);
	    return false;
	} catch (MalformedURLException ex) {
	    logger.error("addServer", ex);
	    return false;
	} catch (RemoteException ex) {
	    logger.error("addServer", ex);
	    return false;
	}
    }

    public synchronized void removeServer(Server server) {
	servers.remove(server);
	remoteFacades.remove(server);
	dataContainers.remove(server);
    }

    public synchronized List<Server> addServers(List<Server> servers) {
	List<Server> workingServers = new LinkedList<Server>();
	workingServers.addAll(servers);
	for (Server s : servers) {
	    try {
		initializeServer(s);
	    } catch (NotBoundException ex) {
		logger.error("addServers", ex);
		workingServers.remove(s);
	    } catch (MalformedURLException ex) {
		logger.error("addServers", ex);
		workingServers.remove(s);
	    } catch (RemoteException ex) {
		logger.error("addServers", ex);
		workingServers.remove(s);
	    }
	}
	return workingServers;
    }

    public synchronized void removeServers(List<Server> servers) {
	for (Server s : servers) {
	    removeServer(s);
	}
    }

    protected void initializeServer(Server server) throws NotBoundException, MalformedURLException, RemoteException {
	ServerFacade remoteFacade = (ServerFacade) Naming.lookup(generateRemoteObjectName(server));
	ServerDataListenerImpl dataListener = new ServerDataListenerImpl(server);
	
        ServerDataContainer dataContainer = new ServerDataContainerImpl();
	dataListener.setServerDataContainer(dataContainer);
        
        remoteFacade.assignServerDataListener(dataListener);
	
	remoteFacades.put(server, remoteFacade);
	dataContainers.put(server, dataContainer);
	servers.add(server);
	logger.debug("initializeServer", "Server " + server.getHostname() + " is working and added to list of working servers");
    }

    //TODO: change this method to generate different names depending on server's specific information, i.e port or id (in case there is more than one running instance of FTPServer on a host)
    protected String generateRemoteObjectName(Server server) {
	return new String("rmi://" + server.getHostname() + ":" + RMIConfig.RMIREGISTRY_PORT + "/" + ServerFacade.SERVERFACADENAME);
    }

    public synchronized void addObservedConnection(ConnectionData connection) {
	if (!observedConnections.contains(connection)) {
	    observedConnections.add(connection);
	}
    }

    public synchronized void removeObservedConnection(ConnectionData connection) {
	observedConnections.remove(connection);
    }

    public synchronized List<ConnectionData> getObservedConnections() {
	return observedConnections;
    }

    public synchronized List<Server> setAllServers(List<Server> servers) {
	List<Server> newServers = new LinkedList<Server>();
	Map<Server, ServerFacade> newRemoteFacades = new HashMap<Server, ServerFacade>();
	Map<Server, ServerDataContainer> newDataContainers = new HashMap<Server, ServerDataContainer>();

	for (Server srv : servers) {
	    if (this.servers.contains(srv)) {
		newServers.add(srv);
		newRemoteFacades.put(srv, remoteFacades.get(srv));
		newDataContainers.put(srv, dataContainers.get(srv));
	    }
	}
	//remove listeners from remote facades
	for (Server srv : this.servers) {
	    if (!servers.contains(srv)) {
		try {
		    getServerSideFacade(srv).removeServerDataListener();
		} catch (Exception e) {
		    logger.debug("setAllServers", e);
		}
	    }
	}

	this.servers = newServers;
	this.remoteFacades = newRemoteFacades;
	this.dataContainers = newDataContainers;

	for (Server srv : servers) {
	    if (!this.servers.contains(srv)) {
		try {
		    initializeServer(srv);
		} catch (Exception ex) {
		    logger.error("setAllServers", ex);
		}
	    }
	}

	return this.servers;
    }

    public static synchronized DefaultContext getInstance() {
	if (instance == null) {
	    instance = new DefaultContext();
	}
	return instance;
    }
}
