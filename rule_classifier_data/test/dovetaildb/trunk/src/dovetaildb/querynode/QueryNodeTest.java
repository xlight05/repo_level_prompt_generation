package dovetaildb.querynode;

import dovetaildb.bagindex.EditRec;
import dovetaildb.bytes.ArrayBytes;
import junit.framework.TestCase;

public class QueryNodeTest extends TestCase {
	/**
	 * resulting query node should have:
	 * 2  age:32
	 * 2  id:002
	 * 2  name:phil
	 * 4  id:004
	 * 6  idle:true
	 * 6  id:006
	 * @return
	 */
	public static QueryNode testingQueryNode() {
		return new LiteralRangeQueryNode(new EditRec[]{
				new EditRec(2, ArrayBytes.fromString("{age:s32")),
				new EditRec(2, ArrayBytes.fromString("{id:s002")),
				new EditRec(2, ArrayBytes.fromString("{name:sphil")),
				new EditRec(4, ArrayBytes.fromString("{id:s004")),
				new EditRec(6, ArrayBytes.fromString("{id:s006")),
				new EditRec(6, ArrayBytes.fromString("{idle:strue")),
		});
	}
	public QueryNode makeQueryNode() {	
		// indirection so we can have a static version for other tests to re-use
		return testingQueryNode();
	}
	public void testQueryNode() {
		QueryNode node = makeQueryNode();
		assertEquals(2, node.doc());
		assertEquals(0, ArrayBytes.fromString("{age:s32").compareTo(node.term()));
		assertEquals(QueryNode.NextStatus.NEXT_TERM, node.nextTerm());
		
		assertEquals(2, node.doc());
		assertEquals(0, ArrayBytes.fromString("{id:s002").compareTo(node.term()));
		QueryNode copy = node.copy();
		assertEquals(QueryNode.NextStatus.NEXT_TERM, node.nextTerm());
		
		assertEquals(2, node.doc());
		assertEquals(0, ArrayBytes.fromString("{name:sphil").compareTo(node.term()));
		assertEquals(QueryNode.NextStatus.NEXT_DOC, node.nextTerm());

		// reset this doc 
		node.seek(2, ArrayBytes.EMPTY_BYTES);
		assertTrue(node.next());
		
		assertEquals(4, node.doc());
		assertEquals(0, ArrayBytes.fromString("{id:s004").compareTo(node.term()));
		assertEquals(true, node.next());
		
		assertEquals(6, node.doc());
		assertEquals(0, ArrayBytes.fromString("{id:s006").compareTo(node.term()));
		
		// over to copy:
		assertEquals(2, copy.doc());
		assertEquals(0, ArrayBytes.fromString("{id:s002").compareTo(copy.term()));
		assertEquals(QueryNode.NextStatus.NEXT_TERM, copy.nextTerm());
		assertEquals(2, copy.doc());
		assertEquals(0, ArrayBytes.fromString("{name:sphil").compareTo(copy.term()));
		assertEquals(true, copy.next());
		assertEquals(4, copy.doc());
		// and back to original
		
		assertEquals(QueryNode.NextStatus.NEXT_TERM, node.nextTerm());

		assertEquals(6, node.doc());
		assertEquals(0, ArrayBytes.fromString("{idle:strue").compareTo(node.term()));
		assertEquals(QueryNode.NextStatus.AT_END, node.nextTerm());

		node.seek(6, ArrayBytes.EMPTY_BYTES);
		assertEquals(6, node.doc());
		assertEquals(0, ArrayBytes.fromString("{id:s006").compareTo(node.term()));
		assertEquals(QueryNode.NextStatus.NEXT_TERM, node.nextTerm());

		assertEquals(6, node.doc());
		assertEquals(0, ArrayBytes.fromString("{idle:strue").compareTo(node.term()));
		assertEquals(QueryNode.NextStatus.AT_END, node.nextTerm());

	}
}
