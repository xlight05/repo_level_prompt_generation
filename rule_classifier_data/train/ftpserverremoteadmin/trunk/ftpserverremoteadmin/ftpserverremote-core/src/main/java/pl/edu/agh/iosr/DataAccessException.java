package pl.edu.agh.iosr;

/**
 * Thrown when there is problem in database or generally in persistence layer
 * 
 * @author Agnieszka Janowska
 */
public class DataAccessException extends Exception {

  private static final long serialVersionUID = 1L;

  public DataAccessException() {
    super();
  }

  public DataAccessException(final String msg) {
    super( msg );
  }

  public DataAccessException(final Throwable e) {
    super( e );
  }
}
