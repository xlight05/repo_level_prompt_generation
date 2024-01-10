package dovetaildb.bagindex;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.SlicedBytes;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.QueryNode.NextStatus;
import dovetaildb.util.Util;

public abstract class BagIndexTest  extends TestCase {

	protected abstract BagIndex createIndex();
	
	private static final int NUM_EDITS = 1024;
	private static final int MAX_IDS = NUM_EDITS/2;
	
	protected Bytes t0 = new ArrayBytes(new byte[]{});
	protected Bytes t1 = new ArrayBytes(new byte[]{'0','1'});
	protected Bytes t2 = new ArrayBytes(new byte[]{'0','1','0','1','0','1'});
	protected Bytes t34pre = new ArrayBytes(new byte[]{'1','2','3'});
	protected Bytes t3 = new ArrayBytes(new byte[]{'1','2','3','4','5','6','7','8','0'});
	protected Bytes t4 = new ArrayBytes(new byte[]{'1','2','3','4','5','6','7','8','9','0'});
	protected Bytes t5 = new ArrayBytes(new byte[]{'2','4','6','8','0','0','1','1','0','0','1','1','0','0','1','1','0','0'});
	
	protected File tempStoreDir = null;
	protected BagIndex index;
	
	@Override
	public void setUp() {
		index = createIndex();
		tempStoreDir = Util.createTempDirectory("BagIndexTest-"+this.getClass().getSimpleName());
		index.setHomedir(tempStoreDir.getAbsolutePath());
	}

	@Override
	public void tearDown() {
		if (tempStoreDir != null) {
			Util.deleteDirectory(tempStoreDir);
		}
	}
	
	public void checkEmpty(long revNum) {
		assertEquals(null,index.getRange(new Range(t0, t1, t5, true, true), revNum));
		assertEquals(null,index.getRange(new Range(t0, t3, t3, false, false),  revNum));
		assertEquals(null,index.getTerm(t1, revNum));
	}
	
	public void checkTwoInserts(long revNum) {
		long doc0, doc1, doc2;
		QueryNode s;
		QueryNode allTerms = index.getRange(new Range(ArrayBytes.EMPTY_BYTES, null, null, true, true), revNum);
		s = index.getTerm(t1, revNum);
		assertTrue(s.next());
		doc0 = s.doc();
		assertFalse(s.next());
		
		allTerms.seek(doc0, ArrayBytes.EMPTY_BYTES);
		assertEquals(0, allTerms.term().compareTo(t1));
		assertEquals(NextStatus.NEXT_TERM, allTerms.nextTerm());
		assertEquals(0, allTerms.term().compareTo(t2));
		assertEquals(NextStatus.NEXT_TERM, allTerms.nextTerm());
		assertEquals(0, allTerms.term().compareTo(t4));
		assertEquals(NextStatus.NEXT_TERM, allTerms.nextTerm());
		assertEquals(0, allTerms.term().compareTo(t5));
		assertEquals(NextStatus.AT_END, allTerms.nextTerm());
		
		s = index.getTerm(t1, revNum);
		doc1 = s.doc();
		assertTrue(s.next());
		doc2 = s.doc();
		assertFalse(s.next());
		assertTrue(doc1 < doc2);
		
		allTerms = index.getRange(new Range(ArrayBytes.EMPTY_BYTES, null, null, true, true), revNum);
		allTerms.seek(doc1, ArrayBytes.EMPTY_BYTES);
		assertEquals(0, allTerms.term().compareTo(t1));
		assertEquals(NextStatus.NEXT_TERM, allTerms.nextTerm());
		assertEquals(0, allTerms.term().compareTo(t3));
		assertEquals(NextStatus.NEXT_DOC, allTerms.nextTerm());
		
		s = index.getRange(new Range(t0, t3, t4, false, false),  revNum);
		assertNull(s);
		s = index.getRange(new Range(t0, t3, t4, false, true), revNum);
		assertNotNull(s);
		assertFalse(s.next());
		s = index.getRange(new Range(t0, t3, t4, true, false),  revNum);
		assertNotNull(s);
		assertFalse(s.next());
		s = index.getRange(new Range(t0, t3, t4, true, true),  revNum);
		assertNotNull(s);
		assertTrue(s.next());
		assertFalse(s.next());
		
		allTerms = index.getRange(new Range(ArrayBytes.EMPTY_BYTES, null, null, true, true), revNum);
		Range r = new Range(t1, null, null, true, true);
		s = allTerms.specialize(r);
		assertNotNull(s);

		r = new Range(t3, t3, t3, true, true);
		s = allTerms.specialize(r);
		// could be null (no results expected)
		if (s != null) { // if not null, make sure no results match
			do {
				assertFalse(r.matches(s.term()));
			} while(s.nextTerm() != NextStatus.AT_END);
		}
	}

	long initialEmptyRev, twoInsertsRev;

