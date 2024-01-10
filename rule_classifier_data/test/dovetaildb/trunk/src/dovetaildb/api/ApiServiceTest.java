package dovetaildb.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONValue;

import junit.framework.TestCase;
import dovetaildb.bagindex.TrivialBagIndex;
import dovetaildb.dbservice.BagEntry;
import dovetaildb.dbservice.BagEntryFactory;
import dovetaildb.dbservice.BagIndexBridge;
import dovetaildb.dbservice.DbService;
import dovetaildb.dbservice.DbServiceTest;
import dovetaildb.dbservice.ProcessTransactionMapper;
import dovetaildb.iter.Iter;
import dovetaildb.util.Util;

public abstract class ApiServiceTest  extends TestCase {

	protected DbService createDbService() {
		BagIndexBridge b = new BagIndexBridge();
		final ProcessTransactionMapper mapper = new ProcessTransactionMapper();
		mapper.addRevsForTxn(0, new HashMap<String,Long>());
		b.setBagIndexFactory(new BagEntryFactory() {
			public BagEntry makeBagEntry(String bagName) {
				TrivialBagIndex bagIndex = new TrivialBagIndex();
				mapper.introduceBag(bagName, bagIndex);
				return new BagEntry(bagIndex);
			}
		});
		b.setTxnMapper(mapper);
		return b;
	}
	
	protected abstract ApiService createApi(DbService dbService);
	
	public static List<Object >yank(Iter iter) { return DbServiceTest.yank(iter); }
	
	public void checkEmpty(ApiService api) {
		assertEquals(0, yank(api.query("nobag", Util.literalMap(), Util.literalSMap())).size());
		assertEquals(0, yank(api.query("people", Util.literalMap(), Util.literalSMap())).size());
		assertEquals(0, yank(api.query("people", Util.literalMap().p("name", Util.literalList().a("!").a("Joe")), Util.literalSMap())).size());
	}
	
