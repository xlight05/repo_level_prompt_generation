package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to suspend running server
 * 
 * @author Agnieszka Janowska
 */
public class ServerSuspendCommand implements Command {

    public ServerFacade facade = null;

    public ServerSuspendCommand(final ServerFacade facade) {
	this.facade = facade;
    }

    public void execute() throws CommunicationException {
	try {
	    facade.suspendServer();
	} catch (final RemoteException ex) {
	    throw new CommunicationException(ex);
	}
    }
}
