package com.shautvast.realtimegc.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Concatenates (data of) underlying collections of readers
 * 
 * Should not be used in more than 1 thread
 * 
 * @author sander
 * 
 */
public class ConcatReader extends Reader {
	private List<Reader> readers;
	private Reader currentReader;
	private Iterator<Reader> readerIterator;

	public ConcatReader(List<Reader> readers) {
		super();
		this.readers = readers;
	}

	public ConcatReader(Reader... readers) {
		super();
		this.readers = Arrays.asList(readers);
	}

	@Override
	public void close() throws IOException {
		boolean exceptionCaught = false;
		for (Reader reader : readers) {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
				exceptionCaught = true;
			}
		}
		if (exceptionCaught) {
			throw new IOException("while closing one of the readers");
		}
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (readerIterator == null) {
			readerIterator = readers.iterator();
		}
		for (;;) {	
			if (currentReader == null) {
				if (readerIterator.hasNext()) {
					currentReader = readerIterator.next();
				} else
					return -1;
			}
			int n = currentReader.read(cbuf, off, len);
			if (n == -1) {
				currentReader = null;
			} else {
				return n;
			}
			
		}
	}

}
