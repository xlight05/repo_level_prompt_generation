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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.xfuze.dao.DaoException;
import org.xfuze.dao.SequenceDao;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class SequenceDaoImpl extends SqlMapClientDaoSupport implements SequenceDao {
	protected final transient Logger logger = LoggerFactory.getLogger(SequenceDaoImpl.class);

	/**
	 * Read current ID query name.
	 *
	 * Query: Sequence.getCurrentId<br>
	 */
	protected static final String QUERY_GET_CURRENT_ID = "Sequence.getCurrentId";

	/**
	 * Update current ID query name.
	 *
	 * Query: Sequence.updateCurrentId<br>
	 */
	protected static final String QUERY_UPDATE_CURRENT_ID = "Sequence.updateCurrentId";

	public Long getCurrentId(String modelName) throws DaoException {
		Long result = null;

		try {
			result = (Long) getSqlMapClientTemplate().queryForObject(QUERY_GET_CURRENT_ID, modelName);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (SqlMapException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		if (result == null) {
			result = new Long(0);
		}
		return result;
	}

	public Long getNextId(String modelName) throws DaoException {
		Long result = null;

		try {
			int count = getSqlMapClientTemplate().update(QUERY_UPDATE_CURRENT_ID, modelName);
			logger.trace("Affected {} row(s).", count);
			result = (Long) getSqlMapClientTemplate().queryForObject(QUERY_GET_CURRENT_ID, modelName);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (SqlMapException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		if (result == null) {
			result = new Long(0);
		}
		return result;
	}
}
