package dovetaildb.dbservice;

import junit.framework.TestCase;
import dovetaildb.querynode.QueryNode;
import dovetaildb.querynode.QueryNodeTest;

public class QueryNodeDbResultTest extends TestCase {
	
	public void testBasic() {
		QueryNode node = QueryNodeTest.testingQueryNode();
		QueryNodeDbResult result = new QueryNodeDbResult(node);
		result.initialize(node.doc());
		assertTrue(result.isObject());
		assertEquals("002",result.derefByKey("id").simplify());
		assertEquals("32",result.derefByKey("age").simplify());
		assertEquals("phil",result.derefByKey("name").simplify());
		assertEquals(null,result.derefByKey("xxx").simplify());
		result.reset();
		result.initialize(4);
		assertTrue(result.isObject());
	}
	
	public void testComplex() {
		
	}
}
