package com.shautvast.realtimegc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * skips any matching pattern in the input. Accepts only single character strings in the expression.
 * So "g" will make it skip any "g"s
 * And "g|h" will make it skip any "g" and any "h". 
 * NB. It won´t work if you want to skip "gh" (using that expression) excluding single "g"s and "h"s.
 * Sorry for that.
 * <br/> 
 * @notthreadsafe.
 * @author sander
 * 
 */
public class PatternSkippingReader extends BufferedReader {
	private Reader reader;
	private Pattern pattern;

	public PatternSkippingReader(Reader in, String expression) {
		super(in);
		this.reader = in;
		pattern = Pattern.compile(expression);
	}

	public PatternSkippingReader(Reader in, int size, String expression) {
		super(in, size);
		this.reader = in;
		pattern = Pattern.compile(expression);
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		reader.read(cbuf, off, len);
		Matcher matcher = pattern.matcher(new String(cbuf, off, len));
		String result = matcher.replaceAll("");
		int len2 = result.length();
		System.arraycopy(result.toCharArray(), 0, cbuf, 0, len2);
		return len;
	}

	@Override
	public int read() throws IOException {
		/*must continue until non-skippable character or eof*/
		for (;;) {
			int i=reader.read();
			if (i==-1){
				return i;
			}
			char c = (char) i;
			Matcher matcher = pattern.matcher(new String(new char[] { c }));
			String result = matcher.replaceAll("");
			if (result.length() == 1) {
				char r=result.toCharArray()[0];
				return r;
			}
		}
	}
}
