package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to spy user connection
 * 
 * @author Tomasz Jadczyk
 */
public class SpyUserCommand implements Command {

    private final ServerFacade facade;
    private ConnectionData connectionData = null;

    public SpyUserCommand(final ServerFacade facade) {
	this.facade = facade;
    }

    public void execute() throws CommunicationException {
	if (connectionData != null) {
	    try {
		facade.spyUser(connectionData);
	    } catch (final RemoteException ex) {
		throw new CommunicationException(ex);
	    }
	}
    }

    public void setConnectionData(final ConnectionData connectionData) {
	this.connectionData = connectionData;
    }
}
