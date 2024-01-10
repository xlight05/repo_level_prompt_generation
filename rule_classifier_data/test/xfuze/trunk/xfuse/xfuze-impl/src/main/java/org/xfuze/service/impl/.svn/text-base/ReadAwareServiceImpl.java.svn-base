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

package org.xfuze.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.xfuze.dao.ReadAwareDao;
import org.xfuze.model.Model;
import org.xfuze.service.ReadAwareService;
import org.xfuze.service.ServiceException;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public class ReadAwareServiceImpl<M extends Model, K extends Serializable> extends ServiceImpl<M> implements
		ReadAwareService<M, K> {
	private ReadAwareDao<M, K> readAwareDao;

	@Required
	public void setReadAwareDao(ReadAwareDao<M, K> readAwareDao) {
		this.readAwareDao = readAwareDao;
	}

	public M getByPrimaryKey(K key) throws ServiceException {
		return readAwareDao.getByPrimaryKey(key);
	}

	public List<M> getByExample(Restriction restriction, Match match, M example) throws ServiceException {
		return readAwareDao.getByExample(restriction, match, example);
	}
}
