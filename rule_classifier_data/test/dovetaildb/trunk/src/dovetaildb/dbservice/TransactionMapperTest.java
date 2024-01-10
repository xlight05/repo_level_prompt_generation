package dovetaildb.dbservice;

import java.util.HashMap;

import junit.framework.TestCase;

public abstract class TransactionMapperTest extends TestCase {

	public void testMapper() throws Exception {
		TransactionMapper mapper = createMapper();
		HashMap<String,Long> revs = new HashMap<String,Long>();
		revs.put("people",new Long(3));
		mapper.addRevsForTxn(0, revs);
		assertEquals(3, mapper.getRevForTxn("people", 0));
		revs = new HashMap<String,Long>();
		revs.put("pets",new Long(7));
		mapper.addRevsForTxn(1, revs);
		assertEquals(3, mapper.getRevForTxn("people", 1));
		assertEquals(7, mapper.getRevForTxn("pets", 1));
		assertEquals(0, mapper.getRevForTxn("pets", 0));
		//assertEquals(0, mapper.getRevForTxn("xxxxxx", 0));
	}
	
	protected abstract TransactionMapper createMapper();
	
	
}
