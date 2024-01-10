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

package org.xfuze.dao;

import org.xfuze.model.Model;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public interface CountAwareDao<M extends Model> extends Dao {
	/**
	 * Count the total no. of record.
	 *
	 * @return total no. of record; 0 if no record found.
	 * @throws DaoException
	 *             if failed to count.
	 */
	public int getCount() throws DaoException;

	/**
	 * Count the no. of record with the given restriction, match mode and example.
	 *
	 * @param restriction
	 *            the restriction.
	 * @param match
	 *            the match mode.
	 * @param example
	 *            the example
	 * @return no. of record; 0 if no record found.
	 * @throws DaoException
	 *             if failed to count.
	 */
	public int getCount(Restriction restriction, Match match, M example) throws DaoException;
}
