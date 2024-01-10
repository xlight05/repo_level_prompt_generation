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

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.xfuze.dao.Dao;
import org.xfuze.model.Model;

/**
 * @author Jason Chan
 *
 */
public abstract class DaoImpl<M extends Model> extends SqlMapClientDaoSupport implements Dao {
	protected final transient Logger logger = LoggerFactory.getLogger(DaoImpl.class);

	protected Class<M> persistentClass;
	protected String modelName;

	public DaoImpl(final Class<M> persistentClass) {
		this.persistentClass = persistentClass;
		this.modelName = persistentClass.getSimpleName();
	}

	protected String getQueryName(String pattern, Object... parameters) {
		return MessageFormat.format(pattern, parameters);
	}
}
