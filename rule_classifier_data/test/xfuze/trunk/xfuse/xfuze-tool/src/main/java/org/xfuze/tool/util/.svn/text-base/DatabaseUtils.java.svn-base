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

package org.xfuze.tool.util;

import java.sql.DatabaseMetaData;
import java.sql.Types;

/**
 * @author Jason Chan
 *
 */
public final class DatabaseUtils {
	private DatabaseUtils() {
	}

	/**
	 * Generate database field type (with size, if available).
	 *
	 * @param jdbcType
	 *            the JDBC type.
	 * @param databaseFieldType
	 *            the database field type.
	 * @param columnSize
	 *            the column size.
	 * @param decimalDigits
	 *            the no. of decimal digits.
	 * @return the database field type (with size, if available).
	 */
	public static String getDatabaseFieldType(int jdbcType, String databaseFieldType, int columnSize, int decimalDigits) {
		String fieldType = databaseFieldType;

		if (hasSize(jdbcType)) {
			fieldType = fieldType + "(" + columnSize + ")";
		} else if (hasPrecisionAndScale(jdbcType)) {
			fieldType = fieldType + "(" + columnSize + ", " + decimalDigits + ")";
		}

		return fieldType;
	}

	/**
	 * Generate nullable constraint.
	 *
	 * @param columnNoNulls
	 *            the column no nulls value from database.
	 * @return "NOT NULL" or "".
	 */
	public static String getNullable(int columnNoNulls) {
		return DatabaseMetaData.columnNoNulls == columnNoNulls ? "NOT NULL" : "";
	}

	/**
	 * Check if the given JDBC type should use exact match in SQL WHERE clause or not.
	 *
	 * @param iJdbcType
	 *            the JDBC type.
	 * @return true if the given JDBC type is boolean or numeric type; false otherwise.
	 */
	public static boolean exactMatch(int iJdbcType) {
		boolean flag = false;

		switch (iJdbcType) {
			case Types.BIT:
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
			case Types.BIGINT:
			case Types.REAL:
			case Types.DOUBLE:
			case Types.FLOAT:
			case Types.DECIMAL:
			case Types.NUMERIC:
				flag = true;
				break;
		}

		return flag;
	}

	/**
	 * Check if the given JDBC type should use LIKE match in SQL WHERE clause or not.
	 *
	 * @param iJdbcType
	 *            the JDBC type.
	 * @return true if the given JDBC type is character type; false otherwise.
	 */
	public static boolean likeMatch(int iJdbcType) {
		boolean flag = false;

		switch (iJdbcType) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				flag = true;
				break;
		}

		return flag;
	}

	/**
	 * Check if the given JDBC type has length property or not.
	 *
	 * @param iJdbcType
	 *            the JDBC type.
	 * @return true if the given JDBC type is character type; false otherwise.
	 */
	private static boolean hasSize(int iJdbcType) {
		boolean flag = false;

		switch (iJdbcType) {
			case Types.CHAR:
			case Types.VARCHAR:
				flag = true;
				break;
		}

		return flag;
	}

	/**
	 * Check if the given JDBC type has precision and scale property or not.
	 *
	 * @param iJdbcType
	 *            the JDBC type.
	 * @return true if the given JDBC type is decimal or numeric type; false otherwise.
	 */
	private static boolean hasPrecisionAndScale(int iJdbcType) {
		boolean flag = false;

		switch (iJdbcType) {
			case Types.DECIMAL:
			case Types.NUMERIC:
				flag = true;
				break;
		}

		return flag;
	}

	/**
	 * Generate the JDBC type (String) for the given JDBC type (int).
	 *
	 * @param iJdbcType
	 *            the JDBC type.
	 * @return the JDBC type (String).
	 */
	public static String getJdbcType(int iJdbcType) {
		String sJdbcType = "XXX";

		switch (iJdbcType) {
			case Types.CHAR:
				sJdbcType = "CHAR";
				break;
			case Types.VARCHAR:
				sJdbcType = "VARCHAR";
				break;
			case Types.LONGVARCHAR:
				sJdbcType = "LONGVARCHAR";
				break;
			case Types.BINARY:
				sJdbcType = "BINARY";
				break;
			case Types.VARBINARY:
				sJdbcType = "VARBINARY";
				break;
			case Types.LONGVARBINARY:
				sJdbcType = "LONGVARBINARY";
				break;
			case Types.BIT:
				sJdbcType = "BIT";
				break;
			case Types.TINYINT:
				sJdbcType = "TINYINT";
				break;
			case Types.SMALLINT:
				sJdbcType = "SMALLINT";
				break;
			case Types.INTEGER:
				sJdbcType = "INTEGER";
				break;
			case Types.BIGINT:
				sJdbcType = "BIGINT";
				break;
			case Types.REAL:
				sJdbcType = "REAL";
				break;
			case Types.DOUBLE:
				sJdbcType = "DOUBLE";
				break;
			case Types.FLOAT:
				sJdbcType = "FLOAT";
				break;
			case Types.DECIMAL:
				sJdbcType = "DECIMAL";
				break;
			case Types.NUMERIC:
				sJdbcType = "NUMERIC";
				break;
			case Types.DATE:
				sJdbcType = "DATE";
				break;
			case Types.TIME:
				sJdbcType = "TIME";
				break;
			case Types.TIMESTAMP:
				sJdbcType = "TIMESTAMP";
				break;
		}
		return sJdbcType;
	}
}
