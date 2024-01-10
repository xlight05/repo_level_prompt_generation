package org.jjsc.compiler.javascript;

import java.io.IOException;
import java.io.Reader;
/**
 * Reads javascript source file and returns separate tokens from it.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
final class JavaScriptTokenizer {
	private static final int BUFFER_SIZE = 50;
	private static final int BUFFER_MAX_SIZE = 5000;
	
	private char[] buf;
	private int pos;
	private int max;
	private int line;
	private Reader charSource;
	/**
	 * Creates tokenizer for the reader passed.
	 * Reading process will be performed as lazily as possible.
	 * @param charSource
	 */
	JavaScriptTokenizer(Reader charSource) {
		this.charSource = charSource;
		this.buf = new char[BUFFER_SIZE];
		this.line = 1;
	}

	JavaScriptToken next() throws IOException {
		clean();
		
		if (checkForMoreChars()) {
			return null;
		}
		int tokStart = pos;
		if (checkLineEnd()) {
			this.line++;
			if (checkForMoreChars()) {
				return null;
			}
		}
		char currentChar = buf[pos];
		if (currentChar == '\'' || currentChar == '"') {
			return readStringLiteral(currentChar);
		}
		
		if (Character.isJavaIdentifierStart(currentChar)) {
			return readIdentifier();
		}
		if (currentChar != '/') {
			char[] rez = new char[pos-tokStart+1];
			for(int i=pos++;i>=tokStart;i--){
				rez[i-tokStart] = buf[i];
			}
			return new JavaScriptToken(rez, line);
		}
		if ((++pos >= max && !fill()) || (buf[pos] != '/' && buf[pos] != '*')) {
			return new JavaScriptToken(new char[]{buf[pos-1]}, line);
		}
		return readComment();
	}

	private JavaScriptToken readStringLiteral(char end) throws IOException {
		int start = pos++;
		if (checkForMoreChars()) {
			throw new JavaScriptParseError("Non-terminated string literal");
		}
		while (buf[pos] != end) {
			if (isLineEnd() || (++pos >= max && !fill())) {
				throw new JavaScriptParseError("Non-terminated string literal");
			}
			if (buf[pos] == end && isEscaped()) {
				pos++;
			}
		}
		char[] rez = new char[pos-start+1];
		for(int i=pos++;i>=start;i--){
			rez[i-start] = buf[i];
		}
		return new JavaScriptToken(rez, line);
	}

	private boolean isEscaped() {
		int i = pos;
		while(i-->0 && buf[i] == '\\');
		return (pos - i) % 2 == 0;
	}

	private boolean checkForMoreChars() throws IOException {
		return pos >= max && !fill();
	}
	
	private JavaScriptToken readComment() throws IOException {
		int start = pos - 1;
		if (buf[pos] == '/') {
			toLineEnd();
			char[] rez = new char[pos - start];
			System.arraycopy(buf, start, rez, 0, rez.length);
			return new JavaScriptToken(rez, line++);
		}
		int line = this.line;
		while(++pos < max || fill()) {
			if (checkLineEnd()) {
				this.line++;
			}
			if (buf[pos] != '*') {
				continue;
			}
			if (++pos >= max && !fill()) {
				break;
			}
			if (buf[pos] == '/') {
				char[] rez = new char[++pos - start];
				if (checkLineEnd()) {
					this.line++;
				}
				System.arraycopy(buf, start, rez, 0, rez.length);
				return new JavaScriptToken(rez, line);
			}
		}
		throw new JavaScriptParseError("Non-terminated comment");
	}

	private boolean checkLineEnd() throws IOException {
		if(!isLineEnd()) {
			return false;
		}
		if (isNextCarriageReturn()) {
			pos++;
		}
		return true;
	}

	private void toLineEnd() throws IOException {
		while((++pos < max || fill()) && !checkLineEnd()) ;
		if (!checkForMoreChars()) pos++;
	}
	
	private boolean isLineEnd() {
		return buf[pos] == '\n' || buf[pos] == '\r';
	}
	
	private boolean isNextCarriageReturn() throws IOException {
		pos++;
		if (checkForMoreChars()||buf[pos] != '\r') {
			pos--;
			return false;
		}
		return true;
	}

	private JavaScriptToken readIdentifier() throws IOException {
		int start = pos;
		while ((++pos < max || fill()) && Character.isJavaIdentifierPart(buf[pos]));
		char[] rez = new char[pos - start];
		System.arraycopy(buf, start, rez, 0, rez.length);
		return new JavaScriptToken(rez, line);
	}
	
	private boolean fill() throws IOException {
		int dif = buf.length - max;
		if (dif < BUFFER_SIZE) {
			char[] tmp = buf;
			buf = new char[buf.length << 1];
			System.arraycopy(tmp, 0, buf, 0, max);
			dif += BUFFER_SIZE;
		}
		dif = charSource.read(buf, pos, dif);
		if (dif <=0 ) {
			return false;
		}
		max += dif;
		return true;
	}

	private void clean() {
		if (buf.length < BUFFER_MAX_SIZE) {
			return;
		}
		max -= pos;
		System.arraycopy(buf, pos, buf, 0, max);
		pos = 0;
	}
}
