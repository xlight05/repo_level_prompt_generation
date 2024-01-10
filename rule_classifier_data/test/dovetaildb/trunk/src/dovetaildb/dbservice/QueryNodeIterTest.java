package dovetaildb.dbservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import dovetaildb.bagindex.EditRec;
import dovetaildb.bagindex.MemoryBlueSteelBagIndex;
import dovetaildb.bagindex.PrefixCompressedBagIndex;
import dovetaildb.bagindex.Range;
import dovetaildb.bagindex.TrivialBagIndex;
import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;
import dovetaildb.querynode.FilteredQueryNode;
import dovetaildb.querynode.LiteralRangeQueryNode;
import dovetaildb.querynode.OrderedOrQueryNode;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.QueryNodeTest;
import dovetaildb.util.Util;

public class QueryNodeIterTest extends TestCase {

	private PrefixCompressedBagIndex index;
	private long revNum;

	@Override
	public void setUp() {
		EditRec[] edits;
		edits = new EditRec[]{
				 new EditRec(1, ArrayBytes.fromString("{age:s0036")),
				 new EditRec(2, ArrayBytes.fromString("{age:s0054")),
				 new EditRec(3, ArrayBytes.fromString("{age:s0031")),
				 new EditRec(4, ArrayBytes.fromString("{age:s0041")),
				 new EditRec(5, ArrayBytes.fromString("{age:s0051")),
				 new EditRec(6, ArrayBytes.fromString("{age:s0062"))
				 };
		
		ArrayList<QueryNode> q = new ArrayList<QueryNode>();
		q.add(new LiteralRangeQueryNode(edits));
		Bytes[] prefixes = new Bytes[]{
				ArrayBytes.fromString(""),
				ArrayBytes.fromString("{age:s002"),
				ArrayBytes.fromString("{age:s003"),
				ArrayBytes.fromString("{age:s004"),
				ArrayBytes.fromString("{age:s005"),
				ArrayBytes.fromString("{age:s006"),
				ArrayBytes.fromString("{age:s007"),
				ArrayBytes.fromString(""),
		};
		index = new PrefixCompressedBagIndex(new MemoryBlueSteelBagIndex(), prefixes);
		revNum = index.commitNewRev(Arrays.asList(edits));
	}
	
	private QueryNode makeQN() {
		return index.getRange(Range.OPEN_RANGE, revNum);
	}
	
	public void testBasic() {
		QueryNodeIter iter;
		boolean  optimized;
		
		iter = new QueryNodeIter(makeQN(), makeQN(), index, revNum);
		assertTrue(iter.hasNext());
		assertEquals("{\"age\":\"0036\"}", Util.jsonEncode(iter.next()));
		assertTrue(iter.hasNext());
		assertEquals("{\"age\":\"0054\"}", Util.jsonEncode(iter.next()));
		assertTrue(iter.hasNext());
		assertEquals("{\"age\":\"0031\"}", Util.jsonEncode(iter.next()));
		assertTrue(iter.hasNext());
		assertEquals("{\"age\":\"0041\"}", Util.jsonEncode(iter.next()));
		assertTrue(iter.hasNext());
		assertEquals("{\"age\":\"0051\"}", Util.jsonEncode(iter.next()));
		assertTrue(iter.hasNext());
		assertEquals("{\"age\":\"0062\"}", Util.jsonEncode(iter.next()));
		assertFalse(iter.hasNext());
		
		iter = new QueryNodeIter(makeQN(), makeQN(), index, revNum);
		optimized = iter.specialize(Util.literalSMap().p("age", Util.literalList().a("[]").a("0039").a("0100")));
		assertTrue(optimized);
		optimized = iter.specialize(Util.literalSMap().p("age", Util.literalList().a("[]").a("0039").a("0100")));
		assertFalse(optimized);
		optimized = iter.specialize(Util.literalSMap().p("age", Util.literalList().a("[]").a("0050").a("0100")));
		assertTrue(optimized);
		assertEquals("{\"age\":\"0054\"}", Util.jsonEncode(iter.next()));
		assertEquals("{\"age\":\"0051\"}", Util.jsonEncode(iter.next()));
		assertEquals("{\"age\":\"0062\"}", Util.jsonEncode(iter.next()));
		assertFalse(iter.hasNext());
	}

}
