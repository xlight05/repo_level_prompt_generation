package dovetaildb.servlet;


import java.util.Collection;

import java.util.HashSet;
import java.util.Iterator;

import junit.framework.TestCase;
import dovetaildb.dbrepository.MemoryDbRepository;
import dovetaildb.dbrepository.ParsedRequest;
import dovetaildb.util.LiteralHashMap;
import dovetaildb.util.Util;

public class DovetaildbServletTest  extends TestCase {

	private static LiteralHashMap<String,Object> m() {
		return Util.literalSMap();
	}
	
	public void testBasic() throws Exception {
		MemoryDbRepository repo = new MemoryDbRepository();
		repo.initMeta();
		DovetaildbServlet servlet = new DovetaildbServlet();
		servlet.setRepo(repo);
		Object ret = servlet.handle(ParsedRequest.makeExecute("_meta", "40+2"));
		assertEquals(42, ((Number)ret).intValue());
		ret = servlet.handle(ParsedRequest.makeExecute("_meta", "repo.getDbNames()"));
		assertEquals(Util.literalList().a("_meta"), ret);
		ret = servlet.handle(ParsedRequest.makeExecute("_meta", "repo.setDb('social',Packages.dovetaildb.StdLib.makeMemoryDb())"));
		assertEquals(null, ret);
		ret = servlet.handle(ParsedRequest.makeExecute("_meta", "repo.getDbNames()"));
		assertEquals(Util.literalSet().a("_meta").a("social"), new HashSet((Collection)ret));
		ret = servlet.handle(ParsedRequest.makeExecute("_meta", "repo.getCodeFileNames('social')"));
		assertEquals(Util.literalList(), ret);
		Object phil = m().p("id","ID1").p("name","Phil").p("age",32);
		ret = servlet.handle(ParsedRequest.makePut("social","people",Util.jsonEncode(phil)));
		assertEquals(null, ret);
		Iterator itr = (Iterator)servlet.handle(ParsedRequest.makeQuery("social","people",Util.jsonEncode(m().p("name", "Phil"))));
		assertEquals(true, itr.hasNext());
		itr.next();
		assertEquals(false, itr.hasNext());
		String mtimeJsSrc = 
			"function wrapApiService(api, req) {\n"+
			"  var proxy={'query'   :function(b,q,o){q.put('age',31);return api.query.call(api,b,q,o)},\n"+
			"             'put'     :function(){return api.put.apply(api,arguments)},\n"+
			"             'remove'  :function(){throw 'Remove not allowed';},\n"+
			"             'commit'  :function(){return api.commit.apply(api,arguments)},\n"+
			"             'rollback':function(){return api.rollback.apply(api,arguments)}};\n"+
			"  return new Packages.dovetaildb.api.ApiService(proxy);\n"+
			"}\n"+
			"apiplugins.add(Packages.dovetaildb.api.Plugin({'wrapApiService':wrapApiService}))\n";
		ret = servlet.handle(ParsedRequest.makeExecute("_meta", "repo.setCodeFile('social','use_mtime.js',"+Util.jsonEncode(mtimeJsSrc)+")"));
		assertEquals(null, ret);
		itr = (Iterator)servlet.handle(ParsedRequest.makeQuery("social","people",Util.jsonEncode(m())));
		assertEquals(false, itr.hasNext());
		Object jane = m().p("id","ID2").p("name","Jane").p("age",31);
		ret = servlet.handle(ParsedRequest.makePut("social","people",Util.jsonEncode(jane)));
		assertEquals(null, ret);
		itr = (Iterator)servlet.handle(ParsedRequest.makeQuery("social","people",Util.jsonEncode(m().p("age", 32))));
		assertEquals(true, itr.hasNext());
		itr.next();
		assertEquals(false, itr.hasNext());
		try {
			servlet.handle(ParsedRequest.makeRemove("social","people","ID2"));
			fail();
		} catch(RuntimeException e) {}
	}

}
