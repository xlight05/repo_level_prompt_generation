package dovetaildb.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.org.mozilla.javascript.internal.Scriptable;


public class JsConversions {

	public static Map asJavaMap(Scriptable jsObject) {
		Map map = new HashMap<Object,Object>();
		for(Object jsKey : jsObject.getIds()) {
			map.put(jsKey, jsObject.get((String)jsKey, jsObject));
		}
		return map;
	}
	
	public static List asJavaList(Scriptable jsObject) {
		throw new RuntimeException("Not implemented");
	}
}
