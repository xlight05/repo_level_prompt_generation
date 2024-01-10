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
public class ModelField {
	private String fieldType;
	private String fieldName;

	// for char or date type this is the maximum number of characters; for numeric or decimal types this is precision
	private int columnSize;
	// the number of fractional digits
	private int decimalDigit;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getDecimalDigit() {
		return decimalDigit;
	}

	public void setDecimalDigit(int decimalDigit) {
		this.decimalDigit = decimalDigit;
	}

	public String getPropertyName() {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ModelField)) {
			return false;
		}
		ModelField rhs = (ModelField) object;
		return new EqualsBuilder().append(this.fieldName, rhs.fieldName).isEquals();
	}
}
