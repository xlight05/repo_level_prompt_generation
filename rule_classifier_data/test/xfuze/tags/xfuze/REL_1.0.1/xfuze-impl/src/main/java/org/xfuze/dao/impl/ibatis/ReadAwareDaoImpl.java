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

package org.xfuze.dao.impl.ibatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.xfuze.dao.DaoException;
import org.xfuze.dao.ReadAwareDao;
import org.xfuze.model.Model;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class ReadAwareDaoImpl<M extends Model, K extends Serializable> extends DaoImpl<M> implements ReadAwareDao<M, K> {
	/**
	 * Read by primary key query name.
	 *
	 * Query: {0}.selectByPrimaryKey<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectByPrimaryKey
	 */
	protected static final String QUERY_GET_BY_PRIMARY_KEY = "{0}.selectByPrimaryKey";

	/**
	 * Read by criteria query name (Conjunction, Anywhere).
	 *
	 * Query: {0}.selectConjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectConjunctionAnywhere
	 */
	protected static final String QUERY_GET_CONJUNCTION_ANYWHERE = "{0}.selectConjunctionAnywhere";

	/**
	 * Read by criteria query name (Conjunction, Exact).
	 *
	 * Query: {0}.selectConjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectConjunctionExact
	 */
	protected static final String QUERY_GET_CONJUNCTION_EXACT = "{0}.selectConjunctionExact";

	/**
	 * Read by criteria query name (Disjunction, Anywhere).
	 *
	 * Query: {0}.selectDisjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectDisjunctionAnywhere
	 */
	protected static final String QUERY_GET_DISJUNCTION_ANYWHERE = "{0}.selectDisjunctionAnywhere";

	/**
	 * Read by criteria query name (Disjunction, Exact).
	 *
	 * Query: {0}.selectDisjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectDisjunctionExact
	 */
	protected static final String QUERY_GET_DISJUNCTION_EXACT = "{0}.selectDisjunctionExact";

	public ReadAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	public M getByPrimaryKey(K key) throws DaoException {
		M model = null;

		try {
			String query = getQueryName(QUERY_GET_BY_PRIMARY_KEY, modelName);
			model = (M) getSqlMapClientTemplate().queryForObject(query, key);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (SqlMapException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return model;
	}

	@SuppressWarnings("unchecked")
	public List<M> getByExample(Restriction restriction, Match match, M example) throws DaoException {
		String query = null;
		if (Restriction.AND == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_CONJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_CONJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else if (Restriction.OR == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_DISJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_DISJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else {
			logger.error("Unknown restriction [" + restriction + "].");
		}

		List<M> list = null;
		if (StringUtils.isNotBlank(query)) {
			try {
				if (example == null) {
					list = (List<M>) getSqlMapClientTemplate().queryForList(query);
				} else {
					list = (List<M>) getSqlMapClientTemplate().queryForList(query, example);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				throw e;
			} catch (SqlMapException e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}

		if (list == null) {
			list = new ArrayList<M>();
		}

		return list;
	}
}
