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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;

public class UUID {

	static byte[] ip;
	static short counter = 0;
	static byte[] randBuffer = new byte[256];
	static long lastTimestamp = 0;
	static HashSet<Short> randomsSeen = new HashSet<Short>();
	static SecureRandom uuidRandom;

	static {
		try {
			uuidRandom = SecureRandom.getInstance("SHA1PRNG");
	        ip = InetAddress.getLocalHost().getAddress();
	        refillRandom();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	private static void refillRandom() {
		uuidRandom.nextBytes(randBuffer);
	}
	
	private static short nextValue() {
		counter +=2;
		if (counter > randBuffer.length) {
			refillRandom();
			counter = 2;
		}
		return (short)(randBuffer[counter-2] | (randBuffer[counter-1] << 8));
	}

	public static String generatePureRandom(int numChars) {
		int numBytes = ((numChars * 2) / 3)+3;
		byte[] bytes = new byte[numBytes];
		if (counter+numBytes > randBuffer.length) {
			refillRandom();
			counter=0;
		}
		System.arraycopy(randBuffer, counter, bytes, 0, numBytes);
		counter += numBytes;
		String ret = Base64.encodeBytes(bytes,Base64.ORDERED);
		return ret.substring(0,numChars);
	}
	
	public static String generate() {
		long sd, ts;
		synchronized (UUID.class) {
			boolean alreadySeen;
			do {
				sd = nextValue();
				ts = System.currentTimeMillis();
				if (ts != lastTimestamp) {
					lastTimestamp = ts;
					randomsSeen.clear();
					break;
				}
				alreadySeen = randomsSeen.contains(new Short((short)sd));
			} while (alreadySeen);
			randomsSeen.add(new Short((short)sd));
		}
		sd |= sd << 8*2;
		sd |= sd << 8*4;
		ts ^= sd;
		byte[] bytes = new byte[]{
			(byte)((sd >>> 8 * 1) & 0xFF),
			(byte)((ip[3] ^ sd)   & 0xFF),
			(byte)((sd)           & 0xFF),
			(byte)((ts >>> 8 * 5) & 0xFF),
			(byte)((ip[2] ^ sd)   & 0xFF),
			(byte)((ts >>> 8 * 4) & 0xFF),
			(byte)((ip[1] ^ sd)   & 0xFF),
			(byte)((ts >>> 8 * 3) & 0xFF),
			(byte)((ip[0] ^ sd)   & 0xFF),
			(byte)((ts >>> 8 * 2) & 0xFF),
			(byte)((ts >>> 8 * 1) & 0xFF),
			(byte)((ts)           & 0xFF),
		};
		
		String ret = Base64.encodeBytes(bytes,Base64.ORDERED);

		return ret;
	}

}
