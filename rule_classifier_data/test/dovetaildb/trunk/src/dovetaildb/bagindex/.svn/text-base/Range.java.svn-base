package dovetaildb.bagindex;

import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.CompoundBytes;
import dovetaildb.bytes.SlicedBytes;
import dovetaildb.util.Util;

public final class Range {
	public static final Range OPEN_RANGE = new Range(ArrayBytes.EMPTY_BYTES, null, null, true, true);
	public Bytes prefix;
	public Bytes minSuffix;
	public Bytes maxSuffix;
	public boolean isMinIncluded=true;
	public boolean isMaxIncluded=true;
	public Range() {}
	public Range(Bytes prefix, Bytes minSuffix, Bytes maxSuffix, boolean isMinIncluded, boolean isMaxIncluded) {
		super();
		this.prefix = prefix;
		this.minSuffix = minSuffix;
		this.maxSuffix = maxSuffix;
		this.isMinIncluded = isMinIncluded;
		this.isMaxIncluded = isMaxIncluded;
	}
	public Range(Range o) {
		super();
		this.prefix = o.prefix;
		this.minSuffix = o.minSuffix;
		this.maxSuffix = o.maxSuffix;
		this.isMinIncluded = o.isMinIncluded;
		this.isMaxIncluded = o.isMaxIncluded;
		flattenTerms();
	}
	public boolean isMaxIncluded() {
		return isMaxIncluded;
	}
	public void setMaxIncluded(boolean isMaxIncluded) {
		this.isMaxIncluded = isMaxIncluded;
	}
	public boolean isMinIncluded() {
		return isMinIncluded;
	}
	public void setMinIncluded(boolean isMinIncluded) {
		this.isMinIncluded = isMinIncluded;
	}
	public Bytes getMaxSuffix() {
		return maxSuffix;
	}
	public void setMaxSuffix(Bytes maxSuffix) {
		this.maxSuffix = maxSuffix;
	}
	public Bytes getMinSuffix() {
		return minSuffix;
	}
	public void setMinSuffix(Bytes minSuffix) {
		this.minSuffix = minSuffix;
	}
	public Bytes getPrefix() {
		return prefix;
	}
	public void setPrefix(Bytes prefix) {
		this.prefix = prefix;
	}
	public void propagatePrefixIntoRange(int prefixPos) {
		if (prefixPos == prefix.getLength()) return;
		Bytes delta = new SlicedBytes(prefix, prefixPos);
		prefix = new SlicedBytes(prefix, 0, prefixPos);
		if (minSuffix == null) {
			minSuffix = delta;
			isMinIncluded = true;
		} else {
			minSuffix = CompoundBytes.prependBytes(minSuffix, delta);
		}
		if (maxSuffix == null) {
			byte[] bytes = delta.getBytes();
			if (! Util.incrementBinary(bytes)) {
				maxSuffix = null;
				isMaxIncluded = true;
			} else {
				maxSuffix = new ArrayBytes(bytes);
				isMaxIncluded = false;
			}
		} else {
			maxSuffix = CompoundBytes.prependBytes(maxSuffix, delta);
		}
	}
	public void setBoundsAndExtractPrefix(Bytes minTerm, Bytes maxTerm) {
		prefix = ArrayBytes.EMPTY_BYTES;
		if (minTerm == null || maxTerm == null) {
			minSuffix = minTerm;
			maxSuffix = maxTerm;
			return;
		}
		int minTermLen = minTerm.getLength();
		int maxTermLen = maxTerm.getLength();
		int cap = (minTermLen < maxTermLen) ? minTermLen : maxTermLen;
		int i = 0;
		for(; i<cap; i++) {
			if (minTerm.get(i) != maxTerm.get(i)) break;
		}
		if (i == 0) {
			minSuffix = minTerm;
			maxSuffix = maxTerm;
		} else {
			prefix = new SlicedBytes(minTerm, 0, i);
			minSuffix = minTerm.subBytes(i);
			maxSuffix = maxTerm.subBytes(i);
		}
	}
	public String toString() {
		return "Range("+prefix+","+this.minSuffix+","+this.maxSuffix+","+this.isMinIncluded+","+this.isMaxIncluded+")";
	}
	public boolean matches(Bytes term) {
		if (! prefix.isPrefixOf(term)) return false;
		Bytes suffix = term.copy().subBytes(prefix.getLength());
		if (minSuffix != null) {
			int minCmp = suffix.compareTo(minSuffix);
			if (minCmp < 0) return false;
			if (minCmp == 0 && ! isMinIncluded) return false;
		}
		if (maxSuffix != null) {
			int maxCmp = suffix.compareTo(maxSuffix);
			if (maxCmp > 0) return false;
			if (maxCmp == 0 && ! isMaxIncluded) return false;
		}
		return true;
	}
	public void flattenTerms() {
		if (prefix != null) prefix = prefix.flatten();
		if (minSuffix != null) minSuffix = minSuffix.flatten();
		if (maxSuffix != null) maxSuffix = maxSuffix.flatten();
		
	}
	public boolean containsPrefix(Bytes test) {
		int testLen = test.getLength();
		int prefixLen = prefix.getLength();
		if (testLen <= prefixLen) {
			return test.isPrefixOf(prefix);
		} else {
			if (! prefix.isPrefixOf(test)) return false;
			Bytes testSuffix = new SlicedBytes(test, prefixLen, testLen-prefixLen);
			if (minSuffix != null) {
				if ((! testSuffix.isPrefixOf(minSuffix)) &&
				    (testSuffix.compareTo(minSuffix) < 0)) return false;
			}
			if (maxSuffix != null) {
				if ((! testSuffix.isPrefixOf(maxSuffix)) &&
					    (testSuffix.compareTo(maxSuffix) > 0)) return false;
			}
			return true;
		}
	}
	public boolean contains(Range other) {
		this.propagatePrefixIntoRange(0);
		other.propagatePrefixIntoRange(0);
		if (minSuffix.compareTo(other.minSuffix) > 0) return false;
		if (maxSuffix.compareTo(other.maxSuffix) < 0) return false;
		return true;
	}
}
