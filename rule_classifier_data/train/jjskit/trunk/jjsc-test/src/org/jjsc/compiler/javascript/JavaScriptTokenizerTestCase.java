package org.jjsc.compiler.javascript;

import java.io.IOException;

public class JavaScriptTokenizerTestCase extends JavaScriptCompilationTestCase {
	public void testComments() throws IOException {
		assertEquals("//ss", tokenizer("//ss").next().toString());
		
		JavaScriptTokenizer tokenizer = tokenizer("aaaa//ss\ndd");
		tokenizer.next();
		assertEquals("//ss\n", tokenizer.next().toString());
		
		JavaScriptTokenizer tokenizer2 = tokenizer("aaaa/*ss");
		tokenizer2.next();
		try {
			tokenizer2.next();
			fail();
		} catch(JavaScriptParseError ex) {}
		
		JavaScriptTokenizer tokenizer3 = tokenizer("aaaa/*ss\nss\nasda*/ss\n sdf");
		tokenizer3.next();
		assertEquals("/*ss\nss\nasda*/", tokenizer3.next().toString());
	}

	public void testIdentifiers() {
		fail("Not implemented");
	}
	
	public void testStrings() throws IOException {
		assertEquals("'ss'", tokenizer("'ss'").next().toString());
		assertEquals("\"ss\"", tokenizer("\"ss\"").next().toString());
		assertEquals("'//ss'", tokenizer("'//ss'").next().toString());
		assertEquals("\"//ss\"", tokenizer("\"//ss\"").next().toString());
		assertEquals("'\\'ss\\\\'", tokenizer("'\\'ss\\\\'").next().toString());
		assertEquals("\"\\\"//ss\\\\\"", tokenizer("\"\\\"//ss\\\\\"").next().toString());
		
		JavaScriptTokenizer tok = tokenizer("run('java.lang.MyMainClass','http://localhost:8080/index.js','http://localhost:8080/ss.js');");
		assertEquals("run", tok.next().toString());
		assertEquals("(", tok.next().toString());
		assertEquals("'java.lang.MyMainClass'", tok.next().toString());
		assertEquals(",", tok.next().toString());
		assertEquals("'http://localhost:8080/index.js'", tok.next().toString());
		assertEquals(",", tok.next().toString());
		assertEquals("'http://localhost:8080/ss.js'", tok.next().toString());
	}
	
	public void testDigits() {
		fail("Not implemented");
	}
}
