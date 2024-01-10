package com.chenlb.ttserver;

import java.util.Date;
import java.util.Map;

import junit.framework.TestCase;

public class TTServerClientTest extends TestCase {

	TTServerClient ttsc;
	protected void setUp() throws Exception {
		ttsc = new TTServerClient(System.getProperty("ttserver.host", "localhost"), Integer.parseInt(System.getProperty("ttserver.post", "1978")));
	}

	protected void tearDown() throws Exception {
		ttsc.close();
	}
	
	public void testGet() {
			
		Object obj = ttsc.get("my_key");

		System.out.println(obj);

		obj = ttsc.get("my_key");

		System.out.println(obj);
		
		obj = ttsc.get("my_null");

		System.out.println(obj);

	}
	
	public void testGetStr() {
		Object obj = ttsc.getString("my_key");

		System.out.println(obj);

		obj = ttsc.getString("my_object");

		System.out.println(obj);
	}

	public void testMget() {
		String[] datas = {"值-1","值-2","value-3"};
		String[] keys = {"m_key_1","m_key_2","m_key_3"};
		int i = 0;
		for(String data : datas) {
			ttsc.put(keys[i++], data);
		}
		Map<String, Object> vs = ttsc.mget(keys);
		for(Map.Entry<String, Object> e : vs.entrySet()) {
			System.out.println("k="+e.getKey()+", v="+e.getValue());
		}
	}
	
	public void testMgetStr() {
		String[] datas = {"值-1","值-2","value-3"};
		String[] keys = {"m_skey_1","m_skey_2","m_skey_3"};
		int i = 0;
		for(String data : datas) {
			ttsc.putString(keys[i++], data);
		}
		Map<String, String> vs = ttsc.mgetString(keys);
		for(Map.Entry<String, String> e : vs.entrySet()) {
			System.out.println("k="+e.getKey()+", v="+e.getValue());
		}
	}
	
	public void testPut() {
		Date date = new Date();
		
		boolean isPut = ttsc.put("my_date", date);
		
		System.out.println("isPut="+isPut);
		
		Object obj = ttsc.get("my_date");

		System.out.println(obj);
		System.out.println("equal="+date.equals(obj));
	}
	
	public void testPutInt() {
		int d = 1986;
		
		boolean isPut = ttsc.put("my_int", d);
		
		System.out.println("isPut="+isPut);
		
		Object obj = ttsc.get("my_int");

		System.out.println(obj);
		assertNotNull(obj);
		System.out.println("equal="+obj.equals(d));
	}

	public void testPutStr() {
		String blog = "http://blog.chenlb.com/中文";
		
		boolean isPut = ttsc.putString("my_blog", blog);
		
		System.out.println("isPut="+isPut);
		
		Object obj = ttsc.getString("my_blog");

		System.out.println(obj);
		System.out.println("equal="+blog.equals(obj));
	}
	
	public void testPutKeepStr() {
		String str = "this is value putkeep str";
		boolean isPut = ttsc.putkeepString("test_put", str);
		
		System.out.println("isPut="+isPut);
		
		System.out.println(ttsc.getString("test_put"));
	}
	
	public void testPutCat() {
		String str = "this is value put";
		boolean isPut = ttsc.putString("test_put", str);
		
		System.out.println("isPut="+isPut);
		
		isPut = ttsc.putcat("test_put", "cat? so cool!");
		
		System.out.println("isPut="+isPut);
		System.out.println(ttsc.getString("test_put"));
	}
	
	public void testPutShl() {
		String str = "this is value putshl";
		boolean isPut = ttsc.putString("test_put", str);
		
		System.out.println("isPut="+isPut);
		
		isPut = ttsc.putshl("test_put", "ttserver 好用吗!", 25);
		
		System.out.println("isPut="+isPut);
		System.out.println(ttsc.getString("test_put"));
	}
	
	public void testPutNrStr() {
		String str = "this is value putnr";
		boolean isPut = ttsc.out("test_put");
		
		System.out.println("isPut="+isPut);
		
		ttsc.putnrString("test_put", str);
		
		System.out.println(ttsc.getString("test_put"));
	}
	
	public void testOut() {
		boolean isPut = ttsc.put("my_null", "my_null_object");
		System.out.println("isPut="+isPut);
		
		Object obj = ttsc.get("my_null");
		System.out.println(obj);
		assertNotNull(obj);
		
		isPut = ttsc.out("my_null");
		System.out.println("isPut="+isPut);
		
		obj = ttsc.get("my_null");
		assertNull(obj);
	}
	
	public void testInt() {
		Object obj = ttsc.getInt("my_num");
		System.out.println(obj);
		
		Integer num = ttsc.addInt("my_num", 10);
		System.out.println(num);
		
		obj = ttsc.getInt("my_num");
		System.out.println("fetch="+obj);
	}
	
	public void testSetInt() {
		Object obj = ttsc.getInt("my_num");
		System.out.println(obj);
		
		boolean isOk = ttsc.putInt("my_num", -10);
		System.out.println(isOk);
		
		obj = ttsc.getInt("my_num");
		System.out.println("fetch="+obj);
	}
	
	public void testGetInt() {
		Object obj = ttsc.getInt("my_num");
		System.out.println(obj);
	}
	
	public void testVsiz() {
		String str = "this is value put";
		boolean isPut = ttsc.putString("test_vsiz", str);
		System.out.println("isPut="+isPut);
		System.out.println(ttsc.getString("test_vsiz"));
		System.out.println(ttsc.vsiz("test_vsiz"));
		
		
		isPut = ttsc.put("test_vsiz_obj", str);
		System.out.println("isPut="+isPut);
		System.out.println(ttsc.get("test_vsiz_obj"));
		System.out.println(ttsc.vsiz("test_vsiz_obj"));
	}
	
	public void testRnum() {
		long ln = ttsc.rnum();
		System.out.println(ln);
	}
	
	public void testSize() {
		long ln = ttsc.size();
		System.out.println(ln);
	}
	
	public void testStat() {
		String stat = ttsc.stat();
		
		System.out.println(stat);
	}
	
	public void testIterInit() {
		boolean isOk = ttsc.iterinit();
		System.out.println(isOk);
	}
	
	public void testIterNext() {
		String key = "";
		for(int i=0; key!=null&&i<10; i++) {
			key = ttsc.iternext();
			System.out.println(key);
		}
	}
}
