package dovetaildb.bagindex;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import dovetaildb.bytes.AbstractBytes;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.CompoundBytes;
import dovetaildb.bytes.SlicedBytes;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.WrappingQueryNode;
import dovetaildb.querynode.QueryNode.NextStatus;
import dovetaildb.util.MappedIterator;
import dovetaildb.util.Util;

public class PrefixCompressedBagIndex extends WrappingBagIndex {

	private static final long serialVersionUID = 6106684392808661661L;
	
	protected Bytes[] compressionLiterals;
	protected CompoundBytes[] mapping; // first part is the (uncompressed) prefix and second part is a comparison string
	protected int numCompressedBytes, numCompressedBits;
	protected int numCenteringBits;
	protected double LengthToRowRatio = 4.0;
	
	public int getNumCompressedBytes() { return numCompressedBytes; }
	
	public Bytes decompressPrefix(SlicedBytes compressed) {
		int mappingIdx = 0;
		for(int i=0; i<numCompressedBytes; i++) {
			mappingIdx = (mappingIdx << 8) | (compressed.get(0) & 0xff);
			compressed = compressed.subBytes(1);
		}
		CompoundBytes uncompressed = mapping[mappingIdx >> numCenteringBits];
		return uncompressed.getPrefix();
	}
	
	public Bytes compress(Bytes uncompressed) {
		if (uncompressed == null) return null;
		int uncompressedLen = uncompressed.getLength();
		if (uncompressedLen == 0) return compressionLiterals[0];
		int pos = Arrays.binarySearch(mapping, uncompressed);
		if (pos < 0) pos = (-pos) - 2; // need one before the insertion point
		Bytes prefix = mapping[pos].getPrefix();
		assert prefix.isPrefixOf(uncompressed);
		int prefixLen = prefix.getLength();
		Bytes suffix = SlicedBytes.make(uncompressed, prefixLen, 
				uncompressedLen - prefixLen);
		return CompoundBytes.make(compressionLiterals[pos], suffix);
	}

	private Bytes findCompressedPrefixContaining(Bytes uncompressed) { // TODO test
		if (uncompressed == null) return null;
		int uncompressedLen = uncompressed.getLength();
		if (uncompressedLen == 0) return ArrayBytes.EMPTY_BYTES;
		int pos = Arrays.binarySearch(mapping, uncompressed);
		if (pos < 0) pos = (-pos) - 2; // need one before the insertion point
		int startPos = pos;
		int endPos = pos;
		while (pos + 1 < mapping.length) {
			pos++;
			Bytes prefix = mapping[pos].getPrefix();
			if (! prefix.couldOverlapWith(uncompressed)) break;
			endPos = pos;
		}
		if (startPos != endPos) {
			Range range = new Range();
			range.setBoundsAndExtractPrefix(compressionLiterals[startPos], compressionLiterals[endPos]);
			return range.getPrefix();
		}
		Bytes prefix = mapping[startPos];
		assert prefix.isPrefixOf(uncompressed);
		int prefixLen = prefix.getLength();
		Bytes suffix = SlicedBytes.make(uncompressed, prefixLen, uncompressedLen - prefixLen);
		return CompoundBytes.make(compressionLiterals[pos], suffix);
	}


	class PrefixUncompressedBytes extends AbstractBytes {
		Bytes prefix;
		Bytes compressed;
		int compIndex;
		SlicedBytes sliceTemporary = new SlicedBytes(ArrayBytes.EMPTY_BYTES, 0);
		
		public PrefixUncompressedBytes() {}
		public void setCompressed(Bytes compressed) {
			this.compressed = compressed;
			sliceTemporary.reInitialize(compressed, 0, compressed.getLength());
			prefix = decompressPrefix(sliceTemporary);
			compIndex = sliceTemporary.getSlicePosition();
			sliceTemporary.reInitialize(ArrayBytes.EMPTY_BYTES, 0, 0);
		}
		public int get(int position) {
			int prefixLen = prefix.getLength();
			if (position < prefixLen) return prefix.get(position);
			return compressed.get(compIndex + (position - prefixLen));
		}
		public int getLength() {
			return prefix.getLength() + compressed.getLength() - compIndex;
		}
		public String toString() {
			return "Uncompressed(["+prefix.getAsString()+"]"+new SlicedBytes(compressed, compIndex, compressed.getLength() - compIndex)+")";
		}
		@Override
		public Bytes copy() {
			return copyInto(null);
		}
		@Override
		public Bytes copyInto(Bytes other) {
//			return super.copy();
			if (other == null) {
				other = new PrefixUncompressedBytes();
			}
			if (other instanceof PrefixUncompressedBytes) {
				PrefixUncompressedBytes puOther = (PrefixUncompressedBytes)other;
				puOther.compressed = compressed.copyInto(puOther.compressed);
				puOther.sliceTemporary.reInitialize(ArrayBytes.EMPTY_BYTES, 0, 0);
				puOther.compIndex = compIndex;
				puOther.prefix = prefix;
				return puOther;
			} else {
				return super.copy();
			}
		}
	}
	
