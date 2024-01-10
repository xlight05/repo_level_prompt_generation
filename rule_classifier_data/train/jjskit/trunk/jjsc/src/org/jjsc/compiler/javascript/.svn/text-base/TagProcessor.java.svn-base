package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>Provides simple extension engine for {@link JavaScriptBuilder}.
 * Tags for javascript builder are simular to java annotations, except for they starts with 
 * single line comment opening. For example:</p>
 * <pre><code>
 * // @Include path/to/file
 * </code></pre>
 * <p>After the name of tag there should be non-identifier char and some custom parameters in 
 * tag-specific format. E.g. @Include tag should provide the path to include.</p>
 * 
 * <p>All custom implementations should be registered using method 
 * {@link JavaScriptBuilder#registerTag(String tagName, TagProcessor processor)}.</p>
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public interface TagProcessor {
	/**
	 * Processes the tag associated with this processor passing tag parameters and 
	 * writer to store the result of processing. Resource resolver is passed to provide 
	 * more flexibility to processors.
	 * @param parameters
	 * @param resolver 
	 * @param result
	 * @param fileName
	 * @param line
	 * @throws IOException in case of troubles
	 */
	void process(String parameters, ResourcesResolver resolver, Writer result, String fileName, int line) throws IOException;
}
