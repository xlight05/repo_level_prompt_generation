package pl.edu.agh.iosr.ftpserverremote.command;

import java.rmi.RemoteException;
import pl.edu.agh.iosr.CommunicationException;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Command to resume suspended FTPServer
 * 
 * @author Agnieszka Janowska
 */
public class ServerResumeCommand implements Command {

  private ServerFacade facade = null;

  public ServerResumeCommand(final ServerFacade facade) {
    this.facade = facade;
  }

  public void execute() throws CommunicationException {
    try {
      facade.resumeServer();
    }
    catch (final RemoteException ex) {
      throw new CommunicationException( ex );
    }
  }
}
