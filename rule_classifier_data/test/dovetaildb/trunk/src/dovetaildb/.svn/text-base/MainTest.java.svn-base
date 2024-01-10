package dovetaildb;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

import org.glassfish.grizzly.http.server.HttpServer;

import dovetaildb.util.Util;

public class MainTest  extends TestCase {
	String baseUrl;
	protected List<Map<String,Object>> callQuery(String db, String bag, String query, String score) throws MalformedURLException, IOException {
		Map<String,Object> params = Util.literalSMap().p("bag", bag).p("query", query);
		if (score != null) params.put("options", score);
		Map ret = (Map)call(db, "query", params);
		return (List<Map<String,Object>>)ret.get("result");
	}
	protected Object call(String db, String action, Map<String,Object> args) throws MalformedURLException, IOException {
		StringBuffer buf = new StringBuffer(baseUrl);
		buf.append("/");
		buf.append(db);
		buf.append("/");
		buf.append(action);
		boolean first = true;
		for(Map.Entry<String,Object> entry : args.entrySet()) {
			if (first) {
				buf.append("?");
				first = false;
			} else {
				buf.append("&");
			}
			buf.append(entry.getKey());
			buf.append("=");
			buf.append(URLEncoder.encode((String) entry.getValue(), "utf-8"));
		}
		String url = buf.toString();
		System.out.println(url);
		URLConnection conn = new URL(url).openConnection();
		String content = Util.readFully(conn.getInputStream());
		return Util.jsonDecode(content);
	}
	public Map<String, Object> makeEntry(int idNum, Random r) {
		Map<String,Object> entry = Util.literalSMap();
		entry.put("id",Integer.toString(idNum));
		entry.put("name",Util.genUUID());
		entry.put("age", r.nextInt(100));
		entry.put("score", r.nextGaussian());
		return entry;
	}
	
	private void testIter(ArrayList<Map<String,Object>> allRecs, Random random) throws Exception {
		Map<String,Object> entry = makeEntry(-1, random);
		System.out.println( callQuery("testdb","people","{}","{}").get(0).get("score") );
		
	}

	private void checkDocExamples() throws Exception {
		String entry;
		Object ret;
		entry = Util.jsonEncode(Util.literalSMap().p("id", "P001").p("name","Bob").p("age", 32));
		ret = call("testdb","put",Util.literalSMap().p("bag","docex").p("entry", entry));
		entry = Util.jsonEncode(Util.literalSMap().p("id", "P002").p("name","Joe").p("age", 21));
		ret = call("testdb","put",Util.literalSMap().p("bag","docex").p("entry", entry));
		assertEquals(2, callQuery("testdb","docex","{}","{}").size());
		List<Map<String,Object>> rows;
		rows = callQuery("testdb","docex","{\"name\":\"Bob\", \"age\":[\">=\",18]}","{}");
		assertEquals(1, rows.size());
		assertEquals("P001", (rows.get(0).get("id")));
		assertEquals(32.0, rows.get(0).get("age"));
		assertEquals("Bob", rows.get(0).get("name"));
		assertEquals(3, rows.get(0).size());
		ret = call("testdb","execute",Util.literalSMap().p("code","api.query(\"docex\",java.util.HashMap(),java.util.HashMap())"));
		assertEquals(3, rows.get(0).size());
		entry = Util.jsonEncode(Util.literalSMap().p("id", "P001").p("name","Bob").p("age", 33));
		ret = call("testdb","put",Util.literalSMap().p("bag","docex").p("entry", entry));
		ret = call("testdb","remove",Util.literalSMap().p("bag","docex").p("id", "P002"));

		rows = callQuery("testdb","docex","{}","{}");
		assertEquals(1, rows.size());
		assertEquals(33.0, rows.get(0).get("age"));
	
	}
	