	public void checkTwoInserts(ApiService api) {
		
		assertEquals(2, yank(api.query("people", Util.literalMap(), Util.literalSMap())).size());
		
		List<Object> rets = yank(api.query("people", Util.literalMap().p("name","Joe"), Util.literalSMap()));
		assertEquals(1, rets.size());
		assertEquals("Joe", ((Map<String,Object>)rets.get(0)).get("name"));
		
	}

	
	Random rand = new Random(234567);
	protected <T> T pick(T[] items) {
		return items[rand.nextInt(items.length)];
	}
	HashMap<String,Object> records = new HashMap<String,Object>();
	protected int commonlySmallInt(int multiplier) {
		double d = rand.nextGaussian();
		if (d < 0.0) d *= -1;
		return (int)(d*multiplier);
	}
	Character[] COMMON_CHARS = new Character[]{'a','e','i','o','u','b','c','d','f','g','h','p','s','t','0','1','2','3','4','5','6','7','8','9'};
	protected String randString() {
		int len = commonlySmallInt(8);
		boolean unusual = (rand.nextInt(10) == 0);
		StringBuffer buf = new StringBuffer(len);
		for(int i=0; i<len; i++) {
			if (unusual) {
				int codepoint;
				do {
					codepoint = rand.nextInt(1114112); // 0x10ffff + 1
				} while(! Character.isLetter(codepoint));
				buf.append((char)codepoint);
				try {
					assertEquals(buf.toString(), new String(Util.decodeString(buf.toString()), "utf-8") );
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			} else {
				buf.append(pick(COMMON_CHARS));
			}
		}
		return buf.toString();
	}
	protected Map<String,Object> genMap(int depth, boolean isQuery) {
		int len = commonlySmallInt(5);
		if (isQuery) len /= 3;
		HashMap<String,Object> m = new HashMap<String,Object>(len);
		for(int i=0; i<len;  i++) {
			m.put(randString(), genRecord(depth+1, isQuery));
		}
		if (depth == 1) {
			m.put("id", randString());
		}
		return m;
	}
	protected Object genRecord(int depth, boolean isQuery) { // add sorting capability to this?
		if (rand.nextFloat() > 1.0f/(0.05+(float)(depth*depth))) {
			// primitive
			switch (rand.nextInt(6)) {
			case 0: return null;
			case 1: return true;
			case 2: return false;
			case 3: return (double)((float)rand.nextInt());
			case 4: return (double)((float)rand.nextGaussian());
			case 5: return randString();
			default:
				throw new RuntimeException();
			}
		} else if (pick(new Boolean[]{true,true,false})) {
			return genMap(depth, isQuery);
		} else {
			int len = commonlySmallInt(5);
			if (isQuery) len = 1;
			ArrayList<Object> array = new ArrayList<Object>(len);
			for(int i=0; i<len; i++) {
				array.add(genRecord(depth+1, isQuery));
			}
			return array;
		}
	}
	protected Map<String,Object> makeRecord(boolean isQuery) {
		Map<String,Object> result = genMap(1, isQuery);
		if (! isQuery) records.put((String)result.get("id"), result);
		return result;
	}
	protected boolean matches(Object o, Object pattern) {
		if (o instanceof Map) {
			if (!(pattern instanceof Map)) return false;
			Map oMap = (Map)o;
			for(Object entryObj : ((Map)pattern).entrySet()) {
				Map.Entry entry = (Map.Entry)entryObj;
				String key = (String)entry.getKey();
				if (! oMap.containsKey(key)) return false;
				if (! matches(oMap.get(key), entry.getValue())) return false;
			}
			return true;
		} else if (o instanceof List) {
			if (!(pattern instanceof List)) return false;
			Object innerPattern = ((List)pattern).get(0);
			for(Object i : ((List)o)) {
				if (! matches(i, innerPattern)) return false;
			}
			return true;
		} else {
			if ((o == null) ^ (pattern == null)) return false;
			return o == pattern || o.equals(pattern);
		}
	}
	public ArrayList<Object> getMatching(Object query) {
		ArrayList<Object> results = new ArrayList<Object>();
		for(Object record : records.values()) {
			if (matches(record, query)) {
				results.add(record);
			}
		}
		return results;
	}
	
	protected void betweenIters(DbService dbService, ApiService apiService) {
	}
	
	public void testComplex() {
		DbService db = createDbService();
		ApiService api = createApi(db);
		//for(int numIters : new int[]{2,500,2000}) {  // got a failure once with this set, might be useful too
		for(int numIters : new int[]{2,5,20,50,200,500,2000}) {
			System.out.println();
			System.out.println("testComplex numIters = "+numIters);
			System.out.println();
			ArrayList<String> keys = new ArrayList<String>(records.keySet());
			for(int i=0; i< keys.size()/4; i++) {
				String key = keys.remove(this.rand.nextInt(keys.size()));
				System.out.println("REMOVED |"+key+"|");
				records.remove(key);
				api.remove("test", key);
			}
			for(int i=0; i < numIters; i++) {
				Map<String, Object> rec = makeRecord(false);
				System.out.println("ADDED |"+rec.get("id")+"|");
				api.put("test", rec);
			}
			api.commit();
			for(int i=0; i < numIters*4; i++) {
				Object query = makeRecord(true);
				ArrayList<Object> matching = getMatching(query);
				
				System.out.println("  "+query+rand.nextLong());
				System.out.println(matching);
				long checkpoint = rand.nextLong();
				System.out.println("CHECKPOINT "+checkpoint);
				if (checkpoint == -2452059308835280636l) {
					System.out.println();
				}
				
				Iter iter = api.query("test", query, Util.literalSMap());
				List<Object> results = yank(iter);
				
				System.out.println("expctd "+JSONValue.toJSONString(matching));
				System.out.println("vs     "+results);
				
				assertEquals(matching, results);
			}
			betweenIters(db, api);
		}
		
	}
	
	public void XXXtestAll() throws Exception { // TODO re-enable
		DbService db = createDbService();
		ApiService api1 = createApi(db);
		ApiService api2 = createApi(db);
		
		checkEmpty(api1);
		
		Map<String,Object> phil = Util.literalSMap().p("name", "Phil").p("age", new Long(31)).p("id", "p1");
		Map<String,Object> joe  = Util.literalSMap().p("name", "Joe") .p("age", new Integer(28)).p("id", "p2");
		api1.put("people", phil);
		api1.put("people", joe);
		
		checkTwoInserts(api1);
		checkEmpty(api2);
		ApiService api3 = createApi(db);
		
		api1.commit();
		checkTwoInserts(api1);
		
		ApiService api4 = createApi(db);
		checkTwoInserts(api4);
		checkEmpty(api2);
		checkEmpty(api3);
		
		

	}

	
	
}
