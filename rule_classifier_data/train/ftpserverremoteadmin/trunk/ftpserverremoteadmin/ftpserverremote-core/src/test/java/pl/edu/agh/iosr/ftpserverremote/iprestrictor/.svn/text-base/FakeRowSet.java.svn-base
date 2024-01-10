package pl.edu.agh.iosr.ftpserverremote.iprestrictor;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import javax.sql.RowSet;
import javax.sql.RowSetListener;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class FakeRowSet implements RowSet {

  private final String data[][];
  private int index = 0;
  private final int size;

  public FakeRowSet(final String[][] data) {
    this.data = data;
    size = data.length;
  }

  public boolean last() {
    index = size;
    return true;
  }

  public void beforeFirst() {
    index = 0;
  }

  public int getRow() {
    return index;
  }

  public boolean next() {
    index++;
    return index <= size;
  }

  public String getString( final String column ) throws SQLException {
    if ( "IPPattern".equals( column ) ) {
      return data[index - 1][0];
    }
    if ( "Permission".equals( column ) ) {
      return data[index - 1][1];
    }
    throw new SQLException( "Wrong column" );
  }

  public void addRowSetListener( final RowSetListener listener ) {
  }

  public void clearParameters() throws SQLException {
  }

  public void execute() throws SQLException {
  }

  public String getCommand() {
    return null;
  }

  public String getDataSourceName() {
    return null;
  }

  public boolean getEscapeProcessing() throws SQLException {
    return false;
  }

  public int getMaxFieldSize() throws SQLException {
    return 0;
  }

  public int getMaxRows() throws SQLException {
    return 0;
  }

  public String getPassword() {
    return null;
  }

  public int getQueryTimeout() throws SQLException {
    return 0;
  }

  public int getTransactionIsolation() {
    return 0;
  }

  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return null;
  }

  public String getUrl() throws SQLException {
    return null;
  }

  public String getUsername() {
    return null;
  }

  public boolean isReadOnly() {
    return false;
  }

  public void removeRowSetListener( final RowSetListener listener ) {
  }

  public void setArray( final int i, final Array x ) throws SQLException {
  }

  public void setAsciiStream( final int parameterIndex, final InputStream x, final int length )
      throws SQLException
  {
  }

  public void setBigDecimal( final int parameterIndex, final BigDecimal x )
      throws SQLException
  {
  }

  public void setBinaryStream( final int parameterIndex, final InputStream x, final int length )
      throws SQLException
  {
  }

  public void setBlob( final int i, final Blob x ) throws SQLException {
  }

  public void setBoolean( final int parameterIndex, final boolean x ) throws SQLException {
  }

  public void setByte( final int parameterIndex, final byte x ) throws SQLException {
  }

  public void setBytes( final int parameterIndex, final byte[] x ) throws SQLException {
  }

  public void setCharacterStream( final int parameterIndex,
                                  final Reader reader,
                                  final int length ) throws SQLException
  {
  }

  public void setClob( final int i, final Clob x ) throws SQLException {
  }

  public void setCommand( final String cmd ) throws SQLException {
  }

  public void setConcurrency( final int concurrency ) throws SQLException {
  }

  public void setDataSourceName( final String name ) throws SQLException {
  }

  public void setDate( final int parameterIndex, final Date x ) throws SQLException {
  }

  public void setDate( final int parameterIndex, final Date x, final Calendar cal )
      throws SQLException
  {
  }

  public void setDouble( final int parameterIndex, final double x ) throws SQLException {
  }

  public void setEscapeProcessing( final boolean enable ) throws SQLException {
  }

  public void setFloat( final int parameterIndex, final float x ) throws SQLException {
  }

  public void setInt( final int parameterIndex, final int x ) throws SQLException {
  }

  public void setLong( final int parameterIndex, final long x ) throws SQLException {
  }

  public void setMaxFieldSize( final int max ) throws SQLException {
  }

  public void setMaxRows( final int max ) throws SQLException {
  }

  public void setNull( final int parameterIndex, final int sqlType ) throws SQLException {
  }

  public void setNull( final int paramIndex, final int sqlType, final String typeName )
      throws SQLException
  {
  }

  public void setObject( final int parameterIndex, final Object x ) throws SQLException {
  }

  public void setObject( final int parameterIndex, final Object x, final int targetSqlType )
      throws SQLException
  {
  }

  public void setObject( final int parameterIndex,
                         final Object x,
                         final int targetSqlType,
                         final int scale ) throws SQLException
  {
  }

  public void setPassword( final String password ) throws SQLException {
  }

  public void setQueryTimeout( final int seconds ) throws SQLException {
  }

  public void setReadOnly( final boolean value ) throws SQLException {
  }

  public void setRef( final int i, final Ref x ) throws SQLException {
  }

  public void setShort( final int parameterIndex, final short x ) throws SQLException {
  }

  public void setString( final int parameterIndex, final String x ) throws SQLException {
  }

  public void setTime( final int parameterIndex, final Time x ) throws SQLException {
  }

  public void setTime( final int parameterIndex, final Time x, final Calendar cal )
      throws SQLException
  {
  }

  public void setTimestamp( final int parameterIndex, final Timestamp x ) throws SQLException {
  }

  public void setTimestamp( final int parameterIndex, final Timestamp x, final Calendar cal )
      throws SQLException
  {
  }

  public void setTransactionIsolation( final int level ) throws SQLException {
  }

  public void setType( final int type ) throws SQLException {
  }

  public void setTypeMap( final Map<String, Class<?>> map ) throws SQLException {
  }

  public void setUrl( final String url ) throws SQLException {
  }

  public void setUsername( final String name ) throws SQLException {
  }

  public boolean absolute( final int row ) throws SQLException {
    return false;
  }

  public void afterLast() throws SQLException {
  }

  public void cancelRowUpdates() throws SQLException {
  }

  public void clearWarnings() throws SQLException {
  }

  public void close() throws SQLException {
  }

  public void deleteRow() throws SQLException {
  }

  public int findColumn( final String columnName ) throws SQLException {
    return 0;
  }

  public boolean first() throws SQLException {
    return false;
  }

  public Array getArray( final int i ) throws SQLException {
    return null;
  }

  public Array getArray( final String colName ) throws SQLException {
    return null;
  }

  public InputStream getAsciiStream( final int columnIndex ) throws SQLException {
    return null;
  }

  public InputStream getAsciiStream( final String columnName ) throws SQLException {
    return null;
  }

  public BigDecimal getBigDecimal( final int columnIndex ) throws SQLException {
    return null;
  }

  public BigDecimal getBigDecimal( final String columnName ) throws SQLException {
    return null;
  }

  public BigDecimal getBigDecimal( final int columnIndex, final int scale )
      throws SQLException
  {
    return null;
  }

  public BigDecimal getBigDecimal( final String columnName, final int scale )
      throws SQLException
  {
    return null;
  }

  public InputStream getBinaryStream( final int columnIndex ) throws SQLException {
    return null;
  }

  public InputStream getBinaryStream( final String columnName ) throws SQLException {
    return null;
  }

  public Blob getBlob( final int i ) throws SQLException {
    return null;
  }

  public Blob getBlob( final String colName ) throws SQLException {
    return null;
  }

  public boolean getBoolean( final int columnIndex ) throws SQLException {
    return false;
  }

  public boolean getBoolean( final String columnName ) throws SQLException {
    return false;
  }

  public byte getByte( final int columnIndex ) throws SQLException {
    return 0;
  }

  public byte getByte( final String columnName ) throws SQLException {
    return 0;
  }

  public byte[] getBytes( final int columnIndex ) throws SQLException {
    return null;
  }

  public byte[] getBytes( final String columnName ) throws SQLException {
    return null;
  }

  public Reader getCharacterStream( final int columnIndex ) throws SQLException {
    return null;
  }

  public Reader getCharacterStream( final String columnName ) throws SQLException {
    return null;
  }

  public Clob getClob( final int i ) throws SQLException {
    return null;
  }

  public Clob getClob( final String colName ) throws SQLException {
    return null;
  }

  public int getConcurrency() throws SQLException {
    return 0;
  }

  public String getCursorName() throws SQLException {
    return null;
  }

  public Date getDate( final int columnIndex ) throws SQLException {
    return null;
  }

  public Date getDate( final String columnName ) throws SQLException {
    return null;
  }

  public Date getDate( final int columnIndex, final Calendar cal ) throws SQLException {
    return null;
  }

  public Date getDate( final String columnName, final Calendar cal ) throws SQLException {
    return null;
  }

  public double getDouble( final int columnIndex ) throws SQLException {
    return 0;
  }

  public double getDouble( final String columnName ) throws SQLException {
    return 0;
  }

  public int getFetchDirection() throws SQLException {
    return 0;
  }

  public int getFetchSize() throws SQLException {
    return 0;
  }

  public float getFloat( final int columnIndex ) throws SQLException {
    return 0;
  }

  public float getFloat( final String columnName ) throws SQLException {
    return 0;
  }

  public int getInt( final int columnIndex ) throws SQLException {
    return 0;
  }

  public int getInt( final String columnName ) throws SQLException {
    return 0;
  }

  public long getLong( final int columnIndex ) throws SQLException {
    return 0;
  }

  public long getLong( final String columnName ) throws SQLException {
    return 0;
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    return null;
  }

  public Object getObject( final int columnIndex ) throws SQLException {
    return null;
  }

  public Object getObject( final String columnName ) throws SQLException {
    return null;
  }

  public Object getObject( final int i, final Map<String, Class<?>> map ) throws SQLException {
    return null;
  }

  public Object getObject( final String colName, final Map<String, Class<?>> map )
      throws SQLException
  {
    return null;
  }

  public Ref getRef( final int i ) throws SQLException {
    return null;
  }

  public Ref getRef( final String colName ) throws SQLException {
    return null;
  }

  public short getShort( final int columnIndex ) throws SQLException {
    return 0;
  }

  public short getShort( final String columnName ) throws SQLException {
    return 0;
  }

  public Statement getStatement() throws SQLException {
    return null;
  }

  public String getString( final int columnIndex ) throws SQLException {
    return null;
  }

  public Time getTime( final int columnIndex ) throws SQLException {
    return null;
  }

  public Time getTime( final String columnName ) throws SQLException {
    return null;
  }

  public Time getTime( final int columnIndex, final Calendar cal ) throws SQLException {
    return null;
  }

  public Time getTime( final String columnName, final Calendar cal ) throws SQLException {
    return null;
  }

  public Timestamp getTimestamp( final int columnIndex ) throws SQLException {
    return null;
  }

  public Timestamp getTimestamp( final String columnName ) throws SQLException {
    return null;
  }

  public Timestamp getTimestamp( final int columnIndex, final Calendar cal )
      throws SQLException
  {
    return null;
  }

  public Timestamp getTimestamp( final String columnName, final Calendar cal )
      throws SQLException
  {
    return null;
  }

  public int getType() throws SQLException {
    return 0;
  }

  public URL getURL( final int columnIndex ) throws SQLException {
    return null;
  }

  public URL getURL( final String columnName ) throws SQLException {
    return null;
  }

  public InputStream getUnicodeStream( final int columnIndex ) throws SQLException {
    return null;
  }

  public InputStream getUnicodeStream( final String columnName ) throws SQLException {
    return null;
  }

  public SQLWarning getWarnings() throws SQLException {
    return null;
  }

  public void insertRow() throws SQLException {
  }

  public boolean isAfterLast() throws SQLException {
    return false;
  }

  public boolean isBeforeFirst() throws SQLException {
    return false;
  }

  public boolean isFirst() throws SQLException {
    return false;
  }

  public boolean isLast() throws SQLException {
    return false;
  }

  public void moveToCurrentRow() throws SQLException {
  }

  public void moveToInsertRow() throws SQLException {
  }

  public boolean previous() throws SQLException {
    return false;
  }

  public void refreshRow() throws SQLException {
  }

  public boolean relative( final int rows ) throws SQLException {
    return false;
  }

  public boolean rowDeleted() throws SQLException {
    return false;
  }

  public boolean rowInserted() throws SQLException {
    return false;
  }

  public boolean rowUpdated() throws SQLException {
    return false;
  }

  public void setFetchDirection( final int direction ) throws SQLException {
  }

  public void setFetchSize( final int rows ) throws SQLException {
  }

  public void updateArray( final int columnIndex, final Array x ) throws SQLException {
  }

  public void updateArray( final String columnName, final Array x ) throws SQLException {
  }

  public void updateAsciiStream( final int columnIndex, final InputStream x, final int length )
      throws SQLException
  {
  }

  public void updateAsciiStream( final String columnName, final InputStream x, final int length )
      throws SQLException
  {
  }

  public void updateBigDecimal( final int columnIndex, final BigDecimal x )
      throws SQLException
  {
  }

  public void updateBigDecimal( final String columnName, final BigDecimal x )
      throws SQLException
  {
  }

  public void updateBinaryStream( final int columnIndex, final InputStream x, final int length )
      throws SQLException
  {
  }

  public void updateBinaryStream( final String columnName,
                                  final InputStream x,
                                  final int length ) throws SQLException
  {
  }

  public void updateBlob( final int columnIndex, final Blob x ) throws SQLException {
  }

  public void updateBlob( final String columnName, final Blob x ) throws SQLException {
  }

  public void updateBoolean( final int columnIndex, final boolean x ) throws SQLException {
  }

  public void updateBoolean( final String columnName, final boolean x ) throws SQLException {
  }

  public void updateByte( final int columnIndex, final byte x ) throws SQLException {
  }

  public void updateByte( final String columnName, final byte x ) throws SQLException {
  }

  public void updateBytes( final int columnIndex, final byte[] x ) throws SQLException {
  }

  public void updateBytes( final String columnName, final byte[] x ) throws SQLException {
  }

  public void updateCharacterStream( final int columnIndex, final Reader x, final int length )
      throws SQLException
  {
  }

  public void updateCharacterStream( final String columnName,
                                     final Reader reader,
                                     final int length ) throws SQLException
  {
  }

  public void updateClob( final int columnIndex, final Clob x ) throws SQLException {
  }

  public void updateClob( final String columnName, final Clob x ) throws SQLException {
  }

  public void updateDate( final int columnIndex, final Date x ) throws SQLException {
  }

  public void updateDate( final String columnName, final Date x ) throws SQLException {
  }

  public void updateDouble( final int columnIndex, final double x ) throws SQLException {
  }

  public void updateDouble( final String columnName, final double x ) throws SQLException {
  }

  public void updateFloat( final int columnIndex, final float x ) throws SQLException {
  }

  public void updateFloat( final String columnName, final float x ) throws SQLException {
  }

  public void updateInt( final int columnIndex, final int x ) throws SQLException {
  }

  public void updateInt( final String columnName, final int x ) throws SQLException {
  }

  public void updateLong( final int columnIndex, final long x ) throws SQLException {
  }

  public void updateLong( final String columnName, final long x ) throws SQLException {
  }

  public void updateNull( final int columnIndex ) throws SQLException {
  }

  public void updateNull( final String columnName ) throws SQLException {
  }

  public void updateObject( final int columnIndex, final Object x ) throws SQLException {
  }

  public void updateObject( final String columnName, final Object x ) throws SQLException {
  }

  public void updateObject( final int columnIndex, final Object x, final int scale )
      throws SQLException
  {
  }

  public void updateObject( final String columnName, final Object x, final int scale )
      throws SQLException
  {
  }

  public void updateRef( final int columnIndex, final Ref x ) throws SQLException {
  }

  public void updateRef( final String columnName, final Ref x ) throws SQLException {
  }

  public void updateRow() throws SQLException {
  }

  public void updateShort( final int columnIndex, final short x ) throws SQLException {
  }

  public void updateShort( final String columnName, final short x ) throws SQLException {
  }

  public void updateString( final int columnIndex, final String x ) throws SQLException {
  }

  public void updateString( final String columnName, final String x ) throws SQLException {
  }

  public void updateTime( final int columnIndex, final Time x ) throws SQLException {
  }

  public void updateTime( final String columnName, final Time x ) throws SQLException {
  }

  public void updateTimestamp( final int columnIndex, final Timestamp x ) throws SQLException {
  }

  public void updateTimestamp( final String columnName, final Timestamp x )
      throws SQLException
  {
  }

  public boolean wasNull() throws SQLException {
    return false;
  }
}
