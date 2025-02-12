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

package org.xfuze.util;

/**
 * Match mode for string field (char/varchr) in database.
 *
 * @author Jason Chan
 *
 */
public enum Match {
	/**
	 * Exact match, using =. Example: field = 'example'.
	 */
	EXACT {
		public String toString() {
			return "EXACT";
		}
	},
	/**
	 * Partial match, using like. Example: field like '%example%'.
	 */
	ANYWHERE {
		public String toString() {
			return "ANYWHERE";
		}
	}
}