	public void testAll() {
		
		initialEmptyRev = index.getCurrentRevNum();
		
		checkEmpty(initialEmptyRev);
		
		// empty commit
		checkEmpty(index.commitNewRev(new ArrayList<EditRec>()));
		
		// test inserts
		ArrayList<EditRec> edits = new ArrayList<EditRec>();
		edits.add(new EditRec(1, t1));
		edits.add(new EditRec(2, t1));
		edits.add(new EditRec(2, t2));
		edits.add(new EditRec(1, t3));
		edits.add(new EditRec(2, t4));
		edits.add(new EditRec(2, t5));
		twoInsertsRev = index.commitNewRev(edits);

		checkTwoInserts(twoInsertsRev);
		checkEmpty(initialEmptyRev); // old rev should still appear empty
	}
	
	private ArrayBytes randTerm(Random r) {
		return new ArrayBytes(randTermBytes(r));
	}

	protected byte[] ALPHABET = new byte[]{'a','b','c','d','e','f','g'};
	
	private byte[] randTermBytes(Random r) {
		byte[] field = new byte[r.nextInt(5)+1];
		for(int j=0; j<field.length; j++) {
			field[j] = (ALPHABET)[r.nextInt(ALPHABET.length)];
		}
		return field;
	}
	
	public void testApplyDocEdits_complex() {
		Random r = new Random(12345);
		ArrayList<EditRec> edits;
		long ct = 0;
		
		edits = new ArrayList<EditRec>();
		HashSet<String> seen = new HashSet<String>();
		for(int i=0; i<NUM_EDITS; i++) {
			long id = r.nextInt(MAX_IDS);
			byte[] field = randTermBytes(r);
			String rep = Util.bytesAsString(field)+" "+id;
			if (!seen.contains(rep)) {
				seen.add(rep);
				edits.add(new EditRec(id, new ArrayBytes(field), false));
			}
		}
		int startDiv = edits.size()/2;
		List<EditRec> removeQueue = new ArrayList<EditRec>(edits.subList(0, startDiv));
		List<EditRec> addQueue    = new ArrayList<EditRec>(edits.subList(startDiv, edits.size()));
		edits = new ArrayList<EditRec>(removeQueue);
		EditRec.sortByTerm(edits);
		
		long revNum = index.commitNewRev(EditRec.cloneList(edits));
		
		QueryNode node = index.getRange(Range.OPEN_RANGE, revNum);
		long expectedCt = removeQueue.size(); 
		assertNotNull(node);
		for(ct=1; ct<expectedCt; ct++) {
			assertTrue(node.nextTerm() != NextStatus.AT_END);
		}
		assertEquals(NextStatus.AT_END, node.nextTerm());

		for(EditRec edit : removeQueue) { edit.isDeletion = true; }
		int modCap=1;
		while( (!addQueue.isEmpty()) && (!removeQueue.isEmpty()) ) {
			tryQueries(removeQueue, revNum, r);

			int numRemovals = r.nextInt(Math.min(modCap, removeQueue.size()+1));
			int numAdds     = r.nextInt(Math.min(modCap, addQueue.size()+1));
			
			edits = new ArrayList<EditRec>();
			edits.addAll(removeQueue.subList(0, numRemovals));
			List<EditRec> curAdds = addQueue.subList(0, numAdds);
//			System.out.println("removals "+edits);
//			System.out.println("adds "+curAdds);
			edits.addAll(curAdds);
			EditRec.sortByTerm(edits);
//			long checkpoint = r.nextLong();
//			System.out.println("checkpoint "+checkpoint);
//			if (checkpoint == 4723830616276967586L) {
//				System.out.println("checkpoint "+checkpoint);
//			}
			revNum = index.commitNewRev(EditRec.cloneList(edits));
			
			removeQueue = removeQueue.subList(numRemovals, removeQueue.size());
			removeQueue = new ArrayList<EditRec>(removeQueue);
			for(EditRec edit : curAdds) { edit.isDeletion = true; }
			removeQueue.addAll(curAdds);
			addQueue    =    addQueue.subList(numAdds,     addQueue.size());
			expectedCt += numAdds - numRemovals;
			
			ArrayList<EditRec> tmpQueue = new ArrayList<EditRec>(removeQueue);
			EditRec.sortById(tmpQueue);
			int tmpIdx = 0;
			QueryNode n = index.getRange(Range.OPEN_RANGE, revNum);
			float jumpChance = 0.2f;
			while(n != null) {
				if (r.nextFloat() < jumpChance) {
					jumpChance *= 0.9;
					tmpIdx = r.nextInt(tmpQueue.size());
					long docId = tmpQueue.get(tmpIdx).docId;
//					System.out.println("Jump to "+docId);
					while (tmpIdx > 0 && tmpQueue.get(tmpIdx-1).docId == docId) {
						tmpIdx--;
					}
					n.seek(docId, ArrayBytes.EMPTY_BYTES);
				}
				assertEquals(n.doc(), tmpQueue.get(tmpIdx).docId);
				assertEquals(n.term(), tmpQueue.get(tmpIdx).term);
				if (n.nextTerm() == QueryNode.NextStatus.AT_END) break;
				tmpIdx++;
			}

			modCap += 2;
		}
	}

	
	
	
	protected enum MatchReq {mustmatch, nomatch, maymatch}
	protected static interface Filter<T> {
		MatchReq filter(T item);
	}
	
