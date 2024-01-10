package dovetaildb.util;

import java.util.Map;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import junit.framework.TestCase;

public class JsConversionsTest extends TestCase {
	public static void testAsJavaMap() throws ScriptException {
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
		Map m = JsConversions.asJavaMap((sun.org.mozilla.javascript.internal.Scriptable)engine.eval("o={}; o[\"p\"]=\"f\"; o[\"k\"]=null; o"));
		assertEquals(2, m.size());
		assertEquals("f", m.get("p").toString());
		assertEquals(null, m.get("k"));
	}
}