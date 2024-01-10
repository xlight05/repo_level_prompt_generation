package pl.edu.agh.iosr;

import pl.edu.agh.iosr.ftpserverremote.command.Command;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;

/**
 * Command factory for FTPServer managing Command objects. 
 * Given a Server object it produces requested Command object for this specific server.
 * If there is no connection to this server it throws ServerNotSupportedException.  
 * 
 * @author Agnieszka Janowska
 */
public interface CommandFactory {
    
    public Command getServerStartCommand(Server server) throws ServerNotSupportedException;
    
    public Command getServerStopCommand(Server server) throws ServerNotSupportedException;
    
    public Command getServerSuspendCommand(Server server) throws ServerNotSupportedException;
    
    public Command getServerResumeCommand(Server server) throws ServerNotSupportedException;
    
    public Command getConnectionDisconnectCommand(Server server, ConnectionData data) throws ServerNotSupportedException;
    
    public Command getSpyUserCommand(Server server, ConnectionData data) throws ServerNotSupportedException;
    
    public Command getStopSpyingUserCommand(Server server, ConnectionData data) throws ServerNotSupportedException;
    
    public Command getChangeLoggingLevelCommand(Server server, String level) throws ServerNotSupportedException;

    public Command getLogOutCommand(Server server) throws ServerNotSupportedException;
}