	private QueryNode makeQueryNode(QueryNode subQueryNode) {
		if (subQueryNode == null) return null;
		return new PrefixUncompressedQueryNode(subQueryNode);
	}

	class PrefixUncompressedQueryNode extends WrappingQueryNode {
		PrefixUncompressedBytes term = new PrefixUncompressedBytes();
		private HashMap<Bytes, Bytes> prefixCache = new HashMap();
		
		public PrefixUncompressedQueryNode(QueryNode subQueryNode) {
			super(subQueryNode);
		}
		
		@Override
		public Bytes term() {
			term.setCompressed(subQueryNode.term());
			return term; 
		}
		
		@Override
		public QueryNode copy() {
			return new PrefixUncompressedQueryNode(subQueryNode.copy());
		}

		@Override
		public QueryNode specialize(Range range) {
			if (range != Range.OPEN_RANGE) {
				range.propagatePrefixIntoRange(0);
				range.setBoundsAndExtractPrefix(
						compress(range.getMinSuffix()),
						compress(range.getMaxSuffix()));
			}
			QueryNode subRet = subQueryNode.specialize(range);
			if (subRet == subQueryNode) return this;
			if (subRet == null) return null;
			return new PrefixUncompressedQueryNode(subRet);
		}
		
		@Override
		public void seek(long seekDoc, Bytes seekTerm) {
			subQueryNode.seek(seekDoc, compress(seekTerm));
		}

		private PrefixUncompressedBytes anyMatchingTerm = new PrefixUncompressedBytes();
		@Override
		public Bytes findAnyMatching(long docId, Bytes prefix) {
			Bytes term;
			if (prefix.getLength() == 0) {
				term = subQueryNode.findAnyMatching(docId, prefix);
			} else {
				Bytes min = compress(prefix);
				byte[] prefixBytes = prefix.getBytes();
				Util.incrementBinary(prefixBytes);
				Bytes max = compress(new ArrayBytes(prefixBytes));
				Range r = new Range(ArrayBytes.EMPTY_BYTES, ArrayBytes.EMPTY_BYTES, ArrayBytes.EMPTY_BYTES, true, true);
				r.setBoundsAndExtractPrefix(min, max);
				term = subQueryNode.findAnyMatching(docId, r.getPrefix());
			}
			if (term == null) return null;
			anyMatchingTerm.setCompressed(term);
			return anyMatchingTerm;
		}

		@Override
		public boolean positionSet(long docId, Bytes prefix) {
			this.positionDoc = docId;
			this.positionPrefix = prefix;
			Bytes compressedPrefix = prefixCache.get(prefix);
			if (compressedPrefix == null) {
				compressedPrefix = findCompressedPrefixContaining(prefix);
				prefixCache.put(prefix, compressedPrefix);
			}
			boolean found = subQueryNode.positionSet(docId, compressedPrefix);
			if (! found) return false;
			if (prefix.isPrefixOf(term())) {
				return true;
			} else {
				return positionNext();
			}
		}
		
		@Override
		public boolean positionNext() {
			do {
				boolean found = subQueryNode.positionNext();
				if (! found) return false;
			} while (! positionPrefix.isPrefixOf(term()));
			return true;
		}

		public String toString() {
			return "PrefixUncompressedQN("+super. subQueryNode+")";
		}

	}

	protected Bytes makeCompressionLiteral(int value) {
		byte[] compressedPrefix = new byte[numCompressedBytes];
		value <<= numCenteringBits;
		for(int i = numCompressedBytes-1; i >= 0; i--) {
			compressedPrefix[i] = (byte)(value & 0xFF);
			value >>= 8;
		}
		return new ArrayBytes(compressedPrefix);
	}
	
