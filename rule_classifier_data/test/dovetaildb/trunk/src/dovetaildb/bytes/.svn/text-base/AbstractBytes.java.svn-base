package dovetaildb.bytes;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import dovetaildb.store.BytesInterface;
import dovetaildb.util.Util;

public abstract class AbstractBytes implements Bytes {

	
	public void writeBytes(int fOffset, byte[] buffer) {
		writeBytes(fOffset, buffer, 0, getLength()-fOffset);
	}
	public void writeBytes(int fOffset, byte[] buffer, int limit) {
		writeBytes(fOffset, buffer, 0, limit);
	}
	public void writeBytes(byte[] buffer, int offset) {
		writeBytes(0, buffer, offset, getLength());
	}
	public void writeBytes(byte[] buffer, int offset, int limit) {
		writeBytes(0, buffer, offset, limit);
	}
	public void writeBytes(int rIdx, byte[] buffer, int wIdx, int limit) {
		while(limit-- > 0) {
			buffer[wIdx++] = (byte)get(rIdx++);
		}
	}
	
	public String getAsString() {
		try {
			return new String(getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public byte[] getBytes() {
		byte[] buf = new byte[getLength()];
		writeBytes(buf, 0);
		return buf;
	}

	public byte[] getBytes(int offset, int length) {
		byte[] buf = new byte[length];
		writeBytes(offset, buf, 0, length);
		return buf;
	}
	
	public Bytes flatten() {
		return new ArrayBytes(getBytes());
	}
	
//	public void getBytes(byte[] buffer, int offset, int limit) {
//		limit += offset;
//		int idx=0;
//		for(int i=offset; i<limit; i++) {
//			buffer[idx++] = (byte)get(i);
//		}
//	}

	public static Bytes removePrefixBytes(Bytes bytes, int offset) {
		return new SlicedBytes(bytes, offset, bytes.getLength()-offset);
	}
	
	public static Bytes prependBytes(Bytes bytes, Bytes bytesToPrepend) {
		if (bytes.getLength() == 0) return bytesToPrepend;
		if (bytesToPrepend.getLength() == 0) return bytes;
		return new CompoundBytes(bytesToPrepend, bytes);
	}
	
	@Override
	public int compareTo(Bytes that) {
		return compareToParts(that, 0, 0, getLength(), that.getLength(), 9);
	}

	@Override
	public int compareToParts(Bytes that, int o1, int o2, int l1, int l2, int otherCmpPriority) {
		int minLen = Math.min(l1, l2);
		for(int i=0; i<minLen; i++) {
			int diff = this.get(o1+i) - that.get(o2+i);
			if (diff != 0) return diff;
		}
		return l1 - l2;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for(int i=getLength()-1; i>=0; i--) {
			hash = (hash*31) + get(i);
		}
		return hash;
	}

	public boolean equals(Object o) {
		return compareTo((Bytes)o) == 0;
	}
	
	public boolean isPrefixOf(Bytes that) {
		int len1 = this.getLength();
		int len2 = that.getLength();
		if (len2 < len1) return false;
		// TODO : try this : return compareToParts(that, 0, 0, len1, len1, 9) == 0;
		for(int i=0; i<len1; i++) {
			if (this.get(i) != that.get(i)) return false;
		}
		return true;
	}
	
	public boolean isSuffixOf(Bytes that) {
		int len1 = this.getLength();
		int len2 = that.getLength();
		if (len2 < len1) return false;
		return compareToParts(that, 0, len2 - len1, len1, len1, 9) == 0;
	}
	
	public boolean couldOverlapWith(Bytes that) {
		int len1 = this.getLength();
		int len2 = that.getLength();
		int len = (len2 < len1) ? len2 : len1;
		return compareToParts(that, 0, 0, len, len, 9) == 0;
	}
	public String toString() {
		String s = getClass().getName();
		s += "("+Util.makePrintable(getBytes())+")";
		return s;
	}
	public Bytes copyInto(Bytes other) {
		if (other instanceof ArrayBytes) {
			ArrayBytes arrBytes = (ArrayBytes)other;
			arrBytes.bytes = getBytes();
			return arrBytes;
		} else {
			return copy();
		}
	}
	public Bytes copy() {
		return new ArrayBytes(getBytes());
	}
	/** subBytes is allowed to be destructive and may return itself. */
	public Bytes subBytes(int index) {
		return subBytes(index, getLength()-index);
	}
	/** subBytes is allowed to be destructive and may return itself. */
	public Bytes subBytes(int start, int count) {
		return new ArrayBytes(getBytes(start, count));
	}
	public void appendTo(BytesInterface iface) {
		iface.appendBytes(getBytes());
	}

}
