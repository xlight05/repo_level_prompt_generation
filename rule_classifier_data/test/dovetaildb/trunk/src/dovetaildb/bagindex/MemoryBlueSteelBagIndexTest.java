package dovetaildb.bagindex;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dovetaildb.bagindex.BlueSteelBagIndex.MemoryPostingNode;
import dovetaildb.bagindex.BlueSteelBagIndex.MemoryTokenTable;
import dovetaildb.bagindex.BlueSteelBagIndex.MergeIterator;
import dovetaildb.bagindex.BlueSteelBagIndex.ProbabilisticBalancingPolicy;
import dovetaildb.bagindex.BlueSteelBagIndex.SegmentPush;
import dovetaildb.bagindex.BlueSteelBagIndex.TokenRec;
import dovetaildb.bagindex.BlueSteelBagIndex.TokenTable;
import dovetaildb.bagindex.BlueSteelBagIndex.TraversalStack;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.SlicedBytes;



public class MemoryBlueSteelBagIndexTest extends BlueSteelBagIndexTest {

	@Override
	protected BagIndex createIndex() {
		MemoryBlueSteelBagIndex index = new MemoryBlueSteelBagIndex();
		index.setTermTableDepth(2);
		return index;
	}


	private ArrayBytes bytes(String s) {
		try {
			return new ArrayBytes(s.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public void testMergeIterator() {
		ArrayList<String> alpha = new ArrayList<String>();
		alpha.add("Phil");
		alpha.add("Zax");
		ArrayList<String> beta  = new ArrayList<String>();
		beta.add("Alice");
		beta.add("Phil");
		class StringMergeIterator extends MergeIterator<String,String> {
			public StringMergeIterator(Iterator<String> a, Iterator<String> b) {
				super(a,b);
			}
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		}
		StringMergeIterator i = new StringMergeIterator(alpha.iterator(), beta.iterator());
		String l,r;
		i.next(); l = i.getLeft(); r = i.getRight();
		assertEquals(null, l);
		assertEquals("Alice", r);
		i.next(); l = i.getLeft(); r = i.getRight();
		assertEquals("Phil", l);
		assertEquals("Phil", r);
		i.next(); l = i.getLeft(); r = i.getRight();
		assertEquals("Zax", l);
		assertEquals(null, r);
		i.next(); l = i.getLeft(); r = i.getRight();
		assertEquals(null, l);
		assertEquals(null, r);
	}

	public void compareSegments(SegmentPush push, long[] docIds) {
		TraversalStack traversal = new TraversalStack(push);
		int i=0;
		while(traversal.nextOrDown()) {
			assertEquals(docIds[i++], traversal.getCurrent().getDocId());
		}
		assertFalse(traversal.nextOrDown());
	}
	
	public void testSpliceSegmentsIntoSegmentPush() {
		
		ArrayList<MemoryPostingNode> nodes = new ArrayList<MemoryPostingNode>();
		MemoryPostingNode node3 = new MemoryPostingNode(3, new ArrayBytes(new byte[]{53}));
		nodes.add(node3);
		ArrayList<MemoryPostingNode> subnodes1 = new ArrayList<MemoryPostingNode>();
		MemoryPostingNode node5 = new MemoryPostingNode(5, new ArrayBytes(new byte[]{53}));
		subnodes1.add(node5);
		MemoryPostingNode node6 = new MemoryPostingNode(6, new ArrayBytes(new byte[]{53}));
		subnodes1.add(node6);
		SegmentPush sub1 = new SegmentPush(subnodes1);
		MemoryPostingNode node7 = new MemoryPostingNode(sub1, 7, new ArrayBytes(new byte[]{57}));
		nodes.add(node7);
		MemoryPostingNode node9 = new MemoryPostingNode(null, 9, new ArrayBytes(new byte[]{59}));
		nodes.add(node9);
		SegmentPush push = new SegmentPush(nodes);

		SegmentPush o;
		List<EditRec> edits = new ArrayList<EditRec>();
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(2, new ArrayBytes(new byte[]{0}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{2,3,5,6,7,9});
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(9, new ArrayBytes(new byte[]{127}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,7,9,9});

		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(7, new ArrayBytes(new byte[]{0}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,7,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(3, new ArrayBytes(new byte[]{127}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,3,5,6,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(5, new ArrayBytes(new byte[]{127}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,5,6,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(6, new ArrayBytes(new byte[]{127}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,6,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(8, new ArrayBytes(new byte[]{127}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,7,8,9});

		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node3, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{5,6,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node5, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,6,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node6, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node7, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,9});
	
		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node9, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,7});
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(2, new ArrayBytes(new byte[]{0}), false));
		edits.add(makeEdit(node3, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{2,5,6,7,9});
		
		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node3, true));
		edits.add(new EditRec(4, new ArrayBytes(new byte[]{0}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{4,5,6,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(7, new ArrayBytes(new byte[]{0}), false));
		edits.add(makeEdit(node7, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,7,9});

		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node9, true));
		edits.add(new EditRec(10, new ArrayBytes(new byte[]{0}), false));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,7,10});

		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(8, new ArrayBytes(new byte[]{0}), false));
		edits.add(makeEdit(node9, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5,6,7,8});

		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node3, true));
		edits.add(makeEdit(node5, true));
		edits.add(makeEdit(node6, true));
		edits.add(makeEdit(node7, true));
		edits.add(makeEdit(node9, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{});

		edits = new ArrayList<EditRec>();
		edits.add(makeEdit(node6, true));
		edits.add(makeEdit(node7, true));
		edits.add(makeEdit(node9, true));
		o = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		compareSegments(o, new long[]{3,5});
	}

	private EditRec makeEdit(MemoryPostingNode node, boolean isDel) {
		return new EditRec(node.getDocId(), node.getToken(), isDel);
	}

	public void testBalanceSegmentPush() {
		SegmentPush o;
		ArrayList<MemoryPostingNode> nodes = new ArrayList<MemoryPostingNode>();
		nodes.add(new MemoryPostingNode(1L, new ArrayBytes(new byte[]{11})));
		nodes.add(new MemoryPostingNode(2L, new ArrayBytes(new byte[]{22})));
		nodes.add(new MemoryPostingNode(3L, new ArrayBytes(new byte[]{33})));
		nodes.add(new MemoryPostingNode(4L, new ArrayBytes(new byte[]{44})));
		nodes.add(new MemoryPostingNode(5L, new ArrayBytes(new byte[]{55})));
		nodes.add(new MemoryPostingNode(6L, new ArrayBytes(new byte[]{66})));
		nodes.add(new MemoryPostingNode(7L, new ArrayBytes(new byte[]{77})));
		nodes.add(new MemoryPostingNode(8L, new ArrayBytes(new byte[]{88})));
		nodes.add(new MemoryPostingNode(9L, new ArrayBytes(new byte[]{99})));
		nodes.add(new MemoryPostingNode(10L,new ArrayBytes(new byte[]{00})));
		SegmentPush push = new SegmentPush(nodes);
		o=BlueSteelBagIndex.balanceSegmentPush(push, new ProbabilisticBalancingPolicy(4, 0.5f, 0.0f));
		compareSegments(o, new long[]{1,2,3,4,5,6,7,8,9,10});
		assertEquals(10, o.getCount());
		assertEquals(3, o.getTopLevelCount());
		o=BlueSteelBagIndex.balanceSegmentPush(push, new ProbabilisticBalancingPolicy(2, 0.5f, 0.0f));
		compareSegments(o, new long[]{1,2,3,4,5,6,7,8,9,10});
		assertEquals(10, o.getCount());
		assertEquals(2, o.getTopLevelCount());
	}
	
	public void testOrganicBalancing() {
		ArrayList<MemoryPostingNode> nodes = new ArrayList<MemoryPostingNode>();
		nodes.add(new MemoryPostingNode(3L, new ArrayBytes(new byte[]{33})));
		nodes.add(new MemoryPostingNode(7L, new ArrayBytes(new byte[]{77})));
		SegmentPush push = new SegmentPush(nodes);
		ArrayList<EditRec> edits = new ArrayList<EditRec>();
		edits.add(new EditRec(2L, new ArrayBytes(new byte[]{22})));
		push = BlueSteelBagIndex.spliceEditsIntoSegmentPush(edits, push);
		push.getTextDisplay();
		push = BlueSteelBagIndex.balanceSegmentPush(push, new ProbabilisticBalancingPolicy(5, 0.5f, 0.0f));
		push.getTextDisplay();
		push.getTextDisplay();
	}
	
	public void testApplyEditsToTokenTable() {
		
		List<EditRec> edits;
		TokenTable o;
		TokenRec tr;
		Iterator<TokenRec> i;
		Iterator<TokenRec> subi;
		
		ArrayList<TokenRec> tokenRecs = new ArrayList<TokenRec>();
		TokenTable root = new MemoryTokenTable(ArrayBytes.EMPTY_BYTES, tokenRecs);
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(1, bytes("age:13"), false));
		edits.add(new EditRec(1, bytes("name:phil"), false));

		o=BlueSteelBagIndex.applyEditsToTokenTable(edits, root, new ProbabilisticBalancingPolicy(10, 0.5f, 0.0f), 0);
		i=o.getTokenRecs();
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('a', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{1});
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('n', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{1});
		assertFalse(i.hasNext());
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(2, bytes("age:14"), false));
		edits.add(new EditRec(2, bytes("name:joe"), false));
		o=BlueSteelBagIndex.applyEditsToTokenTable(edits, o, new ProbabilisticBalancingPolicy(10, 0.5f, 0.0f), 0);
//		System.out.println(o);
		
		i=o.getTokenRecs();
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('a', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{1,2});
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('n', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{1,2});
		assertFalse(i.hasNext());

		// test mutilevel stuff
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(3, bytes("age:14"), false));
		edits.add(new EditRec(3, bytes("foo:yes"), false));
		o=BlueSteelBagIndex.applyEditsToTokenTable(edits, o, new ProbabilisticBalancingPolicy(10, 0.5f, 0.0f), 1);
		
		i=o.getTokenRecs();
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('a', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{1,2,3});
		
		subi = tr.tokenTable.getTokenRecs();
		assertTrue(subi.hasNext());
		tr = subi.next();
		assertFalse(subi.hasNext());
		assertEquals('g', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{3});
		assertNull(tr.tokenTable);
		
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('f', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{3});
		
		subi = tr.tokenTable.getTokenRecs();
		assertTrue(subi.hasNext());
		tr = subi.next();
		assertFalse(subi.hasNext());
		assertEquals('o', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{3});
		assertNull(tr.tokenTable);
		
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('n', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{1,2});
		assertNull(tr.tokenTable);
		assertFalse(i.hasNext());
		
		// test deletions
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(1, bytes("age:13"), true));
		edits.add(new EditRec(3, bytes("age:14"), true));
		edits.add(new EditRec(3, bytes("foo:yes"), true));
		o=BlueSteelBagIndex.applyEditsToTokenTable(edits, o, new ProbabilisticBalancingPolicy(10, 0.5f, 0.0f), 1);
		
		i=o.getTokenRecs();
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('a', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{2});
		
		subi = tr.tokenTable.getTokenRecs();
		assertFalse(subi.hasNext());
		
		assertTrue(i.hasNext());
		tr = i.next();
		assertEquals('n', tr.getToken());
		compareSegments(tr.getSegmentPush(), new long[]{1,2});
		assertNull(tr.tokenTable);
		assertFalse(i.hasNext());
		
	}
	
