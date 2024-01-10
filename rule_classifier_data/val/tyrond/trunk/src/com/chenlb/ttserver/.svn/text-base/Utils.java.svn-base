package com.chenlb.ttserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

public class Utils {

	private static final int bInt = 0xff;
	
	public static int byteToInt(byte[] bs, int start, int len) {
		int t = bInt & bs[start];
		for(int i=1; i<len&&i<4; i++) {
			t = t << 8;
			t += bInt & bs[start+i];
		}
		return t;
	}
	
	public static long byteToLong(byte[] bs, int start, int len) {
		long t = bInt & bs[start];
		for(int i=1; i<len&&i<8; i++) {
			t = t << 8;
			t += bInt & bs[start+i];
		}
		return t;
	}
	
	public static byte[] intToBytes(int d) {
		byte[] bs = new byte[4];
		int t = d;
		bs[3] = (byte) t;
		for(int i=1; i<4; i++) {
			t = t >>> 8;
			bs[3-i] = (byte) t;
		}
		return bs;
	}
	
	public static Object readObject(byte[] datas, int offset, int len) throws IOException, ReadObjectException {
		
		Object obj;
		try {
			ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(datas, offset, Math.min(datas.length-offset, len)));
			obj = oin.readObject();
		} catch(InvalidClassException e) {
			throw new ReadObjectException("read object error! InvalidClassException", e);
		} catch(StreamCorruptedException e) {
			throw new ReadObjectException("read object error! StreamCorruptedException", e);
		} catch (ClassNotFoundException e) {
			throw new ReadObjectException("read object error! ClassNotFoundException", e);
		}
		
		return obj;
	}
}
