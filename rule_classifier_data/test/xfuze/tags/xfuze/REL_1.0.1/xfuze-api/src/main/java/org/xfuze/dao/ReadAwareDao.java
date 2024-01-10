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

import java.io.Serializable;
import java.util.List;

import org.xfuze.model.Model;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public interface ReadAwareDao<M extends Model, K extends Serializable> extends Dao {
	/**
	 * Retrieve the record with the given primary key.
	 *
	 * @param key
	 *            the primary key for lookup.
	 * @return the matched record; null if no record found.
	 * @throws DaoException
	 *             if failed to retrieve.
	 */
	public M getByPrimaryKey(K key) throws DaoException;

	/**
	 * Retrieve the list of records with the given restriction, match mode and example.
	 *
	 * @param restriction
	 *            the restriction.
	 * @param match
	 *            the match mode.
	 * @param example
	 *            the example.
	 * @return the list of records with the given restriction, match mode and example; empty list if no record found.
	 * @throws DaoException
	 *             if failed to retrieve.
	 */
	public List<M> getByExample(Restriction restriction, Match match, M example) throws DaoException;
}
