package pl.edu.agh.iosr.ftpserverremote.command;

import pl.edu.agh.iosr.CommunicationException;

/**
 * Interface for FTPServer managing commands invoked from a web interface side
 * 
 * @author Tomasz Jadczyk
 */
public interface Command {
    
    public void execute() throws CommunicationException;

}
