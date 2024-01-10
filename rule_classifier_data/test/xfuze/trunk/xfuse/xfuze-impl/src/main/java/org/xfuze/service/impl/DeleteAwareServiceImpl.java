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
import org.xfuze.dao.DeleteAwareDao;
import org.xfuze.model.Model;
import org.xfuze.service.DeleteAwareService;
import org.xfuze.service.ServiceException;

/**
 * @author Jason Chan
 *
 */
public class DeleteAwareServiceImpl<M extends Model> extends ServiceImpl<M> implements DeleteAwareService<M> {
	private DeleteAwareDao<M> deleteAwareDao;

	@Required
	public void setDeleteAwareDao(DeleteAwareDao<M> deleteAwareDao) {
		this.deleteAwareDao = deleteAwareDao;
	}

	public M delete(M model) throws ServiceException {
		return deleteAwareDao.delete(model);
	}
}
