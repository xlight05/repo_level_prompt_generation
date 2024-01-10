/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xfuze.dao.helper.ibatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataAccessException;
import org.xfuze.dao.DaoException;
import org.xfuze.model.PagingAwareModel;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class PagingHelper extends CountHelper {
	public static final String DATABASE_MSSQL_2000 = "MSSQL_2000";
	public static final String DATABASE_MYSQL = "MYSQL";

	/**
	 * Read page by criteria query name (Conjunction, Anywhere).
	 *
	 * Query: {0}.selectPageConjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageConjunctionAnywhere
	 */
	protected static final String QUERY_GET_PAGE_CONJUNCTION_ANYWHERE = "{0}.selectPageConjunctionAnywhere";

	/**
	 * Read page by criteria query name (Conjunction, Exact).
	 *
	 * Query: {0}.selectPageConjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageConjunctionExact
	 */
	protected static final String QUERY_GET_PAGE_CONJUNCTION_EXACT = "{0}.selectPageConjunctionExact";

	/**
	 * Read page by criteria query name (Disjunction, Anywhere).
	 *
	 * Query: {0}.selectPageDisjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageDisjunctionAnywhere
	 */
	protected static final String QUERY_GET_PAGE_DISJUNCTION_ANYWHERE = "{0}.selectPageDisjunctionAnywhere";

	/**
	 * Read page by criteria query name (Disjunction, Exact).
	 *
	 * Query: {0}.selectPageDisjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageDisjunctionExact
	 */
	protected static final String QUERY_GET_PAGE_DISJUNCTION_EXACT = "{0}.selectPageDisjunctionExact";

	private String database;

	public PagingHelper(String database) {
		if (DATABASE_MSSQL_2000.equals(database) || DATABASE_MYSQL.equals(database)) {
			this.database = database;
			logger.info("Paging enabled for '{}'.", database);
		} else {
			throw new BeanCreationException("Database '" + database + "' not supported.");
		}
	}

	public List<Serializable> getPaginatedList(Class<PagingAwareModel> persistentClass, String modelName,
			int pageNumber, int pageSize) {
		return getPaginatedList(Restriction.AND, Match.EXACT, persistentClass, modelName, null, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Serializable> getPaginatedList(Restriction restriction, Match match,
			Class<PagingAwareModel> persistentClass, String modelName, PagingAwareModel example, int pageNumber,
			int pageSize) throws DaoException {
		if (logger.isTraceEnabled()) {
			logger.trace(">>>>>-------------------------------------------------");
			logger.trace("Received restriction: {}", restriction);
			logger.trace("Received match      : {}", match);
			logger.trace("Received example    : {}", example);
			logger.trace("Received pageNumber : {}", pageNumber);
			logger.trace("Received pageSize   : {}", pageSize);
			logger.trace("-------------------------------------------------<<<<<");
		}

		String query = null;
		if (Restriction.AND == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_PAGE_CONJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_PAGE_CONJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else if (Restriction.OR == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_PAGE_DISJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_PAGE_DISJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else {
			logger.error("Unknown restriction [" + restriction + "].");
		}

		List<Serializable> list = null;
		if (StringUtils.isNotBlank(query)) {
			if (example == null) {
				try {
					int count = getCount(modelName);
					int quotient = count / pageSize;
					int remainder = count % pageSize;

					PagingAwareModel eg = persistentClass.newInstance();
					if (DATABASE_MSSQL_2000.equals(database)) {
						eg.setPage((1 + pageNumber) * pageSize);
					} else if (DATABASE_MYSQL.equals(database)) {
						eg.setPage(pageNumber * pageSize);
					}
					if (pageNumber < quotient) {
						eg.setPageSize(pageSize);
					} else {
						eg.setPageSize(remainder);
					}

					list = (List<Serializable>) getSqlMapClientTemplate().queryForList(query, eg);
				} catch (InstantiationException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (DataAccessException e) {
					logger.error(e.getMessage(), e);
					throw e;
				} catch (SqlMapException e) {
					logger.error(e.getMessage(), e);
					throw e;
				}
			} else {
				int count = getCount(restriction, match, modelName, example);
				int quotient = count / pageSize;
				int remainder = count % pageSize;

				if (DATABASE_MSSQL_2000.equals(database)) {
					example.setPage((1 + pageNumber) * pageSize);
				} else if (DATABASE_MYSQL.equals(database)) {
					example.setPage(pageNumber * pageSize);
				}
				if (pageNumber < quotient) {
					example.setPageSize(pageSize);
				} else {
					example.setPageSize(remainder);
				}

				try {
					list = (List<Serializable>) getSqlMapClientTemplate().queryForList(query, example);
				} catch (DataAccessException e) {
					logger.error(e.getMessage(), e);
					throw e;
				} catch (SqlMapException e) {
					logger.error(e.getMessage(), e);
					throw e;
				}
			}
		}

		if (list == null) {
			list = new ArrayList<Serializable>();
		}

		return list;
	}
}
