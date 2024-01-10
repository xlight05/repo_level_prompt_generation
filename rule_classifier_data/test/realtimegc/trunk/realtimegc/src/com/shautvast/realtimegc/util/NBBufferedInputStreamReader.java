package com.shautvast.realtimegc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Implements a buffering non-blocking version of java.io.InputStreamReader
 * Tries to read as much values from the stream and buffers them. Tests indicate
 * approximately 100% improvement over non/buffering version
 * 
 * Should not be used in multiple concurrent threads
 * 
 * @author sander
 * 
 */
public class NBBufferedInputStreamReader extends Reader {
	private static final int DEFAULT_BUFFERSIZE = 8192;
	private InputStream in;
	private int bufferSize;
	private int charsInBuffer;
	private byte[] byteBuffer;
	private ByteBuffer nioByteBuffer;
	private CharBuffer nioCharBuffer;
	private boolean eof = false;
	private final static Charset DEFAULT_CHARSET = Charset
			.forName("ISO-8859-1");
	private CharsetDecoder decoder;

	public NBBufferedInputStreamReader(InputStream in) {
		this(in, DEFAULT_BUFFERSIZE);
	}

	public NBBufferedInputStreamReader(InputStream in, int bufferSize) {
		super();
		decoder = DEFAULT_CHARSET.newDecoder();
		this.in = in;
		this.bufferSize = bufferSize;
		charsInBuffer = 0;
		byteBuffer = new byte[bufferSize];
		nioByteBuffer = ByteBuffer.allocate(bufferSize);
		nioCharBuffer = CharBuffer.allocate(bufferSize);
	}

	public NBBufferedInputStreamReader(InputStream in, String charset) {
		super();
		this.in = in;
		decoder = Charset.forName(charset).newDecoder();
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	/*
	 * have to do my own bookkeeping of nioCharBuffer (charsInBuffer) to avoid
	 * having to call flip (only call after conversion)
	 */
	@Override
	public int read(char[] cbuf, int off, int lengthRequested)
			throws IOException {
		if (!eof && charsInBuffer == 0) {
			/* do not read more than will fit in buffer */
			int bytesAvailable = Math.min(in.available(), bufferSize
					- charsInBuffer);
			/* read at least 1 byte */
			int lenReadFromStream = in.read(byteBuffer, off, Math.max(bytesAvailable, 1));
			/* if something is read, convert it */
			if (lenReadFromStream > 0) {
				charsInBuffer += lenReadFromStream;
				nioByteBuffer.put(byteBuffer, off, lenReadFromStream);
				nioByteBuffer.flip();
				decoder.decode(nioByteBuffer, nioCharBuffer, false);
				nioCharBuffer.flip();
				nioByteBuffer.clear();
			} else if (lenReadFromStream == -1) {
				eof = true;
			}
		}
		/* eof is possible here, but the buffer may still be non-empty */
		if (charsInBuffer > 0) {
			int len = Math.min(charsInBuffer, lengthRequested);
			nioCharBuffer.get(cbuf, off, len);
			charsInBuffer -= len;
			if (charsInBuffer == 0) {
				nioCharBuffer.clear();
			}
			return lengthRequested;
		}
		if (eof) {// buffer is empty and eof reached
			return -1;
		}
		/* no input, no eof and nothing in buffer */
		return 0;
	}

	public int getBufferSize() {
		return bufferSize;
	}

}
