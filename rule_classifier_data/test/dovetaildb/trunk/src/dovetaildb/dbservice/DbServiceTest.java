package dovetaildb.dbservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.json.simple.JSONValue;


import dovetaildb.api.ApiBuffer;
import dovetaildb.iter.Iter;
import dovetaildb.util.LiteralHashMap;
import dovetaildb.util.LiteralList;
import dovetaildb.util.Util;

public abstract class DbServiceTest  extends TestCase {

	protected abstract DbService createService();
	
	protected DbService db;
	
	@Override
	public void setUp() {
		db = createService();
		db.initialize();
	}

	@Override
	public void tearDown() {
		if (db != null) {
			db.drop();
		}
	}
	
	
	public static Object jsonCopy(Object o) {
		String s = JSONValue.toJSONString(o);
		return JSONValue.parse(s);
	}
	
	public static List<Object> yank(Iter iter) {
		ArrayList<Object> rets = new ArrayList<Object>();
		while(iter.hasNext()) {
			rets.add(jsonCopy(iter.next()));
		}
		return rets;
	}
	
	public void checkEmpty(long txnId) {
		assertEquals(0, yank(db.query("nobag", txnId, Util.literalMap(), Util.literalSMap())).size());
		assertEquals(0, yank(db.query("people", txnId, Util.literalMap(), Util.literalSMap())).size());
		assertEquals(0, yank(db.query("people", txnId, Util.literalMap().put("name", "Joe"), Util.literalSMap())).size());
	}
	
	public void checkTwoInserts(long txnId) {
		
		assertEquals(2, yank(db.query("people", txnId, Util.literalMap(), Util.literalSMap())).size());
		List<Object> rets;
		
		rets = yank(db.query("people", txnId, Util.literalMap().p("name","Joe"), Util.literalSMap()));
		assertEquals(1, rets.size());
		assertEquals("Joe", ((Map)rets.get(0)).get("name"));
		
		rets = yank(db.query("people", txnId, Util.literalMap().p("age",Util.literalList().a(">").a(25)), Util.literalSMap()));
		assertEquals(1, rets.size());
		assertEquals("Joe", ((Map)rets.get(0)).get("name"));

		assertEquals("{\"id\":\"ID1\",\"age\":31.0,\"name\":\"Joe\",\"gender\":\"m\"}", JSONValue.toJSONString( rets.get(0) ));
	}

	long initialEmptyTxnId, twoInsertsTxnId;

	public void testAll() throws Exception {
		long prevId = db.getHighestCompletedTxnId();
		checkEmpty(prevId);
		
		initialEmptyTxnId = db.commit(prevId, new HashMap<String,ApiBuffer>());
		
		checkEmpty(initialEmptyTxnId);
		
		HashMap<String,ApiBuffer> batch = new HashMap<String,ApiBuffer>();
		ApiBuffer buffer = new ApiBuffer();
		buffer.put("ID1", Util.literalSMap().p("name", "Joe") .p("gender", "m").p("age", 31));
		buffer.put("ID2", Util.literalSMap().p("name", "Jill").p("gender", "f").p("age", 23));
		batch.put("people", buffer);
		twoInsertsTxnId = db.commit(initialEmptyTxnId, batch);
		checkTwoInserts(twoInsertsTxnId);
		checkEmpty(initialEmptyTxnId); // old rev should still appear empty

		batch = new HashMap<String,ApiBuffer>();
		buffer = new ApiBuffer();
		buffer.getDeletions().add("ID1");
		buffer.getDeletions().add("ID2");
		batch.put("people", buffer);
		System.out.println("---------");
		long clearedTxnId = db.commit(twoInsertsTxnId, batch);
		assertEquals(clearedTxnId, initialEmptyTxnId+2);
		checkEmpty(clearedTxnId);
	}

	protected LiteralList<Object> l() { return Util.literalList(); }
	protected LiteralHashMap<String,Object> m() { return Util.literalSMap(); }
	
