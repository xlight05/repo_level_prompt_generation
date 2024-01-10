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
public class SqlMapField {
	private String jdbcType;
	private String javaFieldName;
	private String databaseFieldType;
	private String databaseFieldName;

	private boolean exactMatch = false;
	private boolean likeMatch = false;

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJavaFieldName() {
		return javaFieldName;
	}

	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}

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

	public boolean isExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(boolean exactMatch) {
		this.exactMatch = exactMatch;
	}

	public boolean isLikeMatch() {
		return likeMatch;
	}

	public void setLikeMatch(boolean likeMatch) {
		this.likeMatch = likeMatch;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SqlMapField)) {
			return false;
		}
		SqlMapField rhs = (SqlMapField) object;
		return new EqualsBuilder().append(this.databaseFieldName, rhs.databaseFieldName).isEquals();
	}
}
