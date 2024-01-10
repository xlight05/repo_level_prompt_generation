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

import java.io.Serializable;
import java.util.List;

import org.xfuze.model.Model;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public interface ReadAwareService<M extends Model, K extends Serializable> extends Service {
	public M getByPrimaryKey(K key) throws ServiceException;

	public List<M> getByExample(Restriction restriction, Match match, M example) throws ServiceException;
}
