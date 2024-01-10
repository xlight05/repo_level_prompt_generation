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

import java.util.Map;

import dovetaildb.iter.Iter;

/*
 * send external command (or execute call() script on top of) then send commit
 * 
 * ApiService
 * ApiService
 * ...
 * ApiService
 * ChangesetBuffer
 * DbService
 * DbService
 * ...
 * DbService
 * 
 * 
 * basic idea for using ApiService:
 * 
 * ApiService api;
 * ScriptInferface scriptingLanguage;
 * ChangesetBuffer b = new ChangesetBuffer();
 * dovetaildbGlobal = new DovetaildbProxyObject(api, b);
 * scriptingLanguage.callFunction(..., dovetaildbGlobal);
 *   ...
 *   <inside dovetaildbGlobal.query>
 */

public interface ApiService {

	public Iter query(String bag, Object query, Map<String,Object> options);

	/**
	 * <entry> must have a property named "id"
	 */
	public void put(String bag, Map entry);
	
	/**
	 * A deletion of a non-existent id may be silently ignored.
	 */
	public void remove(String bag, String id);
	
	public void commit();
	
	public void rollback();
	
	public Map<String,Object> getMetrics(int detailLevel);
}
