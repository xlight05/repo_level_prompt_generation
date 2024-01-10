package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;

import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to start FTPServer
 * 
 * @author Tomasz Jadczyk
 */
public class ServerStartCommand implements Command {

    private ServerFacade facade = null;

    public ServerStartCommand(final ServerFacade facade) {
	this.facade = facade;
    }

    public void execute() throws CommunicationException {
	try {
	    facade.startServer();
	} catch (final RemoteException ex) {
	    throw new CommunicationException(ex);
	}
    }
}
