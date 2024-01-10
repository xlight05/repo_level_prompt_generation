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

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jason Chan
 *
 */
public abstract class JavaObject extends BaseObject {
	protected String javaPackage;
	protected String javaName;

	private Set<String> javaImports = new TreeSet<String>();

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public String getJavaPackage() {
		return javaPackage;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public Set<String> getJavaImports() {
		return javaImports;
	}

	public void setJavaImports(Set<String> javaImports) {
		this.javaImports = javaImports;
	}

	public String getFileName() {
		return javaName + ".java";
	}

	public String getInstanceName() {
		return javaName.substring(0, 1).toLowerCase() + javaName.substring(1);
	}
}