	public PrefixCompressedBagIndex(BagIndex inner, Bytes[] prefixes) {
		super(inner);
		mapping = new CompoundBytes[prefixes.length];
		Bytes prev = ArrayBytes.EMPTY_BYTES;
		for(int i=0; i<prefixes.length; i++) {
			Bytes cur = prefixes[i].flatten();
			int curLen = cur.getLength();
			int prevLen = prev.getLength();
			while(prevLen > 0 && prev.get(prevLen-1) == 255) {
				prevLen -= 1;
			}
			if (curLen >= prevLen) {
				mapping[i] = new CompoundBytes(cur, ArrayBytes.EMPTY_BYTES);
			} else {
				byte[] cmpBytes = new byte[prevLen-curLen];
				for(int j=curLen; j<prevLen; j++) {
					int thisByte = prev.get(j);
					if (j == prevLen-1) { // incr byte
						thisByte++;
					}
					cmpBytes[j-curLen] = (byte)thisByte;
				}
				// needs a compare key
				mapping[i] = new CompoundBytes(cur, new ArrayBytes(cmpBytes));
			}
			prev = cur;
		}
		assert (mapping.length & (mapping.length-1)) == 0; // assert length is a power of 2
		this.numCompressedBits = integerLogBase2RoundDown(mapping.length);
		numCompressedBytes = (numCompressedBits+7) / 8;

		// center the bits in the space
		int remainder = numCompressedBits % 8;
		if (remainder == 0) {
			numCenteringBits = 0;
		} else {
			numCenteringBits = (8 - remainder) / 2;
		}
		
		compressionLiterals = new Bytes[mapping.length];
		for(int i=0; i<mapping.length; i++) {
			compressionLiterals[i] = makeCompressionLiteral(i);
		}
	}
	
	@Override
	public long commitNewRev(Collection<EditRec> edits) {
		for(EditRec rec : edits) {
			rec.term = compress(rec.getTerm());
		}
		return subBagIndex.commitNewRev(edits);
	}
	
	@Override
	public QueryNode getRange(Range range, long revNum) {
		range.propagatePrefixIntoRange(0);
		Bytes minTerm = compress(range.getMinSuffix());
		Bytes maxTerm = compress(range.getMaxSuffix());
		range.setBoundsAndExtractPrefix(minTerm, maxTerm);
		return makeQueryNode(subBagIndex.getRange(range, revNum));
	}

	@Override
	public QueryNode getTerms(List<Bytes> terms, long revNum) {
		for(int i=terms.size()-1; i>=0; i--) {
			terms.set(i, compress(terms.get(i)));
		}
		return makeQueryNode(subBagIndex.getTerms(terms, revNum));
	}
	
	@Override
	public QueryNode getTerm(Bytes term, long revNum) {
		return makeQueryNode(subBagIndex.getTerm(compress(term), revNum));
	}
	
	@Override
	public long rebuild(Iterator<EditRec> edits) {
		// this capability is not yet ready, see comments in FsBlueSteelBagIndex.rebuild
		Iterator<EditRec> compressedItr = new MappedIterator<EditRec,EditRec>(edits) {
			@Override
			public EditRec map(EditRec edit) {
				edit.term = compress(edit.getTerm());
				return edit;
			}
		};
		return subBagIndex.rebuild(compressedItr);
	}	
	
	enum SlotTransition { SPECIALIZE, GENERALIZE, INCREMENT };
	static class SlotBufferItem {
		Bytes bytes;
		long hits;
		public SlotBufferItem(Bytes bytes, long hits) {
			this.bytes = bytes;
			this.hits = hits;
		}
		public String toString() {
			return "SlotBufferItem("+bytes.getAsString()+","+hits+")";
		}
	}
	static class SlotBuffer {
		public LinkedList<SlotBufferItem> slots = new LinkedList<SlotBufferItem>();
		/*
		 * transition types: specialize, generalize, increment
		 * XsYgX -> X*
		 * XsYsZ -> X*sZ
		 * XgYs(X+1) -> Xi(X+1)
		 * XgYsZ -> ---
		 * XiYgZ -> XgZ*
		 * XiYsZ -> ---
		 * XiYiZ -> ---
		 * XsYiZ -> X*sZ
		 * XgYgZ -> XgZ* 
		 * 
		 */
		private static SlotTransition getTransitionType(SlotBufferItem i1, SlotBufferItem i2) {
			if      (i1.bytes.isPrefixOf(i2.bytes)) return SlotTransition.SPECIALIZE;
			else if (i2.bytes.isPrefixOf(i1.bytes)) return SlotTransition.GENERALIZE;
			else {
				int l1 = i1.bytes.getLength();
				int l2 = i2.bytes.getLength();
				assert l1 == l2;
				assert SlicedBytes.make(i1.bytes,0,l1-1).equals(SlicedBytes.make(i2.bytes,0,l2-1));
				assert i1.bytes.get(l1-1) + 1 == i2.bytes.get(l2-1);
				return SlotTransition.INCREMENT;
			}
		}
		private static int NOT_COLLAPSABLE = -1;
		private static int AT_END = -2;
		
