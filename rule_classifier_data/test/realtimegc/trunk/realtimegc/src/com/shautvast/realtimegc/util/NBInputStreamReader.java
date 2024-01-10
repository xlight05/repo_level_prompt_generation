package com.shautvast.realtimegc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * implements a non-blocking version of java.io.InputStreamReader (that uses
 * buffering)
 * 
 * @author sander
 * 
 */
public class NBInputStreamReader extends Reader {
	private InputStream in;
	private byte[] byteBuffer = new byte[1];
	private ByteBuffer nioByteBuffer = ByteBuffer.allocate(1);
	private CharBuffer nioCharBuffer = CharBuffer.allocate(1);

	private final static Charset DEFAULT_CHARSET = Charset
			.forName("ISO-8859-1");
	private CharsetDecoder decoder;

	public NBInputStreamReader(InputStream in) {
		super();
		decoder = DEFAULT_CHARSET.newDecoder();
		this.in = in;
	}

	public NBInputStreamReader(Object lock, FileInputStream in) {
		super(lock);
		decoder = DEFAULT_CHARSET.newDecoder();
		this.in = in;
	}

	public NBInputStreamReader(InputStream in, String charset) {
		super();
		this.in = in;
		decoder = Charset.forName(charset).newDecoder();
	}

	public NBInputStreamReader(Object lock, FileInputStream in, String charset) {
		super(lock);
		this.in = in;
		decoder = Charset.forName(charset).newDecoder();
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int lenRead = in.read(byteBuffer, off, len);
		if (lenRead == -1)
			return -1;
		nioByteBuffer.put(byteBuffer, off, lenRead);
		nioByteBuffer.flip();
		decoder.decode(nioByteBuffer, nioCharBuffer, false);
		nioCharBuffer.flip();
		nioCharBuffer.get(cbuf, off, lenRead);
		nioByteBuffer.clear();
		nioCharBuffer.clear();
		return lenRead;
	}

}
