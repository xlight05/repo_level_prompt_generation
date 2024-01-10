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
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jason Chan
 *
 */
public class SqlMap extends BaseObject {
	private String database;
	private String tableName;
	private String likeHandlerAlias;
	private String updateControl;
	private String deleteControl;

	private Model model;
	private List<SqlMapField> primaryKeyFields = new ArrayList<SqlMapField>();
	private List<SqlMapField> sqlMapFields = new ArrayList<SqlMapField>();
	private List<SqlMapField> updateFields = new ArrayList<SqlMapField>();

	private Set<SqlMapTypeAlias> typeAliases = new TreeSet<SqlMapTypeAlias>();

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getUpdateControl() {
		return updateControl;
	}

	public void setUpdateControl(String updateControl) {
		this.updateControl = updateControl;
	}

	public String getDeleteControl() {
		return deleteControl;
	}

	public void setDeleteControl(String deleteControl) {
		this.deleteControl = deleteControl;
	}

	public String getLikeHandlerAlias() {
		return likeHandlerAlias;
	}

	public void setLikeHandlerAlias(String likeHandlerAlias) {
		this.likeHandlerAlias = likeHandlerAlias;
	}

	public List<SqlMapField> getPrimaryKeyFields() {
		return primaryKeyFields;
	}

	public void setPrimaryKeyFields(List<SqlMapField> primaryKeyFields) {
		this.primaryKeyFields = primaryKeyFields;
	}

	public List<SqlMapField> getSqlMapFields() {
		return sqlMapFields;
	}

	public void setSqlMapFields(List<SqlMapField> sqlMapFields) {
		this.sqlMapFields = sqlMapFields;
	}

	public Set<SqlMapTypeAlias> getTypeAliases() {
		return typeAliases;
	}

	public void setTypeAliases(Set<SqlMapTypeAlias> typeAliases) {
		this.typeAliases = typeAliases;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<SqlMapField> getUpdateFields() {
		return updateFields;
	}

	public void setUpdateFields(List<SqlMapField> updateFields) {
		this.updateFields = updateFields;
	}

	public String getFileName() {
		return model.getJavaName() + ".xml";
	}

	public String getFileName(String suffix) {
		return model.getJavaName() + "-" + suffix;
	}
}
