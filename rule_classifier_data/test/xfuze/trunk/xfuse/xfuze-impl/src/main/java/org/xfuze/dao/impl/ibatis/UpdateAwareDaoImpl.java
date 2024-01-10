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

import org.xfuze.dao.DaoException;
import org.xfuze.dao.UpdateAwareDao;
import org.xfuze.dao.helper.ibatis.UpdateHelper;
import org.xfuze.model.Model;

/**
 * @author Jason Chan
 *
 */
public class UpdateAwareDaoImpl<M extends Model> extends DaoImpl<M> implements UpdateAwareDao<M> {
	protected UpdateHelper updateHelper;

	public UpdateAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	public void setUpdateHelper(UpdateHelper updateHelper) {
		this.updateHelper = updateHelper;
	}

	@SuppressWarnings("unchecked")
	public M update(M model) throws DaoException {
		return (M) updateHelper.update(modelName, model);
	}
}
