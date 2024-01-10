/**
 * 
 */
package dovetaildb.bytes;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import dovetaildb.util.Util;


public final class SlicedBytes extends AbstractBytes implements Serializable {

	private static final long serialVersionUID = 3217835882708900147L;

	Bytes bytes;
	int offset, limit;
	public SlicedBytes(Bytes bytes, int offset) {
		this(bytes, offset, bytes.getLength()-offset);
	}
	public SlicedBytes(Bytes bytes, int offset, int limit) {
		this.bytes = bytes;
		this.offset = offset;
		this.limit = limit;
	}
	public int get(int position) { return bytes.get(offset+position); }
	public int getLength() { return limit; }
	public SlicedBytes subBytes(int start, int count) {
		this.offset += start;
		this.limit = count;
		return this;
	}
	public SlicedBytes subBytes(int start) {
		this.offset += start;
		this.limit -= start;
		return this;
	}
	public void reInitialize(Bytes bytes, int start, int count) {
		this.bytes = bytes;
		this.offset = start;
		this.limit = count;
	}
	public static Bytes make(Bytes bytes, int start, int count) {
		if (bytes instanceof CompoundBytes) {
			CompoundBytes compound = (CompoundBytes)bytes;
			int mid = compound.midIndex;
			if (mid <= start) {
				return SlicedBytes.make(compound.part2, start-mid, count);
			} else if (mid >= start+count) {
				return SlicedBytes.make(compound.part1, start, count);
			}
		} // maybe someday: else if (term instanceof SlicedBytes) {...
		if (start == 0 && count == bytes.getLength()) return bytes;
		return new SlicedBytes(bytes, start, count);
	}
	public int getSlicePosition() {
		return this.offset;
	}
	public String toString() {
		return "SlicedBytes("+bytes+","+offset+","+limit+")";
	}
	@Override
	public void writeBytes(int rIdx, byte[] buffer, int wIdx, int limit) {
		bytes.writeBytes(rIdx + offset, buffer, wIdx, limit);
	}
	@Override
	public int compareToParts(Bytes that, int o1, int o2, int l1, int l2, int otherCmpPriority) {
		o1 += offset;
		// l1 must already be in my bounds; no need to do anything special
		return bytes.compareToParts(that, o1, o2, l1, l2, otherCmpPriority);
	}

	public boolean wantsToDriveComparisonsWith() { return true; }
	public Bytes underlyingBytes() {
		return bytes;
	}
}