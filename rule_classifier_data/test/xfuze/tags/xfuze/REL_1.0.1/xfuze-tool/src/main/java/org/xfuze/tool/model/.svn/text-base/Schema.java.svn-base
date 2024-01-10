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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason Chan
 *
 */
public class Schema extends BaseObject {
	private String modelName;
	private String tableName;

	private List<SchemaField> schemaFields = new ArrayList<SchemaField>();
	private List<SchemaField> primaryKeyFields = new ArrayList<SchemaField>();
	private List<SchemaField> uniqueFields = new ArrayList<SchemaField>();
	private List<SchemaField> foreignKeyFields = new ArrayList<SchemaField>();

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<SchemaField> getForeignKeyFields() {
		return foreignKeyFields;
	}

	public void setForeignKeyFields(List<SchemaField> foreignKeyFields) {
		this.foreignKeyFields = foreignKeyFields;
	}

	public List<SchemaField> getPrimaryKeyFields() {
		return primaryKeyFields;
	}

	public void setPrimaryKeyFields(List<SchemaField> primaryKeyFields) {
		this.primaryKeyFields = primaryKeyFields;
	}

	public List<SchemaField> getSchemaFields() {
		return schemaFields;
	}

	public void setSchemaFields(List<SchemaField> schemaFields) {
		this.schemaFields = schemaFields;
	}

	public List<SchemaField> getUniqueFields() {
		return uniqueFields;
	}

	public void setUniqueFields(List<SchemaField> uniqueFields) {
		this.uniqueFields = uniqueFields;
	}

	public String getFileName() {
		return modelName + ".sql";
	}
}
