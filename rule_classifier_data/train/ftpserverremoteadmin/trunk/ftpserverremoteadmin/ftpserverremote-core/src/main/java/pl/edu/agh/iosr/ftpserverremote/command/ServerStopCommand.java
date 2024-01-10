package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;

import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to stop running server
 * 
 * @author Tomasz Jadczyk
 */
public class ServerStopCommand implements Command {

    private ServerFacade facade = null;

    public ServerStopCommand(final ServerFacade facade) {
	this.facade = facade;
    }

    public void execute() throws CommunicationException {
	try {
	    facade.stopServer();
	} catch (final RemoteException ex) {
	    throw new CommunicationException(ex);
	}
    }
}
