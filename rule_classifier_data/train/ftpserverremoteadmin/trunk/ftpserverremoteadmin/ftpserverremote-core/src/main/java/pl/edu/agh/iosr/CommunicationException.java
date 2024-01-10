package pl.edu.agh.iosr;

import java.rmi.RemoteException;

/**
 * Thrown when there is communication problem (in current implementation - related to RMI) between server and web sides
 * 
 * @author Tomasz Jadczyk
 */
public class CommunicationException extends RemoteException {

  private static final long serialVersionUID = 1L;

  public CommunicationException(final String message, final Throwable e) {
    super( message, e );
  }

  public CommunicationException(final Throwable e) {
    this( "", e );
  }
}