		public static long check(ListIterator<SlotBufferItem> pointer, long popThreshold) {
			if (! pointer.hasNext()) return AT_END;
			SlotBufferItem prev = pointer.next();
			if (! pointer.hasNext()) return AT_END;
			SlotBufferItem slot = pointer.next();
			if (! pointer.hasNext()) return AT_END;
			SlotBufferItem next = pointer.next();
			pointer.previous();
			pointer.previous();
			SlotTransition t1 = getTransitionType(prev, slot);
			SlotTransition t2 = getTransitionType(slot, next);
			boolean shiftHitsDown;
			if (t1 == SlotTransition.GENERALIZE) {
				if (t2 == SlotTransition.GENERALIZE) {
					shiftHitsDown = true;
				} else if (t2 == SlotTransition.INCREMENT) {
					return NOT_COLLAPSABLE;
				} else if (t2 == SlotTransition.SPECIALIZE) {
					// can work if it is an increment
					int len = prev.bytes.getLength();
					if (slot.hits == 0 && next.bytes.getLength() == len) {
						Bytes prevPrefix = SlicedBytes.make(prev.bytes, 0, len-1);
						Bytes nextPrefix = SlicedBytes.make(next.bytes, 0, len-1);
						if (prevPrefix.compareTo(nextPrefix) == 0 && 
								prev.bytes.get(len-1) + 1 == next.bytes.get(len-1)) {
							shiftHitsDown = true; // doesn't matter cause hits==0
						} else {
							return NOT_COLLAPSABLE;
						}
					} else {
						return NOT_COLLAPSABLE;
					}
				} else throw new RuntimeException();
			} else if (t1 == SlotTransition.INCREMENT) {
				if (t2 == SlotTransition.GENERALIZE) {
					shiftHitsDown = true;
				} else if (t2 == SlotTransition.INCREMENT) {
					return NOT_COLLAPSABLE;
				} else if (t2 == SlotTransition.SPECIALIZE) {
					return NOT_COLLAPSABLE;
				} else throw new RuntimeException();
			} else if (t1 == SlotTransition.SPECIALIZE) {
				if (t2 == SlotTransition.GENERALIZE) {
					shiftHitsDown = prev.bytes.isPrefixOf(next.bytes);
				} else if (t2 == SlotTransition.INCREMENT) {
					shiftHitsDown = false;
				} else if (t2 == SlotTransition.SPECIALIZE) {
					shiftHitsDown = false;
				} else throw new RuntimeException();
			} else throw new RuntimeException();
			// In the -S-G- case, it's possible the prev and next have the same byte
			// sequence, in which case they are redundant:
			boolean doDoubleRemove = (next.bytes.compareTo(prev.bytes) == 0);
			long lenDelta = 1 + slot.bytes.getLength() - (shiftHitsDown?next:prev).bytes.getLength();
			long hitFactor = (slot.hits * lenDelta) / (doDoubleRemove ? 2 : 1);
			if (hitFactor > popThreshold) {
				// not attractive enough
				return hitFactor;
			}
			pointer.remove();
			if (doDoubleRemove) {
				SlotBufferItem after = pointer.next();
				assert after == next;
				pointer.remove();
				prev.hits += next.hits;
				shiftHitsDown = false;
			}
			pointer.previous();
			if (pointer.hasPrevious()) pointer.previous();
			if (shiftHitsDown) {
				next.hits += slot.hits;
			} else {
				prev.hits += slot.hits;
				// if we're editing prev, we want to start next time from the item before it
				if (pointer.hasPrevious()) pointer.previous();
			}
			return Long.MAX_VALUE;
		}
		public void collapse(int size, long totalTerms) {
			long threshold = (totalTerms / size) >> 8;
			while(slots.size() > size) {
				long smallestUncollapsed = Long.MAX_VALUE;
				ListIterator<SlotBufferItem> pointer = slots.listIterator();
				while(slots.size() > size) {
					long szToCollapse = check(pointer, threshold);
					if (szToCollapse == Long.MAX_VALUE) {
						// removed
					} else if (szToCollapse == NOT_COLLAPSABLE) {
						// not collapse-able
					} else if (szToCollapse == AT_END) {
						break;
					} else {
						if (szToCollapse < smallestUncollapsed) {
							smallestUncollapsed = szToCollapse;
						}
					}
				}
				threshold = smallestUncollapsed;
			}
		}
	}
	private static class ByteTable {
		long hits=0;
		ByteTable[] tbl=null;
		long termHits=0;
		public void count(SlicedBytes term, long forkThreshold) {
			hits++;
			if (term.getLength() == 0) {
				termHits++;
			} else {
				if (tbl == null) {
					if (hits > forkThreshold) {
						tbl = new ByteTable[256];
					} else {
						return;
					}
				}
				int idx = term.get(0);
				ByteTable subTbl = tbl[idx];
				if (subTbl == null) {
				    subTbl = tbl[idx] = new ByteTable();
				}
				subTbl.count(term.subBytes(1), forkThreshold);
			}
		}

