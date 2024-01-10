package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Writer;
/**
 * Processes @Include tags by including the resource javascript file from tag parameter.
 *  
 * @author alex.bereznevatiy@gmail.com
 */
public class IncludeTagProcessor implements TagProcessor {
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.javascript.TagProcessor#process(java.lang.String, org.jjsc.compiler.javascript.ResourcesResolver, java.io.BufferedWriter)
	 */
	public void process(String path, ResourcesResolver resolver,
			Writer result, String fileName, int line) throws IOException {
		new JavaScriptBuilder(path.trim(), resolver).compile(result);
	}
}
