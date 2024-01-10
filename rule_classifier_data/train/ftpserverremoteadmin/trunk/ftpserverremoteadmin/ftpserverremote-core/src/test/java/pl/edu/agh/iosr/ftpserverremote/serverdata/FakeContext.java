/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.agh.iosr.ftpserverremote.serverdata;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.agh.iosr.Context;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 *
 * @author Agnieszka Janowska
 */
public class FakeContext extends DefaultContext {
    
    private static FakeContext instance; 
    
    private FakeContext() {	
    }
    
    public synchronized static FakeContext getInstance() {
	if (instance == null) {
	    instance = new FakeContext();
	}
	return instance;
    }
    
    @Override
    public void initializeServer(Server server) {
	try {
	    ServerDataListenerImpl dataListener = new ServerDataListenerImpl(server);
	    ServerDataContainer dataContainer = new ServerDataContainerImpl();
	    dataListener.setServerDataContainer(dataContainer);
	    dataContainers.put(server, dataContainer);
	    servers.add(server);
	} catch (RemoteException ex) {
	}
    }
}
