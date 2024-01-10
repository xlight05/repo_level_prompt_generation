package org.jjsc.compiler.javascript;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.jjsc.modules.RelativeFileSystemResourcesResolver;
import org.jjsc.utils.IO;
/**
 * Javascript compiler and processor.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class JavaScriptBuilder {
	private static final Map<String, TagProcessor> processors;
	
	static {
		processors = new HashMap<String, TagProcessor>();
		processors.put("@Include", new IncludeTagProcessor());
		processors.put("@Version", new VersionTagProcessor());
		processors.put("@TODO", new TodoTagProcessor());
	}
	
	private String name;
	private ResourcesResolver resolver;
	/**
	 * Registers handler for the tag with name specified.
	 * @param name
	 * @param processor
	 * @throws IllegalArgumentException if handler is already exists
	 * @throws NullPointerException if one of the parameters is <code>null</code>.
	 */
	public static void registerTag(String name, TagProcessor processor) {
		if (name == null || processor == null) {
			throw new NullPointerException();
		}
		if (processors.containsKey(name)) {
			throw new IllegalArgumentException("Attempt to register already registered tag processor with name: "+name);
		}
		processors.put(name, processor);
	}
	
	/**
	 * Creates javascript builder for source file.
	 * @param source
	 */
	public JavaScriptBuilder(File source) {
		this(source.getName(), new RelativeFileSystemResourcesResolver(source.getParentFile()));
	}
	/**
	 * 
	 * @param name
	 * @param resolver
	 */
	public JavaScriptBuilder(String name, ResourcesResolver resolver) {
		this.name = name;
		this.resolver = resolver;
	}
	/**
	 * Compiles content associated with this javascript builder and writes result to
	 * target writer.
	 * @param target
	 * @throws IOException
	 */
	public void compile(Writer target) throws IOException {
		Reader in = null;
		
			in = resolver.getResource(name);
			BufferedWriter out = wrapWithBuffer(target);
			JavaScriptTokenizer tokenizer = new JavaScriptTokenizer(in);
			JavaScriptToken tst = null;
		try {
			JavaScriptToken token;
			while ((token = tokenizer.next()) != null) {tst = token;
				processToken(token, out);
			}
			out.flush();
		} catch(IndexOutOfBoundsException ex) {
			throw new JavaScriptParseError("`" + tst + "` " + name+" at "+tst.getLineNumber(),ex);
		} finally {
			IO.close(in);
		}
	}

	private BufferedWriter wrapWithBuffer(Writer target) {
		if (target instanceof BufferedWriter) {
			return (BufferedWriter)target;
		}
		return new BufferedWriter(target);
	}

	private void processToken(JavaScriptToken token, BufferedWriter out) throws IOException {
		if (!token.isComment()) {
			token.write(out);
			return;
		}
		String tag = token.getBuilderTag();
		if (tag != null && processors.containsKey(tag)) {
			processors.get(tag).process(token.getBuilderTagParameters(), resolver, out, name, token.getLineNumber());
		}
	}
}