	public void testQueryForms() throws Exception {
		initialEmptyTxnId = db.getHighestCompletedTxnId();
		
		checkEmpty(initialEmptyTxnId);
		
		HashMap<String,ApiBuffer> batch = new HashMap<String,ApiBuffer>();
		ApiBuffer buffer = new ApiBuffer();
		Object loc = m().p("name", "Seattle").p("lat", 34.2).p("lng", 123.1);
		Object txns = l().a(Util.literalMap().p("tot",10295.0)).a(Util.literalMap().p("tot",1995.0));
		Map<String,Object> o1 = m().p("name", "Joe") .p("gender", "m").p("age", 31.0).p("loc",loc).p("txns", txns);
		buffer.put("ID1", o1);
		loc = m().p("name", "New York").p("lat", 22.8).p("lng", 122.8);
		txns = l().a(m().p("tot",6900.0)).a(m().p("tot",623.0));
		Map<String,Object> o2 = m().p("name", "Jill").p("gender", "f").p("age", 23.0).p("loc",loc).p("txns", txns);
		buffer.put("ID2", o2);
		batch.put("people", buffer);
		long commitId = db.commit(initialEmptyTxnId, batch);
		
		List<Object> both = l().a(o1).a(o2); 
		List<Object> none = l(); 
		List<Object> jst1 = l().a(o1); 
		List<Object> jst2 = l().a(o2); 
		assertEquals(both, yank(db.query("people", commitId, Util.literalMap(), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("id",""), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("nofield",3), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("txns",l().a("X")), Util.literalSMap())));
		assertEquals(both, yank(db.query("people", commitId, m().p("txns",l()), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("txns",m()), Util.literalSMap())));
		assertEquals(both, yank(db.query("people", commitId, m().p("txns",l().a(m())), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("txns",l().a(null)), Util.literalSMap())));
		assertEquals(both, yank(db.query("people", commitId, m().p("txns",l().a(m().p("tot", l().a("*")))), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("age",l().a("<").a(20)), Util.literalSMap())));
		assertEquals(jst2, yank(db.query("people", commitId, m().p("age",l().a("<").a(30)), Util.literalSMap())));
		assertEquals(both, yank(db.query("people", commitId, m().p("age",l().a("<").a(40)), Util.literalSMap())));
		assertEquals(jst1, yank(db.query("people", commitId, m().p("age",l().a(">=").a(31)), Util.literalSMap())));
		assertEquals(jst1, yank(db.query("people", commitId, m().p("age",l().a("(]").a(23).a(31)), Util.literalSMap())));
		assertEquals(jst2, yank(db.query("people", commitId, m().p("age",l().a("[)").a(23).a(31)), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("age",l().a("()").a(23).a(31)), Util.literalSMap())));
		assertEquals(both, yank(db.query("people", commitId, m().p("age",l().a("[]").a(23).a(31)), Util.literalSMap())));
		assertEquals(both, yank(db.query("people", commitId, m().p("txns",l().a(m().p("tot",l().a(">").a(5000)))), Util.literalSMap())));
		assertEquals(jst1, yank(db.query("people", commitId, m().p("txns",l().a(m().p("tot",l().a(">").a(9000)))), Util.literalSMap())));
		assertEquals(none, yank(db.query("people", commitId, m().p("txns",l().a(m().p("tot",l().a(">").a(20000)))), Util.literalSMap())));
		assertEquals(jst1, yank(db.query("people", commitId, m().p("loc",m().p("lat",l().a("[]").a(32).a(35))), Util.literalSMap())));
		assertEquals(jst1, yank(db.query("people", commitId, m().p("loc",l().a("&").a(l().a(m().p("lat",l().a("[]").a(32).a(35))).a(m().p("lng",l().a("[]").a(122).a(124))))), Util.literalSMap())));
		assertEquals(both, yank(db.query("people", commitId, l().a("|").a(l().a(m().p("name","Jill")).a(m().p("loc",m().p("lat",l().a("[]").a(32).a(35))))), Util.literalSMap())));
	}
	
}
