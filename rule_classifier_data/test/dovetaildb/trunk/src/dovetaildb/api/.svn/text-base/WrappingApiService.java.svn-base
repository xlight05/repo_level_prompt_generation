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

public abstract class WrappingApiService implements ApiService {

	public WrappingApiService() {}
	
	public WrappingApiService(ApiService api) {
		this.inner = api;
	}
	
	protected ApiService inner;
	public ApiService getInner() { return inner; }
	public void setInner(ApiService inner) { this.inner = inner; }
	
	public Iter query(String bag, Object query, Map<String,Object> options) {
		return inner.query(bag, query, options);
	}
	
	public void put(String bag, Map entry) {
		inner.put(bag, entry);
	}
	
	public void remove(String bag, String id) {
		inner.remove(bag, id);
	}
	
	public void commit() {
		inner.commit();
	}
	
	public void rollback() {
		inner.rollback();
	}

	public Map<String,Object> getMetrics(int detailLevel) {
		return inner.getMetrics(detailLevel);
	}
}
