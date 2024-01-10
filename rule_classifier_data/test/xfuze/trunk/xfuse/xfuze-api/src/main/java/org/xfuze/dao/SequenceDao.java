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

/**
 * @author Jason Chan
 *
 */
public interface SequenceDao extends Dao {
	/**
	 * Retrieve the current sequence no. (ID) from the sequence table (sequence_tb).
	 *
	 * @param modelName
	 *            the model to lookup.
	 * @return the current sequence no. (ID) from the sequence table (sequence_tb).
	 * @throws DaoException
	 *             if failed to retrieve.
	 */
	public Long getCurrentId(String modelName) throws DaoException;

	/**
	 * Retrieve the next sequence no. (ID) from the sequence table (sequence_tb).
	 *
	 * @param modelName
	 *            the model to lookup.
	 * @return the next sequence no. (ID) from the sequence table (sequence_tb).
	 * @throws DaoException
	 *             if failed to retrieve.
	 */
	public Long getNextId(String modelName) throws DaoException;
}
