package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.jjsc.CommonJJSTestCase;

public abstract class JavaScriptCompilationTestCase extends CommonJJSTestCase {
	
	protected String build(final String script) throws IOException {
		StringWriter result = new StringWriter();
		new JavaScriptBuilder("aa",
				new ResourcesResolver() {
					public Reader getResource(String name) throws IOException {
						return new StringReader(script);
					}
				}).compile(result);
		return result.toString();
	}
	
	protected JavaScriptTokenizer tokenizer(String str) {
		return new JavaScriptTokenizer(new StringReader(str));
	}
}
