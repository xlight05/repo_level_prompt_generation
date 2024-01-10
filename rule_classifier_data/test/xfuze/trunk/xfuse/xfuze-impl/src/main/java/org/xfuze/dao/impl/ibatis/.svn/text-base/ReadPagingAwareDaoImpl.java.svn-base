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
import java.util.List;

import org.xfuze.dao.DaoException;
import org.xfuze.dao.PagingAwareDao;
import org.xfuze.dao.ReadAwareDao;
import org.xfuze.dao.helper.ibatis.PagingHelper;
import org.xfuze.dao.helper.ibatis.ReadHelper;
import org.xfuze.model.PagingAwareModel;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public class ReadPagingAwareDaoImpl<M extends PagingAwareModel, K extends Serializable> extends DaoImpl<M> implements
		ReadAwareDao<M, K>, PagingAwareDao<M> {
	protected ReadHelper readHelper;
	protected PagingHelper pagingHelper;

	public ReadPagingAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	// ---------- start of injection ----------
	public void setReadHelper(ReadHelper readHelper) {
		this.readHelper = readHelper;
	}

	public void setPagingHelper(PagingHelper pagingHelper) {
		this.pagingHelper = pagingHelper;
	}

	// ---------- end of injection ----------

	@SuppressWarnings("unchecked")
	public M getByPrimaryKey(K key) throws DaoException {
		return (M) readHelper.getByPrimaryKey(modelName, key);
	}

	@SuppressWarnings("unchecked")
	public List<M> getByExample(Restriction restriction, Match match, M example) throws DaoException {
		return (List<M>) readHelper.getByExample(restriction, match, modelName, example);
	}

	// ---------- start of paging ----------
	public int getCount() throws DaoException {
		return pagingHelper.getCount(modelName);
	}

	public int getCount(Restriction restriction, Match match, M example) throws DaoException {
		return pagingHelper.getCount(restriction, match, modelName, example);
	}

	@SuppressWarnings("unchecked")
	public List<M> getPaginatedList(int pageNumber, int pageSize) throws DaoException {
		return (List<M>) pagingHelper.getPaginatedList((Class<PagingAwareModel>) persistentClass, modelName,
				pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<M> getPaginatedList(Restriction restriction, Match match, M example, int pageNumber, int pageSize)
			throws DaoException {
		return (List<M>) pagingHelper.getPaginatedList(restriction, match, (Class<PagingAwareModel>) persistentClass,
				modelName, example, pageNumber, pageSize);
	}

	// ---------- end of paging ----------
}
