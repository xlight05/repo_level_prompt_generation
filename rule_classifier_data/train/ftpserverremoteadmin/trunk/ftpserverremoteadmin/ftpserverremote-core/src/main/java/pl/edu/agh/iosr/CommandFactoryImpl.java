package pl.edu.agh.iosr;

import pl.edu.agh.iosr.ftpserverremote.command.ChangeLoggingLevelCommand;
import pl.edu.agh.iosr.ftpserverremote.command.Command;
import pl.edu.agh.iosr.ftpserverremote.command.DisconnectCommand;
import pl.edu.agh.iosr.ftpserverremote.command.LogOutCommand;
import pl.edu.agh.iosr.ftpserverremote.command.ServerResumeCommand;
import pl.edu.agh.iosr.ftpserverremote.command.ServerStartCommand;
import pl.edu.agh.iosr.ftpserverremote.command.ServerStopCommand;
import pl.edu.agh.iosr.ftpserverremote.command.ServerSuspendCommand;
import pl.edu.agh.iosr.ftpserverremote.command.SpyUserCommand;
import pl.edu.agh.iosr.ftpserverremote.command.StopSpyingUserCommand;
import pl.edu.agh.iosr.ftpserverremote.data.ConnectionData;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.facade.ServerFacade;

/**
 * Implementation of CommandFactory interface 
 * 
 * @author Agnieszka Janowska
 */
public class CommandFactoryImpl implements CommandFactory {
    
    private static CommandFactoryImpl instance;
    
    private Context context; 
    
    private CommandFactoryImpl(Context context) {
	this.context = context;
    }

    public Command getServerStartCommand(Server server) throws ServerNotSupportedException {
	ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
	    return new ServerStartCommand(facade);
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

    public Command getServerStopCommand(Server server) throws ServerNotSupportedException {
	ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
	    return new ServerStopCommand(facade);
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

    public Command getServerSuspendCommand(Server server) throws ServerNotSupportedException {
	ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
	    return new ServerSuspendCommand(facade);
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

    public Command getServerResumeCommand(Server server) throws ServerNotSupportedException {
	ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
	    return new ServerResumeCommand(facade);
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

    public Command getConnectionDisconnectCommand(Server server, 
            ConnectionData connection) throws ServerNotSupportedException {
        ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
            DisconnectCommand command = new DisconnectCommand(facade);
            command.setConnectionData(connection);
	    return command;
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }
    
    public void setContext(Context context) {
	this.context = context;
    }
    
    public static CommandFactoryImpl getInstance(Context context) {
	if (instance == null) {
	    instance = new CommandFactoryImpl(context);
	} else {
	    instance.setContext(context);
	}
	return instance;
    }

    public Command getSpyUserCommand(Server server, ConnectionData data) throws ServerNotSupportedException {
        ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
            SpyUserCommand command = new SpyUserCommand(facade);
            command.setConnectionData(data);
	    return command;
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

    public Command getStopSpyingUserCommand(Server server, ConnectionData data) throws ServerNotSupportedException {
        ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
            StopSpyingUserCommand command = new StopSpyingUserCommand(facade);
            command.setConnectionData(data);
	    return command;
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

    public Command getChangeLoggingLevelCommand(Server server, String level) throws ServerNotSupportedException {
         ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
            ChangeLoggingLevelCommand command = new ChangeLoggingLevelCommand(facade);
            command.setLevel(level);
	    return command;
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

    public Command getLogOutCommand(Server server) throws ServerNotSupportedException {
        ServerFacade facade = context.getServerSideFacade(server);
	if (facade != null) {
            LogOutCommand command = new LogOutCommand(facade);
	    return command;
	} else {
	    throw new ServerNotSupportedException("There is no connection to server " + server);
	}
    }

}
