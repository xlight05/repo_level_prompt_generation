package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to notify server that admin has logged out (removes server listener)
 * 
 * @author Tomasz Jadczyk
 */
public class LogOutCommand implements Command {

    private ServerFacade facade = null;

    public LogOutCommand(final ServerFacade facade) {
	this.facade = facade;
    }

    public void execute() throws CommunicationException {
	try {
	    facade.removeServerDataListener();
	} catch (final RemoteException ex) {
	    throw new CommunicationException(ex);
	}
    }
}
