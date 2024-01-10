package pl.edu.agh.iosr.ftpserverremote.iprestrictor;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class FakeDataSource implements DataSource {

  private final String[][] data;

  public FakeDataSource(final String[][] data) {
    this.data = data;
  }

  public Connection getConnection() throws SQLException {
    return new FakeConnection( data );
  }

  public Connection getConnection( final String username, final String password )
      throws SQLException
  {
    return null;
  }

  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }

  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  public void setLogWriter( final PrintWriter out ) throws SQLException {

  }

  public void setLoginTimeout( final int seconds ) throws SQLException {

  }

  public <T> T unwrap( final Class<T> iface ) throws SQLException {
    throw new UnsupportedOperationException( "Not supported yet." );
  }

  public boolean isWrapperFor( final Class<?> iface ) throws SQLException {
    throw new UnsupportedOperationException( "Not supported yet." );
  }

}
