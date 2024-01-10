package org.jsemantic.services.dbservice;

import java.sql.ResultSet;

import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.services.dbservice.exception.DBServerException;
import org.jsemantic.services.dbservice.impl.DBServerConfiguration;

public interface DBServer extends Service {
	/**
	 * 
	 * @param isMemoryDBServer
	 */
	public void setMemoryDBServer(boolean isMemoryDBServer);

	/**
	 * 
	 * @return
	 */
	public boolean isMemoryDBServer();
	/**
	 * 
	 * @param dbServerConfiguration
	 */
	public void setDbServerConfiguration(
			DBServerConfiguration dbServerConfiguration);
	/**
	 * 
	 * @return
	 */
	public DBServerConfiguration getDbServerConfiguration();

	/**
	 * 
	 * @param pExpression
	 * @return
	 * @throws Throwable
	 */
	public ResultSet executeQuery(String pExpression) throws DBServerException;;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws Throwable
	 */
	public int executeUpdate(String query) throws DBServerException;;

}
