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

package org.xfuze.tool.model;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Jason Chan
 *
 */
public class SchemaField {
	private String databaseFieldType;
	private String databaseFieldName;
	private String nullable;

	private String foreignKeyTableName;
	private String foreignKeyFieldName;

	public String getDatabaseFieldType() {
		return databaseFieldType;
	}

	public void setDatabaseFieldType(String databaseFieldType) {
		this.databaseFieldType = databaseFieldType.toLowerCase();
	}

	public String getDatabaseFieldName() {
		return databaseFieldName;
	}

	public void setDatabaseFieldName(String databaseFieldName) {
		this.databaseFieldName = databaseFieldName.toLowerCase();
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getForeignKeyFieldName() {
		return foreignKeyFieldName;
	}

	public void setForeignKeyFieldName(String foreignKeyFieldName) {
		this.foreignKeyFieldName = foreignKeyFieldName;
	}

	public String getForeignKeyTableName() {
		return foreignKeyTableName;
	}

	public void setForeignKeyTableName(String foreignKeyTableName) {
		this.foreignKeyTableName = foreignKeyTableName;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SchemaField)) {
			return false;
		}
		SchemaField rhs = (SchemaField) object;
		return new EqualsBuilder().append(this.databaseFieldName, rhs.databaseFieldName).isEquals();
	}
}
