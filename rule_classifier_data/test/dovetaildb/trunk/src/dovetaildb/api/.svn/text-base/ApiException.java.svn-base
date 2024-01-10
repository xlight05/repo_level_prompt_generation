package dovetaildb.api;
/*
 * Copyright 2008 Phillip Schanely
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



public class ApiException extends RuntimeException { 
	private static final long serialVersionUID = -1530047865739165355L;
	public String exName;
	public String stackTrace;
	
	public ApiException(String exName, String message) {
		super(message);
		this.exName = exName;
	}
	public ApiException(String exName, Exception source) {
		super(source.getMessage(), source);
		this.exName = exName;
	}
	public ApiException(String exName, String message, Exception source) {
		super(message, source);
		this.exName = exName;
	}
	public ApiException(String exName, String message, String stackTrace) {
		super(message);
		this.exName = exName;
		this.stackTrace = stackTrace;
	}

	public String getExceptionName() { return exName; }
}
