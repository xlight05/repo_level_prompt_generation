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
public class Model extends JavaObject {
	private List<ModelField> modelFields = new ArrayList<ModelField>();
	private PrimaryKey primaryKey;
	private List<ModelField> primaryKeyFields = new ArrayList<ModelField>();

	private List<ModelField> excludedModelFields = new ArrayList<ModelField>();

	public List<ModelField> getModelFields() {
		return modelFields;
	}

	public void setModelFields(List<ModelField> modelFields) {
		this.modelFields = modelFields;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ModelField> getPrimaryKeyFields() {
		return primaryKeyFields;
	}

	public void setPrimaryKeyFields(List<ModelField> primaryKeyFields) {
		this.primaryKeyFields = primaryKeyFields;
	}

	public List<ModelField> getExcludedModelFields() {
		return excludedModelFields;
	}

	public void setExcludedModelFields(List<ModelField> excludedModelFields) {
		this.excludedModelFields = excludedModelFields;
	}
}