	public void testDescend() {
		List<EditRec> edits;
		ArrayList<TokenRec> tokenRecs = new ArrayList<TokenRec>();
		TokenTable root = new MemoryTokenTable(ArrayBytes.EMPTY_BYTES, tokenRecs);
		
		edits = new ArrayList<EditRec>();
		edits.add(new EditRec(1, bytes("a"), false));
		edits.add(new EditRec(1, bytes("brother:yes"), false));
		edits.add(new EditRec(1, bytes("name:phil"), false));

		root=BlueSteelBagIndex.applyEditsToTokenTable(edits, root, new ProbabilisticBalancingPolicy(10, 0.5f, 0.0f), 2); // "2" here means we get three tiers

		SlicedBytes sb;
		TokenRec tr;
		sb = new SlicedBytes(ArrayBytes.fromString("brot"), 0, 4);
		tr = root.descend(sb);
		assertEquals(3, sb.getSlicePosition());
		assertEquals('o', (char)tr.token);
		
		sb = new SlicedBytes(ArrayBytes.fromString("bro"), 0, 3);
		tr = root.descend(sb);
		assertEquals(3, sb.getSlicePosition());
		assertEquals('o', (char)tr.token);
		assertEquals(0, sb.getLength());
		
		sb = new SlicedBytes(ArrayBytes.fromString(""), 0, 0);
		tr = root.descend(sb);
		assertNull(tr);
		assertEquals(0, sb.getLength());
		
		sb = new SlicedBytes(ArrayBytes.fromString("a"), 0, 1);
		tr = root.descend(sb);
		assertEquals(1, sb.getSlicePosition());
		assertEquals('a', (char)tr.token);
		assertEquals(0, sb.getLength());
		
		sb = new SlicedBytes(ArrayBytes.fromString("brXX"), 0, 4);
		tr = root.descend(sb);
		assertNull(tr);
		assertTrue(sb.getLength() > 0);
		
		sb = new SlicedBytes(ArrayBytes.fromString("aXXX"), 0, 4);
		tr = root.descend(sb);
		assertNull(tr);
		assertTrue(sb.getLength() > 0);
		
		sb = new SlicedBytes(ArrayBytes.fromString("XXXX"), 0, 4);
		tr = root.descend(sb);
		assertNull(tr);
		assertTrue(sb.getLength() > 0);
		
	}
}
