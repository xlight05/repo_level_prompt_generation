package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to stop spying user connection
 * 
 * @author Tomasz Jadczyk
 */
public class StopSpyingUserCommand implements Command {

    private final ServerFacade facade;
    private ConnectionData connectionData = null;

    public StopSpyingUserCommand(final ServerFacade facade) {
	this.facade = facade;
    }

    public void execute() throws CommunicationException {
	if (connectionData != null) {
	    try {
		facade.stopSpyingUser(connectionData);
	    } catch (final RemoteException ex) {
		throw new CommunicationException(ex);
	    }
	}
    }

    public void setConnectionData(final ConnectionData connectionData) {
	this.connectionData = connectionData;
    }
}