	public void testMain() throws Exception {
		File tempFile = File.createTempFile("MainTest", "ser");
		assertTrue(tempFile.delete());
		File bagDir = Util.createTempDirectory("MainTest_bag");
		try {
			HttpServer ws = Main.createWebServer(new String[]{"--port=10801", "--createmode=OPEN", "-m", tempFile.getAbsolutePath()});
			ws.start();
			Thread.currentThread().sleep(1000);
			try {
				baseUrl = "http://localhost:10801";
				Object ret;
				ret = call("_meta","execute",Util.literalSMap().p("code","repo.getDbNames()"));
				//System.out.println(ret.toString());
				String pathStr = bagDir.getAbsolutePath().replace('\\', '/');
				ret = call("_meta","execute",Util.literalSMap().p("code","repo.setDb(\"testdb\",Packages.dovetaildb.StdLib.makeFsDb(\""+pathStr+"\",false))"));
				//System.out.println(ret.toString());
				checkDocExamples();
				Random random = new Random(43534454);
				long jsonSize=0;
				ArrayList<Map<String,Object>> allRecs = new ArrayList<Map<String,Object>>(); 
				int NUM_ENTRIES = 25;
				for(int i=0; i<NUM_ENTRIES; i++) {
					Map<String,Object> entry = makeEntry(i, random);
					allRecs.add(entry);
					String encodedEntry = Util.jsonEncode(entry);
					jsonSize += encodedEntry.length() + 1;
					ret = call("testdb","put",Util.literalSMap().p("bag","people").p("entry", encodedEntry));
					//System.out.println(ret.toString());
				}
				System.out.println("load complete");
				size(bagDir);
				//long preRebuildSize = Util.sizeDir(bagDir);
				
				forceRebuild();
				size(bagDir);
				
				//long postRebuildSize = Util.sizeDir(bagDir);
				System.out.println("json raw: "+jsonSize);

				// test behavior
				List<Map<String,Object>> rets;
				rets = callQuery("testdb","people","{\"id\":\"0\"}","{}");
				System.out.println("WHO? "+rets);
				assertEquals(1, rets.size());
				rets = callQuery("testdb","people","{}","{}");
				System.out.println("WHO? "+rets);
				assertEquals(NUM_ENTRIES, rets.size());
				List<Map<String,Object>> recs;
				Map<String,Object> rec;
				recs = callQuery("testdb","people","{}","{}");
				assertEquals(NUM_ENTRIES, recs.size());
				double midNum =((Number)recs.get(0).get("score")).doubleValue();
				recs = callQuery("testdb","people","{}","{\"score\":{\"score\":[\"NumericScore\"]}}");
				System.out.println("WHO? "+recs);
				double hiNum = ((Number)recs.get(0).get("score")).doubleValue();
				assertEquals(NUM_ENTRIES, recs.size());
				recs = callQuery("testdb","people","{}","{\"score\":{\"score\":[\"ReverseScore\",[\"NumericScore\"]]}}");
				assertEquals(NUM_ENTRIES, recs.size());
				rec = recs.get(0);
				assertTrue(((String)rec.get("name")).length() > 0);
				double loNum = ((Number)rec.get("score")).doubleValue();
				assertTrue(loNum < hiNum);
				assertTrue(loNum <= midNum);
				assertTrue(midNum <= hiNum);
			} finally {
				ws.stop();
			}
		} finally {
			tempFile.delete();
			Util.deleteDirectory(bagDir);
		}
	}
	private void forceRebuild() throws Exception {
		waitForNoRebuild();
		call("_meta","execute",Util.literalSMap().p("code","repo.getDb(\"testdb\").triggerRebuild()"));
		waitForNoRebuild();
	}
	private void waitForNoRebuild() throws Exception {
		boolean isRebuilding;
		do {
			Thread.currentThread().sleep(1*1000);
			Object ret = call("_meta","execute",Util.literalSMap().p("code", "repo.getDb(\"testdb\").isRebuilding()"));
			isRebuilding = ((Boolean)((Map)ret).get("result")).booleanValue();
			//System.out.println("is reubilding? "+isRebuilding);
		} while(isRebuilding);
	}
	private void size(File bagDir) {
		for(File file : bagDir.listFiles()) {
			System.out.println(file+" : "+Util.sizeDir(file));
		}
	}
}
