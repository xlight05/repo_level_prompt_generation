package org.jjsc.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Utilities for the most common IO operations.
 * @author alex.bereznevatiy@gmail.com
 */
public class IO {
	private static final int BUFFER_SIZE = 1024;

	private IO(){}
	/**
	 * Redirects stream from input to output.
	 * After this method invoked the input stream will be read fully
	 * and output stream will point on the position after last written byte.
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void flow(InputStream input,OutputStream output) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		int len = 0;
		while((len = input.read(buf))>0){
			output.write(buf, 0, len);
		}
	}
	/**
	 * Redirects characters stream from input to output.
	 * After this method invoked the reader will be read fully
	 * and writer will point on the position after last written char.
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void flow(Reader input,Writer output) throws IOException {
		char[] cbuf = new char[BUFFER_SIZE];
		int len = 0;
		while((len = input.read(cbuf))>0){
			output.write(cbuf, 0, len);
		}
	}
	/**
	 * Reads the reader passed and returns the result as string.
	 * @param reader
	 * @return string presentation of reader.
	 * @throws IOException
	 */
	public static String toString(Reader reader) throws IOException {
		StringWriter output = new StringWriter();
		flow(reader,output);
		return output.toString();
	}
	/**
	 * Closes stream passed on the best-try basis.
	 * All exceptions will be ignored.
	 * @param stream
	 */
	public static void close(Closeable stream) {
		if (stream == null) return;
		try {
			stream.close();
		} catch(IOException ex) {} // ignore
	}
}
