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

package org.xfuze.service;

import org.xfuze.lang.FastException;

/**
 * @author Jason Chan
 *
 */
public class ServiceException extends FastException {
	private static final long serialVersionUID = 4168689234305830137L;

	private String code;
	private String[] arguments = new String[] {};

	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(String code, String message, String[] arguments) {
		super(message);
		this.code = code;
		this.arguments = arguments;
	}

	/**
	 * Returns the error code of this exception.
	 *
	 * @return the error code of this exception
	 * @see ExceptionConstant
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Returns the arguments for this exception. Empty String[] if no argument available.
	 *
	 * @return the arguments for this exception. Empty String[] if no argument available.
	 */
	public String[] getArguments() {
		if (arguments == null) {
			arguments = new String[] {};
		}
		return this.arguments;
	}
}
