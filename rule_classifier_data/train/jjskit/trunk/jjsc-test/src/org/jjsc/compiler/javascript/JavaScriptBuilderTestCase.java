package org.jjsc.compiler.javascript;

import java.io.IOException;

public class JavaScriptBuilderTestCase extends JavaScriptCompilationTestCase {
	public void testSimple() throws IOException {
		assertEquals("var a=5;", build("var a=5;"));
		assertEquals("var a={};", build("var a={};"));
		assertEquals("var a={};", build("var veryVeryLongVar={};"));
	}
}
