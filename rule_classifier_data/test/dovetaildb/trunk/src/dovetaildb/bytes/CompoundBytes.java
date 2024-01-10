/**
 * 
 */
package dovetaildb.bytes;

import java.io.Serializable;

import dovetaildb.util.Util;

public final class CompoundBytes extends AbstractBytes implements Serializable {

	private static final long serialVersionUID = -2520233758045807761L;

	Bytes part1, part2;
	int midIndex;
	public CompoundBytes(Bytes part1, Bytes part2) {
		this.part1 = part1;
		this.part2 = part2;
		midIndex = part1.getLength();
	}
	public int get(int position) {
		if (position < midIndex) return part1.get(position);
		else return part2.get(position - midIndex);
	}
	public int getLength() {
		return midIndex + part2.getLength();
	}
	public void writeBytes(int rIdx, byte[] buffer, int wIdx, int limit) {
		if (rIdx + limit <= midIndex) {
			part1.writeBytes(rIdx, buffer, wIdx, limit);
		} else if (rIdx >= midIndex) {
			part2.writeBytes(rIdx - midIndex, buffer, wIdx, limit);
		} else {
			// straddle
			int part1Len = midIndex - rIdx;
			part1.writeBytes(rIdx, buffer, wIdx, part1Len);
			part2.writeBytes(0, buffer, wIdx + part1Len, limit - part1Len);
		}
	}
	public Bytes subBytes(int start, int count) {
		return new SlicedBytes(this, start, count);
	}
	public void setSuffix(Bytes suffix) {
		this.part2 = suffix;
	}
	public Bytes getPrefix() {
		return part1;
	}
	public Bytes getSuffix() {
		return part2;
	}
	public void setPrefix(Bytes bytes) {
		this.part1 = bytes;
		this.midIndex = part1.getLength();
	}
	public static Bytes make(Bytes prefix, Bytes suffix) {
		if (prefix.getLength() == 0) return suffix;
		if (suffix.getLength() == 0) return prefix;
		return new CompoundBytes(prefix, suffix);
	}
	public String toString() {
		return "CompoundBytes("+part1+","+part2+")";
	}
	@Override
	public int compareToParts(Bytes that, int o1, int o2, int l1, int l2, int otherCmpPriority) {
		if (midIndex <= o1)    return part2.compareToParts(that, o1-midIndex, o2, l1, l2, otherCmpPriority); 
		if (midIndex >= o1+l1) return part1.compareToParts(that, o1, o2, l1, l2, otherCmpPriority); 
		int preLen = midIndex - o1;
		if (l2 < preLen) {
			return part1.compareToParts(that, o1, o2, preLen, l2, otherCmpPriority);
		}
		int diff = part1.compareToParts(that, o1, o2, preLen, preLen, otherCmpPriority);
		if (diff != 0) return diff;
		o2 += preLen;
		l2 -= preLen;
		int postLen = o1 + l1 - midIndex;
		return part2.compareToParts(that, 0, o2, postLen, l2, otherCmpPriority);
	}
	
	@Override
	public Bytes copyInto(Bytes other) {
		if (other instanceof CompoundBytes) {
			CompoundBytes otherCompound = (CompoundBytes)other;
			otherCompound.midIndex = midIndex;
			otherCompound.part1 = part1.copyInto(otherCompound.part1);
			otherCompound.part2 = part2.copyInto(otherCompound.part2);
			return otherCompound;
		} else {
			return super.copyInto(other);
		}
	}

	@Override
	public Bytes copy() {
		CompoundBytes copy = new CompoundBytes(part1.copy(), part2.copy());
		assert copy.midIndex == midIndex;
		return copy;
	}

}