	private void tryQueries(List<EditRec> recs, long revNum, Random r) {
		ArrayList<EditRec> byId = new ArrayList<EditRec>(recs);
		EditRec.sortById(byId);
		for(int i=0; i<200; i++) {
			tryQuery(byId, revNum, r);
		}
	}
	private Range genRange(Random r) {
		Bytes pre = randTerm(r);
		Bytes r1 = r.nextBoolean() ? null : randTerm(r);
		Bytes r2 = r.nextBoolean() ? null : randTerm(r);
		Bytes min, max;
		if (r1 != null && r2 != null) {
			min = (r1.compareTo(r2) < 0) ? r1 : r2;
			max = (r1.compareTo(r2) < 0) ? r2 : r1;
		} else {
			min = r1;
			max = r2;
		}
		boolean inclMin = r.nextBoolean();
		boolean inclMax = r.nextBoolean();
		return new Range(pre, min, max, inclMin, inclMax);
	}
	private void tryQuery(List<EditRec> byId, long revNum, Random r) {
		Filter<Bytes> matches = null;
		QueryNode node = null;
//		long y = r.nextLong();
//		System.out.println(" NEW ONE "+y);
//		if (y == 6290374244227081246l) {
//			System.out.println("Y "+y);
//		}
		switch (r.nextInt(2)) {
		case 0:
			final ArrayList<Bytes> termsForQuery = new ArrayList<Bytes>();
			final ArrayList<Bytes> terms = new ArrayList<Bytes>();
			for(int i=r.nextInt(10); i>=0; i--) {
				Bytes bytes = randTerm(r);
				terms.add(bytes);
				termsForQuery.add(bytes.copy());
			}
			node = index.getTerms(termsForQuery, revNum);
			matches = new Filter<Bytes>(){
				public MatchReq filter(Bytes item) {
					return terms.contains(item) ? MatchReq.mustmatch : MatchReq.nomatch;
				}
			};
			break;
		case 1:
			Range range = genRange(r);
			range.flattenTerms();
			boolean relaxed = false;
			Range spec = null;
			if (r.nextBoolean()) {
				node = index.getRange(new Range(range), revNum);
			} else {
				spec = new Range(range);
				Bytes prefix = range.getPrefix();
				Bytes min = range.getMinSuffix();
				Bytes max = range.getMaxSuffix();
				if (min == null && max == null) {
					range.setPrefix(new SlicedBytes(prefix, 0, r.nextInt(prefix.getLength()+1)));
				} else if (min == null) {
					range.setMaxSuffix(new SlicedBytes(max, 0, r.nextInt(max.getLength()+1)));
				} else {
					range.setMinSuffix(new SlicedBytes(min, 0, r.nextInt(min.getLength()+1)));
				}
				spec.flattenTerms();
				range.flattenTerms();
				node = index.getRange(new Range(range), revNum);
//				while (node != null) {
//					if (r.nextBoolean()) break;
//					if (node.nextTerm() == NextStatus.AT_END) break;
//				}
				if (node != null) {
					QueryNode specializedNode = node.specialize(new Range(spec));
					range.flattenTerms();
					relaxed = (specializedNode != node);
					if (! relaxed) {
						specializedNode = index.getRange(new Range(range), revNum);
					}
					node = specializedNode;
				}
			}
			final Range fRange = range;
			final boolean fRelaxed = relaxed;
			final Range fSpecialized = spec;
			matches = new Filter<Bytes>() {
				public MatchReq filter(Bytes item) {
					if (fRelaxed) {
						if (! fRange.matches(item)) return MatchReq.nomatch;
						if (fRange.matches(item) && fSpecialized.matches(item)) return MatchReq.mustmatch;
						return MatchReq.maymatch;
					} else {
						return fRange.matches(item) ? MatchReq.mustmatch : MatchReq.nomatch;
					}
				}
			};
		}
		boolean isNext = (node != null);
		MatchReq matchreq = null;
		for(EditRec edit : byId) {
			matchreq = matches.filter(edit.term);
			if (matchreq != MatchReq.nomatch) {
				if (matchreq == MatchReq.mustmatch) {
					if (node == null) {
						assertTrue(node != null);
					}
				}
//				System.out.println(edit.docId+" "+edit.term+" : "+node);
				if (node != null) {
//					long x = r.nextLong();
//					if (x == -4914999976611305416l) {
//						System.out.println("X "+x);
//					}
//					System.out.println("X "+x);
//					System.out.println(" expected "+edit+" found "+node.doc()); // +" : "+node.term());
					if (node.doc() == edit.docId && node.term().equals(edit.term)) {
						isNext = node.nextTerm() != NextStatus.AT_END;
					} else {
						if (matchreq == MatchReq.mustmatch) {
							fail();
						}
					}
				}
			}
		}
		if (isNext) {
			assertFalse(isNext);
		}
	}
	
}
