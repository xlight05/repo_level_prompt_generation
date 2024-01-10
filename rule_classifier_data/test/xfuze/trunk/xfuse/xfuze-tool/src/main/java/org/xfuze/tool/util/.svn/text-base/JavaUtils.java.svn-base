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

import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * @author Jason Chan
 *
 */
public final class JavaUtils {
	private JavaUtils() {
	}

	/**
	 * Generate model name.
	 *
	 * @param tableName
	 *            the table name.
	 * @param tableNameSuffix
	 *            the table suffix.
	 * @param pojoSuffix
	 *            the suffix to add.
	 * @return the model name.
	 */
	public static String getModelName(String tableName, String tableNameSuffix, String pojoSuffix) {
		String modelName = WordUtils.capitalizeFully(tableName.replaceAll(tableNameSuffix, ""), new char[] { '_' });
		modelName = modelName.replaceAll("_", "");

		if (StringUtils.isNotBlank(pojoSuffix)) {
			modelName += pojoSuffix;
		}
		return modelName;
	}

	/**
	 * Generate DAO or DAO impl. name.
	 *
	 * @param tableName
	 *            the table name.
	 * @param tableNameSuffix
	 *            the table suffix.
	 * @param daoSuffix
	 *            the suffix to add.
	 * @return the DAO or DAO impl. name.
	 */
	public static String getDaoName(String tableName, String tableNameSuffix, String daoSuffix) {
		String daoName = WordUtils.capitalizeFully(tableName.replaceAll(tableNameSuffix, ""), new char[] { '_' });
		daoName = daoName.replaceAll("_", "");

		if (StringUtils.isNotBlank(daoSuffix)) {
			daoName += daoSuffix;
		}
		return daoName;
	}

	/**
	 * Generate service or service impl. name.
	 *
	 * @param tableName
	 *            the table name.
	 * @param tableNameSuffix
	 *            the table suffix.
	 * @param serviceSuffix
	 *            the suffix to add.
	 * @return the service or service impl. name.
	 */
	public static String getServiceName(String tableName, String tableNameSuffix, String serviceSuffix) {
		String serviceName = WordUtils.capitalizeFully(tableName.replaceAll(tableNameSuffix, ""), new char[] { '_' });
		serviceName = serviceName.replaceAll("_", "");

		if (StringUtils.isNotBlank(serviceSuffix)) {
			serviceName += serviceSuffix;
		}
		return serviceName;
	}

	/**
	 * Generate controller name.
	 *
	 * @param tableName
	 *            the table name.
	 * @param tableNameSuffix
	 *            the table suffix.
	 * @param controllerSuffix
	 *            the suffix to add.
	 * @return the controller name.
	 */
	public static String getControllerName(String tableName, String tableNameSuffix, String controllerSuffix) {
		String controllerName = WordUtils
				.capitalizeFully(tableName.replaceAll(tableNameSuffix, ""), new char[] { '_' });
		controllerName = controllerName.replaceAll("_", "");

		if (StringUtils.isNotBlank(controllerSuffix)) {
			controllerName += controllerSuffix;
		}
		return controllerName;
	}

	/**
	 * Generate controller view name.
	 *
	 * @param tableName
	 *            the table name.
	 * @param tableNameSuffix
	 *            the table suffix.
	 * @param controllerViewSuffix
	 *            the suffix to add.
	 * @return the controller view name.
	 */
	public static String getControllerViewName(String tableName, String tableNameSuffix, String controllerViewSuffix) {
		String controllerViewName = WordUtils.capitalizeFully(tableName.replaceAll(tableNameSuffix, ""),
				new char[] { '_' });
		controllerViewName = controllerViewName.replaceAll("_", "");
		controllerViewName = controllerViewName.substring(0, 1).toLowerCase() + controllerViewName.substring(1);

		if (StringUtils.isNotBlank(controllerViewSuffix)) {
			controllerViewName += controllerViewSuffix;
		}
		return controllerViewName;
	}

	/**
	 * Generate validator name.
	 *
	 * @param tableName
	 *            the table name.
	 * @param tableNameSuffix
	 *            the table suffix.
	 * @param validatorSuffix
	 *            the suffix to add.
	 * @return the validator name.
	 */
	public static String getValidatorName(String tableName, String tableNameSuffix, String validatorSuffix) {
		String validatorName = WordUtils.capitalizeFully(tableName.replaceAll(tableNameSuffix, ""), new char[] { '_' });
		validatorName = validatorName.replaceAll("_", "");

		if (StringUtils.isNotBlank(validatorSuffix)) {
			validatorName += validatorSuffix;
		}
		return validatorName;
	}

	/**
	 * Generate the Java field type.
	 *
	 * @param jdbcType
	 *            the JDBC type.
	 * @return the Java field type.
	 */
	public static String getJavaFieldType(int jdbcType) {
		String fieldType = "XXX";

		switch (jdbcType) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				fieldType = "String";
				break;
			case Types.BINARY:
			case Types.VARBINARY:
			case Types.LONGVARBINARY:
				fieldType = "byte[]";
				break;
			case Types.BIT:
				fieldType = "Boolean";
				break;
			case Types.TINYINT:
				fieldType = "Byte";
				break;
			case Types.SMALLINT:
				fieldType = "Short";
				break;
			case Types.INTEGER:
				fieldType = "Integer";
				break;
			case Types.BIGINT:
				fieldType = "Long";
				break;
			case Types.REAL:
				fieldType = "Float";
				break;
			case Types.DOUBLE:
			case Types.FLOAT:
				fieldType = "Double";
				break;
			case Types.DECIMAL:
			case Types.NUMERIC:
				fieldType = "java.math.BigDecimal";
				break;
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				fieldType = "java.util.Date";
				break;
		}

		return fieldType;
	}

	/**
	 * Generate Java field name.
	 *
	 * @param databaseFieldName
	 *            the database field name.
	 * @return the Java field name.
	 */
	public static String getJavaFieldName(String databaseFieldName) {
		String fieldName = databaseFieldName;
		fieldName = WordUtils.capitalizeFully(fieldName, new char[] { '_' });
		fieldName = fieldName.replaceAll("_", "");
		fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);

		return fieldName;
	}

	/**
	 * Generate spring name.
	 *
	 * @param tableName
	 *            the table name.
	 * @param tableNameSuffix
	 *            the table suffix.
	 * @return the spring name.
	 */
	public static String getSpringName(String tableName, String tableNameSuffix) {
		String springName = WordUtils.capitalizeFully(tableName.replaceAll(tableNameSuffix, ""), new char[] { '_' });
		springName = springName.replaceAll("_", "");
		return springName;
	}
}
