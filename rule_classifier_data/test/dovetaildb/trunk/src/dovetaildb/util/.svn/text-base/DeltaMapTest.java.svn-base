package dovetaildb.util;
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

import java.util.HashMap;

import junit.framework.TestCase;

public class DeltaMapTest extends TestCase {

	HashMap<String,String>   m1 = new HashMap<String,String>();
	DeltaMap <String,String> m2 = new DeltaMap<String,String>(m1);
	DeltaMap <String,String> m3 = new DeltaMap<String,String>(m2);

	@Override
	public void setUp(){
		
	}
	
	public void testOverride() {
		m1.put("foo", "bar");
		assertEquals("bar", m2.get("foo"));
		m2.put("foo", "baz");
		assertEquals("bar", m1.get("foo"));
		assertEquals("baz", m2.get("foo"));
		assertEquals("bar", m1.entrySet().iterator().next().getValue());
		assertEquals("baz", m2.entrySet().iterator().next().getValue());
	}

	public void testDelete() {
		m1.put("foo", "bar");
		assertEquals("bar", m2.get("foo"));
		assertEquals("bar", m3.get("foo"));
		m2.remove("foo");
		assertEquals("bar", m1.get("foo"));
		assertEquals(null,  m2.get("foo"));
		assertEquals(null,  m3.get("foo"));
		m3.put("foo", "baz");
		assertTrue (m1.containsValue("bar"));
		assertFalse(m1.containsValue("baz"));
		assertFalse(m2.containsValue("bar"));
		assertFalse(m2.containsValue("baz"));
		assertFalse(m3.containsValue("bar"));
		assertTrue (m3.containsValue("baz"));
		assertEquals(1, m1.size());
		assertEquals(0, m2.size());
		assertEquals(1, m3.size());
	}

}
