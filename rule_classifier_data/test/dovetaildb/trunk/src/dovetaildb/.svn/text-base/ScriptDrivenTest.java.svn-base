package dovetaildb;

import java.io.File;
import java.io.InputStreamReader;
import java.util.logging.Level;


import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import junit.framework.TestCase;
import dovetaildb.dbrepository.MemoryDbRepository;
import dovetaildb.dbrepository.ParsedRequest;
import dovetaildb.servlet.DovetaildbServlet;
import dovetaildb.util.Util;

public class ScriptDrivenTest  extends TestCase {

	public void runTest(String name) throws Exception {
		Main.setLogLevel(Level.ALL);
		File bagDir = Util.createTempDirectory("ScriptDrivenTest_bag");
		try {
			//Object ret = servlet.handle(ParsedRequest.makeExecute("_meta", "40+2"));
			//assertEquals(42, ((Number)ret).intValue());
			ScriptEngine pythonEngine = new ScriptEngineManager().getEngineByName("python");
			String resourcePath = "/dovetaildb/script_driven_test.py";
			String codeFilename = this.getClass().getResource(resourcePath).getPath();
			Bindings bindings = pythonEngine.createBindings();
			bindings.put("bagdir", bagDir.getAbsolutePath());
			bindings.put("testtype", name);
			pythonEngine.put(ScriptEngine.FILENAME, codeFilename);
			pythonEngine.eval(new InputStreamReader(this.getClass().getResourceAsStream(resourcePath)), bindings);
		} finally {
			Util.deleteDirectory(bagDir);
		}
	}

	public void testBasic() throws Exception {
		runTest("basic");
	}
	
	public static void main(String[] argv) throws Exception {
		// load test
		new ScriptDrivenTest().runTest("load");
	}
	
}
