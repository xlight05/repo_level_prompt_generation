package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to change level of logged messages
 * 
 * @author Tomasz Jadczyk
 */
public class ChangeLoggingLevelCommand implements Command {

    private final ServerFacade facade;
    private String level = null;

    public ChangeLoggingLevelCommand(final ServerFacade facade) {
	this.facade = facade;
    }

    public void execute() throws CommunicationException {
	if (level != null) {
	    try {
		facade.setLoggingLevel(level);
	    } catch (final RemoteException e) {
		throw new CommunicationException(e);
	    }
	}
    }

    public void setLevel(final String level) {
	this.level = level;
    }
}