		public void buildSlotBuffer(Bytes prefix, long minHits, SlotBuffer slotBuffer) {
			if (tbl == null) {
				slotBuffer.slots.add(new SlotBufferItem(prefix, hits));
				return;
			}
			slotBuffer.slots.add(new SlotBufferItem(prefix, 0));
			for(int i=0; i<tbl.length; i++) {
				ByteTable sub = tbl[i];
				if (sub != null) {
					if (sub.hits >= minHits) {
						sub.buildSlotBuffer(CompoundBytes.make(prefix, ArrayBytes.SINGLE_BYTE_OBJECTS[i]), minHits, slotBuffer);
						slotBuffer.slots.add(new SlotBufferItem(prefix, 0));
					} else {
						slotBuffer.slots.getLast().hits += sub.hits;
					}
				}
			}
//			for(SlotBufferItem item : slotBuffer.slots) {
//				System.out.println(item.bytes+" -- "+item.hits);
//			}
		}
	}
	private static final Bytes[] EMPTY_BYTE_ARRAY = new Bytes[]{};
	private static int integerLogBase2RoundDown(long i) {
		if (i==0) return 0;
		return 63-Long.numberOfLeadingZeros(i);
	}
	public static Bytes[] determineCompressionTable(BagIndex index, double lengthToRowRatio) {
		long forkThreshold = 25;
		QueryNode query = index.getRange(Range.OPEN_RANGE, index.getCurrentRevNum());
		ByteTable root = new ByteTable();
		if (query == null) return EMPTY_BYTE_ARRAY;
		SlicedBytes slice = new SlicedBytes(ArrayBytes.EMPTY_BYTES, 0);
		do {
			Bytes term = query.term();
			slice.reInitialize(query.term(), 0, term.getLength());
			root.count(slice, forkThreshold);
		} while(query.anyNext());
		long counted = root.hits;
		int numSlots = 1 << integerLogBase2RoundDown((long)(Math.sqrt(counted)/lengthToRowRatio));
		long minHits = (counted / numSlots) >> 8;
		SlotBuffer slotBuffer = new SlotBuffer();
		root.buildSlotBuffer(ArrayBytes.EMPTY_BYTES, minHits, slotBuffer);
		slotBuffer.collapse(numSlots, counted);
		Bytes[] slots = new Bytes[numSlots];
		int i=0;
		Util.logger.log(Level.FINEST, "New prefix compression table (size="+slots.length+") follows for "+counted+" terms in "+index.getHomedir());
		for(SlotBufferItem item : slotBuffer.slots) {
			item.bytes = item.bytes.flatten();
			Util.logger.log(Level.FINEST, i+" : "+item.bytes.toString());
			slots[i++] = item.bytes;
		}
		while(i < numSlots) {
			slots[i++] = ArrayBytes.EMPTY_BYTES;
		}
		return slots;
	}

	
	
}
