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

package org.xfuze.service.helper;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xfuze.lang.ChangeType;
import org.xfuze.model.AuditAwareModel;
import org.xfuze.model.TraceAwareModel;
import org.xfuze.util.ObjectUtils;

/**
 * @author Jason Chan
 *
 */
public class AuditHelper<M extends TraceAwareModel, A extends AuditAwareModel> {
	private final transient Logger logger = LoggerFactory.getLogger(AuditHelper.class);

	/**
	 * Save the audit log into database. Should call within transaction.
	 *
	 * @param changeType
	 *            the change type
	 * @param updatedBy
	 *            the last updated user
	 * @param updatedDate
	 *            the change time
	 * @param persistentClass
	 *            the audit model
	 * @param oldModel
	 *            the old model
	 * @param newModel
	 *            the new model
	 * @param primaryKeyFieldNames
	 *            the field name(s) for primary key
	 * @param ignoreFieldNames
	 *            the field name(s) to be ignored
	 * @return the AuditAwareModel; null if failed to create
	 */
	public A getAuditAwareModel(ChangeType changeType, String updatedBy, Date updatedDate, Class<A> persistentClass,
			M oldModel, M newModel, List<String> primaryKeyFieldNames, List<String> ignoreFieldNames) {
		A result = null;

		try {
			result = persistentClass.newInstance();
			result.setChangeTime(updatedDate);
			result.setChangeType(changeType);

			if (ChangeType.INSERT == changeType) {
				if (newModel == null) {
					throw new IllegalArgumentException("'newModel' is required for INSERT operation.");
				}

				ObjectUtils.copyProperties(newModel, result, null, "new", primaryKeyFieldNames, ignoreFieldNames);
			} else if (ChangeType.UPDATE == changeType) {
				if (oldModel == null) {
					throw new IllegalArgumentException("'oldModel' is required for UPDATE operation.");
				}
				if (newModel == null) {
					throw new IllegalArgumentException("'newModel' is required for UPDATE operation.");
				}

				ObjectUtils.copyProperties(oldModel, result, null, "old", primaryKeyFieldNames, ignoreFieldNames);
				ObjectUtils.copyProperties(newModel, result, null, "new", primaryKeyFieldNames, ignoreFieldNames);
			} else if (ChangeType.DELETE == changeType) {
				if (oldModel == null) {
					throw new IllegalArgumentException("'oldModel' is required for DELETE operation.");
				}

				result.setNewUpdatedBy(updatedBy);
				result.setNewUpdatedDate(updatedDate);

				ObjectUtils.copyProperties(oldModel, result, null, "old", primaryKeyFieldNames, ignoreFieldNames);
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}

		return result;
	}
}
