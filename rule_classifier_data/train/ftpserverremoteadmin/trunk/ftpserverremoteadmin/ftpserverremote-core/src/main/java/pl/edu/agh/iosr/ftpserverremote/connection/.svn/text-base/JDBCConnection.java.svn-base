package pl.edu.agh.iosr.ftpserverremote.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * This is a wrapper class for JDBC <code>Connection</code>.
 * 
 * @author Tomasz Sadura
 *
 */
public class JDBCConnection {

  private static final Logger logger = Logger.getLogger( JDBCConnection.class );

  /**
   * Cached, open <code>Connection</code> to database.
   */
  private Connection cachedConnection;

  /**
   * <code>DataSource</code> with connection parameters.
   */
  private DataSource dataSource;

  /**
   * Sets the dataSource.
   * @param dataSource dataSource to set
   */
  public void setDataSource( final DataSource dataSource ) {
    this.dataSource = dataSource;
  }

  /**
   * Closes the connection.
   */
  public void closeConnection() {
    if ( cachedConnection != null ) {
      try {
        cachedConnection.close();
      }
      catch (final SQLException ex) {
        logger.debug( "JDBCConnection.closeConnection()", ex );
      }
      cachedConnection = null;
    }
    logger.info( "Database connection closed." );
  }

  /**
   * Returns a cached connection if a connection has been already made.
   * If not creates a new connection, stores it in <code>cachedConnection</code> and returns it. 
   * @return the connection
   * @throws SQLException
   */
  public synchronized Connection createConnection() throws SQLException {
    boolean isClosed = false;
    try {
      if ( (cachedConnection == null) || cachedConnection.isClosed() ) {
        isClosed = true;
      }
    }
    catch (final SQLException ex) {
      logger.error( "JDBCConnection.prepareConnection()", ex );
      isClosed = true;
    }

    if ( isClosed ) {
      closeConnection();

      cachedConnection = dataSource.getConnection();
      cachedConnection.setAutoCommit( true );
    }

    return cachedConnection;
  }

  /**
   * Returns a new SQL <code>Statemant</code>
   * @return a new SQL <code>Statement</code>
   * @throws SQLException
   */
  public Statement createStatement() throws SQLException {
    return createConnection().createStatement();
  }
}
