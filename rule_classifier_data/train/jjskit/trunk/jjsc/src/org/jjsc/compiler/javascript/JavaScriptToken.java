package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Writer;

/**
 * Represents single token sequence. Each token can represent only one syntax point (e.g. indentifier or literal).
 * 
 * @author alex.bereznevatiy@gmail.com
 */
class JavaScriptToken {
	private char[] content;
	private int line;
	/**
	 * Creates token for char array.
	 * Note that this operation is not safe because if array will be modified
	 * the token will also be modified. This was done for performance reason.
	 * @param content
	 * @param line number
	 */
	JavaScriptToken(char[] content, int line) {
		if (content == null) {
			throw new JavaScriptParseError("Internal error");
		}
		this.content = content;
		this.line = line;
	}
	
	boolean isComment() {
		return content.length>1 && content[0] == '/' && (content[1] == '*' || content[1] == '/');
	}
	/**
	 * Writes this token to output.
	 * @param out
	 * @throws IOException 
	 */
	void write(Writer out) throws IOException {
		out.write(content);
	}

	@Override
	public String toString() {
		return new String(content);
	}

	public String getBuilderTag() {
		if (content[1] != '/') {
			return null;
		}
		final int len = content.length;
		int i=2;
		for (;i<len && Character.isWhitespace(content[i]);i++) ;
		int start = i;
		if (i>=len || content[i++]!='@') {
			return null;
		}
		if (i>=len || !Character.isJavaIdentifierStart(content[i])) {
			return null;
		}
		for (;i<len && Character.isJavaIdentifierPart(content[i]);i++);
		
		return new String(content, start, i - start);
	}

	public String getBuilderTagParameters() {
		final int len = content.length;
		int i=2;
		for (;i<len && content[i]!='@';i++) ;
		i++;
		for (;i<len && Character.isJavaIdentifierPart(content[i]);i++);
		int start = ++i;
		
		return new String(content, start, content.length - start);
	}

	public int getLineNumber() {
		return line;
	}
}
