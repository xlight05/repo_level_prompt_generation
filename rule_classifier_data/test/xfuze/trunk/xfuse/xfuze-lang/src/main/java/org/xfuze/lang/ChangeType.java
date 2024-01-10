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

package org.xfuze.lang;

/**
 * @author Jason Chan
 *
 */
public enum ChangeType {
	INSERT("I"), UPDATE("U"), DELETE("D");

	private final String code;

	ChangeType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public static ChangeType getInstance(String code) {
		for (ChangeType changeType : ChangeType.values()) {
			if (changeType.code.equals(code)) {
				return changeType;
			}
		}

		throw new IllegalArgumentException("No such Change Type.");
	}
}
