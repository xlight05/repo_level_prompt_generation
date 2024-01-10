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
import org.xfuze.dao.DaoException;
import org.xfuze.dao.DeleteAwareDao;
import org.xfuze.model.Model;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class DeleteAwareDaoImpl<M extends Model> extends DaoImpl<M> implements DeleteAwareDao<M> {
	/**
	 * Delete query name.
	 *
	 * Query: {0}.delete<br>
	 * Notes: 0 - model name<br>
	 * Example: User.delete
	 */
	protected static final String QUERY_DELETE = "{0}.delete";

	public DeleteAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	public M delete(M model) throws DaoException {
		try {
			String query = getQueryName(QUERY_DELETE, modelName);
			getSqlMapClientTemplate().delete(query, model);
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