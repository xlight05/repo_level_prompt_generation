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

import org.xfuze.dao.CreateAwareDao;
import org.xfuze.dao.DaoException;
import org.xfuze.dao.DeleteAwareDao;
import org.xfuze.dao.helper.ibatis.CreateHelper;
import org.xfuze.dao.helper.ibatis.DeleteHelper;
import org.xfuze.model.Model;

/**
 * @author Jason Chan
 *
 */
public class CreateDeleteAwareDaoImpl<M extends Model> extends DaoImpl<M> implements CreateAwareDao<M>,
		DeleteAwareDao<M> {
	protected CreateHelper createHelper;
	protected DeleteHelper deleteHelper;

	public CreateDeleteAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	// ---------- start of injection ----------
	public void setCreateHelper(CreateHelper createHelper) {
		this.createHelper = createHelper;
	}

	public void setDeleteHelper(DeleteHelper deleteHelper) {
		this.deleteHelper = deleteHelper;
	}

	// ---------- end of injection ----------

	@SuppressWarnings("unchecked")
	public M insert(M model) throws DaoException {
		return (M) createHelper.insert(modelName, model);
	}

	@SuppressWarnings("unchecked")
	public M delete(M model) throws DaoException {
		return (M) deleteHelper.delete(modelName, model);
	}
}