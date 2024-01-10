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

import org.springframework.dao.DataAccessException;
import org.xfuze.dao.CreateAwareDao;
import org.xfuze.dao.DaoException;
import org.xfuze.model.Model;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class CreateAwareDaoImpl<M extends Model> extends DaoImpl<M> implements CreateAwareDao<M> {
	/**
	 * Insert query name.
	 *
	 * Query: {0}.insert<br>
	 * Notes: 0 - model name<br>
	 * Example: User.insert
	 */
	protected static final String QUERY_INSERT = "{0}.insert";

	public CreateAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	public M insert(M model) throws DaoException {
		try {
			String query = getQueryName(QUERY_INSERT, modelName);
			getSqlMapClientTemplate().insert(query, model);
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