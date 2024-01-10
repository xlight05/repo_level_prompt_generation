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

import org.xfuze.dao.CountAwareDao;
import org.xfuze.dao.DaoException;
import org.xfuze.dao.helper.ibatis.CountHelper;
import org.xfuze.model.Model;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public class CountAwareDaoImpl<M extends Model> extends DaoImpl<M> implements CountAwareDao<M> {
	protected CountHelper countHelper;

	public CountAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	public void setCountHelper(CountHelper countHelper) {
		this.countHelper = countHelper;
	}

	public int getCount() throws DaoException {
		return countHelper.getCount(modelName);
	}

	public int getCount(Restriction restriction, Match match, M example) throws DaoException {
		return countHelper.getCount(restriction, match, modelName, example);
	}
}
