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
public class ServiceTest extends Service {
	private Model model;
	private Dao dao;
	private Service service;
	private boolean primaryKeyGenerated = true;

	private List<ModelField> nonPrimaryKeyFields = new ArrayList<ModelField>();

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public boolean isPrimaryKeyGenerated() {
		return primaryKeyGenerated;
	}

	public void setPrimaryKeyGenerated(boolean primaryKeyGenerated) {
		this.primaryKeyGenerated = primaryKeyGenerated;
	}

	public List<ModelField> getNonPrimaryKeyFields() {
		return nonPrimaryKeyFields;
	}

	public void setNonPrimaryKeyFields(List<ModelField> nonPrimaryKeyFields) {
		this.nonPrimaryKeyFields = nonPrimaryKeyFields;
	}
}
