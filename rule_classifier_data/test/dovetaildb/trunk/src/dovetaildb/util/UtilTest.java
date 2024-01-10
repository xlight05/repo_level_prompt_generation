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

import java.util.HashSet;


import junit.framework.TestCase;


public class UtilTest extends TestCase {

	public void testGenUuid() {
		HashSet<String> allIds = new HashSet<String>();
		for(int i=0; i<1000; i++) {
			String ret = Util.genUUID();
			boolean isin = allIds.contains(ret);
			assertFalse(isin);
			allIds.add(ret);
		}
	}
	
	public void testBytesToInt() {
		byte[] bytes = new byte[]{0,0,0,-128};
		assertEquals(0x80000000L,       Util.leBytesToUInt(bytes, 0));
		assertEquals(Integer.MIN_VALUE, Util.leBytesToInt(bytes, 0));
	}
}