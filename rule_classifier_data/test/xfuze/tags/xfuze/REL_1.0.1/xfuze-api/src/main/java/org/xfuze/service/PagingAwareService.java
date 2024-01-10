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

package org.xfuze.service;

import java.util.List;

import org.xfuze.model.PagingAwareModel;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public interface PagingAwareService<M extends PagingAwareModel> extends CountAwareService<M> {
	/**
	 * Retrieve list of paginated model in the given page number and page size.
	 *
	 * @param pageNumber
	 *            the page number, start from 0.
	 * @param pageSize
	 *            the page size (must > 0).
	 * @return list of model or empty list if not found.
	 * @throws ServiceException
	 *             if failed to retrieve.
	 */
	public List<M> getPaginatedList(int pageNumber, int pageSize) throws ServiceException;

	/**
	 * Retrieve list of paginated model in the given restriction, match mode, example, page number and page size.
	 *
	 * @param restriction
	 *            the restriction.
	 * @param match
	 *            the match mode.
	 * @param example
	 *            the example
	 * @param pageNumber
	 *            the page number, start from 0.
	 * @param pageSize
	 *            the page size (must > 0).
	 * @return list of model or empty list if not found.
	 * @throws ServiceException
	 *             if failed to retrieve.
	 */
	public List<M> getPaginatedList(Restriction restriction, Match match, M example, int pageNumber, int pageSize)
			throws ServiceException;
}
