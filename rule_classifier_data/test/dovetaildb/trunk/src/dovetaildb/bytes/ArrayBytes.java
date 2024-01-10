package dovetaildb.bytes;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import dovetaildb.util.Util;

public final class ArrayBytes extends AbstractBytes implements Serializable {
	private static final long serialVersionUID = 2913559660500216685L;
	public static final ArrayBytes EMPTY_BYTES = new ArrayBytes(new byte[]{});
	public static final Bytes[] SINGLE_BYTE_OBJECTS = new Bytes[256];
	static {
		for(int i=0; i<256; i++) {
			SINGLE_BYTE_OBJECTS[i] = new ArrayBytes(new byte[]{(byte)i});
		}
	}
	protected byte[] bytes;
	public byte[] getBackingBytes() { return bytes; }
	public ArrayBytes(byte[] bytes) { this.bytes = bytes; }
	public ArrayBytes(String s) {
		this(Util.decodeString(s));
	}
	public int get(int position) { 
		return 0xFF & bytes[position]; 
	}
	public int getLength() { return bytes.length; }
	public void setLength(int len) {
		byte[] newBytes = new byte[len];
		System.arraycopy(bytes, 0, newBytes, 0, (len > bytes.length) ? bytes.length : len);
		bytes = newBytes;
	}
	public void write(int writeOffset, Bytes src, int srcOffset, int len) {
		if (writeOffset + len > bytes.length) {
			setLength(writeOffset+len);
		}
		for(int i=writeOffset; i<len; i++) {
			bytes[writeOffset+i] = (byte)src.get(srcOffset+i); 
		}
	}
	public String toString() {
		return "\""+Util.makePrintable(new String(bytes))+"\"";
		//return "\""+Util.makePrintable(bytes)+"\"";
	}
	public Bytes subBytes(int start, int count) {
		return new SlicedBytes(this, start, count);
	}
	public static Bytes fromString(String s) {
		try {
			return new ArrayBytes(s.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	static final int MY_CMP_PRIORITY = 3;
	
	@Override
	public int compareTo(Bytes that) {
		int mylen = bytes.length;
		if (that instanceof ArrayBytes) {
			ArrayBytes athat = (ArrayBytes)that;
			byte[] b2 = athat.bytes;
			return Util.compareBytes(bytes, b2, 0, 0, mylen, b2.length);
		} else {
			return -that.compareToParts(this, 0, 0, that.getLength(), mylen, MY_CMP_PRIORITY);
		}
	}
	
	@Override
	public int compareToParts(Bytes that, int o1, int o2, int l1, int l2, int otherCmpPriority) {
		if (that instanceof ArrayBytes) {
			ArrayBytes athat = (ArrayBytes)that;
			byte[] b2 = athat.bytes;
			return Util.compareBytes(bytes, b2, o1, o2, l1, l2);
		} else if (otherCmpPriority > MY_CMP_PRIORITY) {
			return -that.compareToParts(this, o2, o1, l2, l1, MY_CMP_PRIORITY);
		} else {
			return super.compareToParts(that, o1, o2, l1, l2, MY_CMP_PRIORITY);
		}
	}

	public Bytes flatten() {
		return this;
	}

	public boolean isPrefixOf(Bytes that) {
		if (bytes.length == 0) return true;
		int len2 = that.getLength();
		if (len2 < bytes.length) return false;
		return that.compareToParts(this, 0, 0, bytes.length, bytes.length, MY_CMP_PRIORITY) == 0;
	}
}