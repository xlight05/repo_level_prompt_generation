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

import java.util.List;

import org.xfuze.dao.AuditAwareDao;
import org.xfuze.dao.DaoException;
import org.xfuze.dao.helper.ibatis.CreateHelper;
import org.xfuze.dao.helper.ibatis.ReadHelper;
import org.xfuze.model.AuditAwareModel;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public class AuditAwareDaoImpl<M extends AuditAwareModel> extends DaoImpl<M> implements AuditAwareDao<M> {
	protected CreateHelper createHelper;
	protected ReadHelper readHelper;

	public AuditAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	// ---------- start of injection ----------
	public void setCreateHelper(CreateHelper createHelper) {
		this.createHelper = createHelper;
	}

	public void setReadHelper(ReadHelper readHelper) {
		this.readHelper = readHelper;
	}

	// ---------- end of injection ----------

	@SuppressWarnings("unchecked")
	public M insert(M model) throws DaoException {
		return (M) createHelper.insert(modelName, model);
	}

	@SuppressWarnings("unchecked")
	public List<M> getByExample(Restriction restriction, Match match, M example) throws DaoException {
		return (List<M>) readHelper.getByExample(restriction, match, modelName, example);
	}
}
