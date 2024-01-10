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

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.xfuze.dao.DaoException;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class CountHelper extends HelperSupport {
	/**
	 * Count by criteria query name (Conjunction, Anywhere).
	 *
	 * Query: {0}.selectCountConjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectCountConjunctionAnywhere
	 */
	protected static final String QUERY_GET_COUNT_CONJUNCTION_ANYWHERE = "{0}.selectCountConjunctionAnywhere";

	/**
	 * Count by criteria query name (Conjunction, Exact).
	 *
	 * Query: {0}.selectCountConjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectCountConjunctionExact
	 */
	protected static final String QUERY_GET_COUNT_CONJUNCTION_EXACT = "{0}.selectCountConjunctionExact";

	/**
	 * Count by criteria query name (Disjunction, Anywhere).
	 *
	 * Query: {0}.selectCountDisjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectCountDisjunctionAnywhere
	 */
	protected static final String QUERY_GET_COUNT_DISJUNCTION_ANYWHERE = "{0}.selectCountDisjunctionAnywhere";

	/**
	 * Count by criteria query name (Disjunction, Exact).
	 *
	 * Query: {0}.selectCountDisjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectCountDisjunctionExact
	 */
	protected static final String QUERY_GET_COUNT_DISJUNCTION_EXACT = "{0}.selectCountDisjunctionExact";

	public int getCount(String modelName) throws DaoException {
		return getCount(Restriction.AND, Match.EXACT, modelName, null);
	}

	public int getCount(Restriction restriction, Match match, String modelName, Serializable example)
			throws DaoException {
		String query = null;
		if (Restriction.AND == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_COUNT_CONJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_COUNT_CONJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else if (Restriction.OR == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_COUNT_DISJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_COUNT_DISJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else {
			logger.error("Unknown restriction [" + restriction + "].");
		}

		Integer result = null;
		if (StringUtils.isNotBlank(query)) {
			try {
				if (example == null) {
					result = (Integer) getSqlMapClientTemplate().queryForObject(query);
				} else {
					result = (Integer) getSqlMapClientTemplate().queryForObject(query, example);
				}
			} catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				throw e;
			} catch (SqlMapException e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}

		if (result == null) {
			result = 0;
		}
		return result;
	}
}
