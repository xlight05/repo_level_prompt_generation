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

import org.springframework.dao.DataAccessException;
import org.xfuze.dao.DaoException;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class UpdateHelper extends HelperSupport {
	/**
	 * Update query name.
	 *
	 * Query: {0}.update<br>
	 * Notes: 0 - model name<br>
	 * Example: User.update
	 */
	protected static final String QUERY_UPDATE = "{0}.update";

	public Serializable update(String modelName, Serializable model) throws DaoException {
		try {
			String query = getQueryName(QUERY_UPDATE, modelName);
			getSqlMapClientTemplate().update(query, model);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (SqlMapException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return model;
	}
}
