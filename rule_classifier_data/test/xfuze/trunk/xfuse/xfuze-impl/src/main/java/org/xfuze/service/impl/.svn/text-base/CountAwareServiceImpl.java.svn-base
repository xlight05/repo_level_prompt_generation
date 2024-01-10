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

import org.springframework.beans.factory.annotation.Required;
import org.xfuze.dao.CountAwareDao;
import org.xfuze.model.Model;
import org.xfuze.service.CountAwareService;
import org.xfuze.service.ServiceException;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public class CountAwareServiceImpl<M extends Model> extends ServiceImpl<M> implements CountAwareService<M> {
	private CountAwareDao<M> countAwareDao;

	@Required
	public void setCountAwareDao(CountAwareDao<M> countAwareDao) {
		this.countAwareDao = countAwareDao;
	}

	public int getCount() throws ServiceException {
		return countAwareDao.getCount();
	}

	public int getCount(Restriction restriction, Match match, M example) throws ServiceException {
		return countAwareDao.getCount(restriction, match, example);
	}
}
