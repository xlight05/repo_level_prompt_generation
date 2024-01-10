package dovetaildb.util;
/*
 * Copyright 2008 Phillip Schanely
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dovetaildb.dbservice.DbResultLiterals;

public class Util {

	public static final byte[] EMPTY_BYTES = new byte[]{};

	public static <T> ArrayList<T> singletonList(T item) {
		ArrayList<T> l = new ArrayList<T>();
		l.add(item);
		return l;
	}

	// UNTESTED
//	public static <T> Iterator<T> chain(final Iterator<? extends T>... subIters) {		
//		return new Iterator<T>() {
//			int idx = 0;
//			public boolean hasNext() {
//				while(idx < subIters.length) {
//					if (subIters[idx].hasNext()) return true;
//					idx++;
//				}
//				return false;
//			}
//			public T next() {
//				while(!subIters[idx].hasNext()) idx++;
//				return subIters[idx].next();
//			}
//			public void remove() {
//				subIters[idx].remove();
//			}
//		};
//	}
	
	public static <K,V> HashMap<V,Map<K,V>> indexBy(List<Map<K,V>> rows, K key) {
		HashMap<V,Map<K,V>> results = new HashMap<V,Map<K,V>>();
		for(Map<K,V> row : rows) {
			results.put(row.get(key), row);
		}
		return results;
	}

	public static <K,V> HashSet<V> findUniqueValues(List<Map<K,V>> rows, K key) {
		HashSet<V> results = new HashSet<V>();
		for(Map<K,V> row : rows) {
			results.add(row.get(key));
		}
		return results;
	}

	public final static Map<Object, Object> EMPTY_MAP = Collections.unmodifiableMap(new HashMap<Object, Object>());

	public static <K,V> LiteralHashMap<K,V> literalMap() {
		return new LiteralHashMap<K,V>();
	}

	public static final Map<String, Object> EMPTY_SMAP = Collections.unmodifiableMap(new HashMap<String, Object>());
	
	public static final Logger logger = Logger.getLogger("");

	public static LiteralHashMap<String,Object> literalSMap() {
		return new LiteralHashMap<String,Object>();
	}

	public static <T> LiteralHashSet<T> literalSet() {
		return new LiteralHashSet<T>();
	}

	public static <T> LiteralList<T> literalList() {
		return new LiteralList<T>();
	}

	public static LiteralList<String> literalSList() {
		return new LiteralList<String>();
	}

	public static String genUUID() {
		return UUID.generate();
	}
	
	/*
	public static Object jsonDecode(String json) throws JSONException {
		return JSONTokener.parse(json);		
	}
	public static String jsonEncode(Object o) throws JSONException {
		try {
			StringWriter buf = new StringWriter();
			jsonEncode(buf, o);
			return buf.toString();
		} catch(IOException e) { throw new RuntimeException(e); }
	}

	public static void jsonEncode(Writer buf, Map o) throws IOException, JSONException {
		buf.append('{');
		Iterator<Map.Entry<Object, Object>> i =	o.entrySet().iterator();
		if (i.hasNext()) {
			Map.Entry<Object,Object> e = i.next();
			jsonEncode(buf, e.getKey());
			buf.append(':');
			jsonEncode(buf, e.getValue());
			while(i.hasNext()) {
				e = i.next();
				buf.append(',');
				jsonEncode(buf, e.getKey());
				buf.append(':');
				jsonEncode(buf, e.getValue());
			}
		}
		buf.append('}');
	}
	
	public static void jsonEncode(Writer buf, List o) throws IOException, JSONException {
		buf.append('[');
		Iterator<Object> i = o.iterator();
		if (i.hasNext()) {
			jsonEncode(buf, i.next());
			while(i.hasNext()) {
				buf.append(',');
				jsonEncode(buf, i.next());
			}
		}
		buf.append(']');
	}
	
	public static void jsonEncode(Writer buf, String o) throws IOException, JSONException {
		buf.append(JSONObject.quote(o));		
	}
	
	public static void jsonEncode(Writer buf, Object o) throws IOException, JSONException {
		if (o == null) {
			buf.append("null");
			return;
		}
		Class clazz = o.getClass();
		if (clazz.isArray()) {
			buf.append('[');
			Class componentType = clazz.getComponentType();
			if (componentType.isPrimitive()) {
				int len = Array.getLength(o);
				if (len > 0) {
					jsonEncode(buf, Array.get(o,0));
					for(int i=1; i<len; i++) {
						buf.append(',');
						jsonEncode(buf, Array.get(o,i));
					}
				}
			} else {
				Object[] items = (Object[])o;
				if (items.length > 0) {
					jsonEncode(buf, items[0]);
					for(int i=1; i< items.length; i++) {
						buf.append(',');
						jsonEncode(buf, items[i]);
					}
				}
			}
			buf.append(']');
			return;
		}
		do {
			if (clazz == String.class) {
				buf.append(JSONObject.quote((String)o));
				return;
			} else if (clazz == Number.class) {
				buf.append(JSONObject.numberToString((Number)o));
				return;
			} else if (clazz == Boolean.class) {
				buf.append(((Boolean)o).booleanValue() ? "true" : "false");
				return;
			} else if (clazz == AbstractResultMap.class || clazz == HashMap.class) {
				jsonEncode(buf, (Map)o);
				return;
			} else if (clazz == AbstractResultList.class || clazz == ArrayList.class) {
				jsonEncode(buf, (List)o);
				return;
			} else if (clazz == JSONObject.class) {
				buf.append(((JSONObject)o).toString());
				return;
			} else if (clazz == JSONArray.class) {
				buf.append(((JSONArray)o).toString());
				return;
			}
			clazz = clazz.getSuperclass();
		}  while (clazz != null);
		if (o instanceof QueryResultIterator) {
			QueryResultIterator itr = (QueryResultIterator)o;
			buf.append('[');
			boolean needsComma = false;
			Object[] buffer = new Object[200];
			while(true) {
				int ct = itr.pullAvailable(buffer, true);
				if (ct == -1) break;
				int i;
				if (! needsComma) { // on first iteration:
					buf.append(jsonEncode(buffer[0]));
					i = 1;
					needsComma = true;
				} else {
					i = 0;
				}

				for(; i<ct; i++) {
					buf.append(',');
					buf.append(jsonEncode(buffer[i]));
				}
			}
			buf.append(']');
			return;
		}
		// last resort (other instances of list, map):
		if (o instanceof Map) {
			jsonEncode(buf, (Map)o);
			return;
		}
		if (o instanceof List) {
			jsonEncode(buf, (List)o);
			return;
		}		
		String message = "Unable to JSON-encode object of type "+o.getClass().getName();
		message += " (subclass of "+o.getClass().getSuperclass().getName()+")";
		throw new JSONException(message);
	}
	*/

	public static <K,V1,V2> Map<V1,V2> getMapsHashMap(Map<K, Map<V1,V2>> map, K key) {
		if (map.containsKey(key)) return map.get(key);
		Map<V1,V2> ret = new HashMap<V1,V2>();
		map.put(key, ret);
		return ret;
	}

	public static String socketToString(Socket socket) {
		return "("+socket.getLocalSocketAddress().toString()+socket.getRemoteSocketAddress().toString()+")";
	}

    public static String jsStackTrace(Throwable t)
    {
    	StringBuffer trace = new StringBuffer();
    	for(StackTraceElement e : t.getStackTrace()) {
    		String fileName = e.getFileName();
    		if (fileName != null && fileName.endsWith(".js"))
    			trace.append(e.toString()+"\n");
    	}
    	return trace.toString();
    }

	public static long sizeDir(File file) {
		if (file.isFile())
			return file.length();
		File[] files = file.listFiles();
		long size = 0;
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				size += sizeDir(files[i]);
			}
		}
		return size;
	}

	public static String readFully(File file) {
		try {
			return readFully(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String readFully(InputStream resultStream) {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(resultStream));
			String line;
			StringBuffer buf = new StringBuffer();
			while( (line = r.readLine()) != null ) {
				buf.append(line);
				buf.append("\n");
			}
			return buf.toString();
		} catch(IOException e) { throw new RuntimeException(e); }
	}

	public static int objToInt(Object obj, int defaultValue) {
		if (obj == null) return defaultValue;
		else if (obj instanceof Number) return ((Number)obj).intValue();
		else if (obj instanceof String) return Integer.parseInt((String)obj);
		else throw new RuntimeException("Value not an integer: "+obj);
	}

	public static String getTraces(int i1, int i2) {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		StringBuffer b = new StringBuffer();
		for(int i=i1; i<=i2; i++) {
			if (i >= elements.length) break;
			b.append(elements[i].toString());
			b.append('\n');
		}
		return b.toString();
	}

	public static String encodeBytes(byte[] bytes) {
		try {
			return new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	public static byte[] decodeString(String s) {
		try {
			return s.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String bytesAsString(byte[] bytes) {
		StringBuffer buf = new StringBuffer();
		for(byte b : bytes) buf.append((char)b);
		return buf.toString();
	}
	public static String bytesAsString(ByteBuffer tsf, int bufIdxMin, int bufIdxMax) {
		StringBuffer buf = new StringBuffer();
		for(int i=bufIdxMin; i<bufIdxMax; i++) buf.append((char)tsf.get(i));
		return buf.toString();
	}
	
	public static int compareBytes(byte[] b1, byte[] b2) {
		int max = Math.min(b1.length, b2.length);
		for(int i=0; i<max; i++) {
			int byte1 = (b1[i]&0xff);
			int byte2 = (b2[i]&0xff);
			int diff = byte1 - byte2;
			if (diff != 0) return diff;
		}
		return b1.length - b2.length;
	}

	public static int compareBytes(byte[] b1, byte[] b2, int o1, int o2, int l1, int l2) {
		try {
			int max = Math.min(l1,l2);
			for(int l=0; l<max; l++) {
				int byte1 = (b1[o1+l]&0xff);
				int byte2 = (b2[o2+l]&0xff);
				int diff = byte1 - byte2;
				if (diff != 0) return diff;
			}
			return l1-l2;
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println();
			throw e;
			
		}
	}

	public static int leBytesToInt(byte[] a, int i) {
		return
		((a[i  ]&0xFF) << 8 * 0) | 
		((a[i+1]&0xFF) << 8 * 1) |
		((a[i+2]&0xFF) << 8 * 2) |
		((a[i+3]&0xFF) << 8 * 3);
	}

	public static long leBytesToUInt(byte[] a, int i) {
		return
		((a[i  ]&0xFFL) << 8 * 0) | 
		((a[i+1]&0xFFL) << 8 * 1) |
		((a[i+2]&0xFFL) << 8 * 2) |
		((a[i+3]&0xFFL) << 8 * 3);
	}

	public static int leBytesToUShort(byte[] a, int i) {
		return
		((a[i  ]&0xFF) << 8 * 0) | 
		((a[i+1]&0xFF) << 8 * 1);
	}
	
	
	public static void leIntToBytes(int i, byte[] a, int offset) {
		a[offset  ] = (byte)((i >>> 8 * 0) & 0xFF);
		a[offset+1] = (byte)((i >>> 8 * 1) & 0xFF);
		a[offset+2] = (byte)((i >>> 8 * 2) & 0xFF);
		a[offset+3] = (byte)((i >>> 8 * 3) & 0xFF);
	}
	
	public static void leUIntToBytes(long i, byte[] a, int offset) {
		a[offset  ] = (byte)((i >>> 8 * 0) & 0xFF);
		a[offset+1] = (byte)((i >>> 8 * 1) & 0xFF);
		a[offset+2] = (byte)((i >>> 8 * 2) & 0xFF);
		a[offset+3] = (byte)((i >>> 8 * 3) & 0xFF);
	}
	
	public static void leShortToBytes(int i, byte[] a, int offset) {
		a[offset  ] = (byte)((i >>> 8 * 0) & 0xFF);
		a[offset+1] = (byte)((i >>> 8 * 1) & 0xFF);
	}
	
	public static void beLongToBytes(long i, byte[] a, int offset) {
		a[offset+7] = (byte)((i >>> 8 * 0) & 0xFF);
		a[offset+6] = (byte)((i >>> 8 * 1) & 0xFF);
		a[offset+5] = (byte)((i >>> 8 * 2) & 0xFF);
		a[offset+4] = (byte)((i >>> 8 * 3) & 0xFF);
		a[offset+3] = (byte)((i >>> 8 * 4) & 0xFF);
		a[offset+2] = (byte)((i >>> 8 * 5) & 0xFF);
		a[offset+1] = (byte)((i >>> 8 * 6) & 0xFF);
		a[offset+0] = (byte)((i >>> 8 * 7) & 0xFF);
	}

	public static long beBytesToLong(byte[] a, int i) {
		return
		(((long)a[i+0]&0xFF) << 8 * 7) | 
		(((long)a[i+1]&0xFF) << 8 * 6) |
		(((long)a[i+2]&0xFF) << 8 * 5) |
		(((long)a[i+3]&0xFF) << 8 * 4) |
		(((long)a[i+4]&0xFF) << 8 * 3) |
		(((long)a[i+5]&0xFF) << 8 * 2) |
		(((long)a[i+6]&0xFF) << 8 * 1) |
		(((long)a[i+7]&0xFF) << 8 * 0);
	}

	public static boolean incrementBinary(byte[] a) {
		for(int i = a.length - 1; i>=0; i--) {
			int v = a[i] & 0xff;
			if (v < 255) {
				a[i] = (byte)(v+1);
				return true;
			} else {
				a[i] = 0;
			}
		}
		return false; // overflow
	}

	public static File createTempDirectory(String name) {
		try {
		    final File temp;
		    temp = File.createTempFile(name,null);
		    if(!(temp.delete())) { throw new IOException("Could not delete temp file: " + temp.getAbsolutePath()); }
		    if(!(temp.mkdir())) { throw new IOException("Could not create temp directory: " + temp.getAbsolutePath()); }
		    return temp;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return( path.delete() );
	}

	public static byte[] appendByte(byte[] prefix, byte suffix) {
		byte[] newBytes = new byte[prefix.length+1];
		System.arraycopy(prefix, 0, newBytes, 0, prefix.length);
		newBytes[prefix.length] = suffix;
		return newBytes;
	}

	private static final ContainerFactory containerFactory = new ContainerFactory() {
		public List creatArrayContainer() {
			return new DbResultLiterals.DbResultList();
		}
		public Map createObjectContainer() {
			return new DbResultLiterals.DbResultMap();
		}
	};
	public static class JsonParseRuntimeException extends RuntimeException {
		private static final long serialVersionUID = -7287604347061693033L;
		public JsonParseRuntimeException(ParseException e) {
			super(e);
		}
	}
	public static Object jsonDecode(String json) {
		JSONParser parser = new JSONParser();
		try {
			return parser.parse(json, containerFactory);
		} catch (ParseException e) {
			throw new JsonParseRuntimeException(e);
		}
	}

	public static void jsonEncode(Writer wtr, Object o) {
		try {
			JSONValue.writeJSONString(o, wtr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String jsonEncode(Object o) {
		return JSONValue.toJSONString(o);
	}

	public static byte[] serialize(Serializable obj) {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(bytes);
			o.writeObject(obj);
			o.flush();
			return bytes.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static int compare(Object o1, Object o2) {
		if (o1 == null) {
			return (o2 == null) ? 0 : -1;
		} else {
			return ((Comparable<Object>)o1).compareTo(o2);
		}
	}

	public static String makePrintable(String s) {
		String json = Util.jsonEncode(s);
		return json.substring(1, json.length()-1);
	}
	public static String makePrintable(byte[] bytes) {
		return makePrintable(bytesAsString(bytes));
	}
}
