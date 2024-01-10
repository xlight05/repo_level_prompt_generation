package dovetaildb.querynode;

import dovetaildb.bagindex.EditRec;
import dovetaildb.bagindex.Range;
import dovetaildb.bytes.ArrayBytes;

public class FilteredQueryNodeTest extends QueryNodeTest {
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
	@Override
	public QueryNode makeQueryNode() {
		return FilteredQueryNode.make(
				new LiteralRangeQueryNode(new EditRec[]{
				new EditRec(2, ArrayBytes.fromString("0xxxxxxx")), // gets filtered
				new EditRec(2, ArrayBytes.fromString("{age:s31")), // gets filtered
				new EditRec(2, ArrayBytes.fromString("{age:s32")),
				new EditRec(2, ArrayBytes.fromString("{id:s002")),
				new EditRec(2, ArrayBytes.fromString("{name:sphil")),
				new EditRec(4, ArrayBytes.fromString("{id:s004")),
				new EditRec(6, ArrayBytes.fromString("{id:s006")),
				new EditRec(6, ArrayBytes.fromString("{idle:strue")),
		}), new Range(
				ArrayBytes.fromString("{"),
				ArrayBytes.fromString("age:s31"),
				null,
				false, false
				));
	}
}
