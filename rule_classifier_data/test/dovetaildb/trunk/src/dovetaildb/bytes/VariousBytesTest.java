package dovetaildb.bytes;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import junit.framework.TestCase;

public class VariousBytesTest extends TestCase {

	public ArrayBytes randArrayBytes(Random r) {
		byte[] bytearray = new byte[r.nextInt(8)];
		r.nextBytes(bytearray);
		return new ArrayBytes(bytearray);
	}
	public Bytes randBytes(Random r, int depth) {
		Bytes bytes = randArrayBytes(r);
		while(true) {
			switch(r.nextInt(depth)) {
			case 0: 
				if (bytes.getLength() > 1) {
					int offset = r.nextInt(bytes.getLength()-1);
					int len = r.nextInt(bytes.getLength() - offset);
					bytes = new SlicedBytes(bytes, offset, len);
				}
				break;
			case 1:
				Bytes other = randBytes(r, depth + 1);
				if (r.nextBoolean()) {
					bytes = new CompoundBytes(bytes, other);
				} else {
					bytes = new CompoundBytes(other, bytes);
				}
				break;
			default:
				return bytes;
			}
			depth++;
		}
	}
	
	public void testRegression1() {
		Bytes min = new ArrayBytes(new byte[]{123,108,111,99,58,123,108,97,116,58,110,-64,64,0,0,0,0,0,0});
		Bytes max = new ArrayBytes(new byte[]{123,108,111,99,58,123,108,97,116,58,110,-64,65,-128,0,0,0,0,0});
		Bytes test = new CompoundBytes(new CompoundBytes(new ArrayBytes(new byte[]{}),
				new ArrayBytes(new byte[]{123})),
				new SlicedBytes(new ArrayBytes(new byte[]{123,108,111,99,58,123}),1,5));
		assertTrue(min.compareTo(test) > 0);
		assertTrue(max.compareTo(test) > 0);
	}
	
	public void testCompareTo() {
		int maxIters = 1000;
		Random r = new Random(2308844);
		for(int iter=0; iter<maxIters; iter++) {
			ArrayList<Bytes> bytes     = new ArrayList<Bytes>();
			ArrayList<Bytes> flattened = new ArrayList<Bytes>();
			for(int i=0; i<iter+1; i++) {
				Bytes b = randBytes(r, 3);
				bytes.add(b);
				flattened.add(b.flatten());
			}
			Collections.sort(bytes);
			Collections.sort(flattened);
//			System.out.println(flattened);
//			System.out.println(bytes);
//			System.out.println("----------");
			for(int i=0; i<bytes.size(); i++) {
				assertEquals(flattened.get(i), bytes.get(i));
			}
		}
	}

}
