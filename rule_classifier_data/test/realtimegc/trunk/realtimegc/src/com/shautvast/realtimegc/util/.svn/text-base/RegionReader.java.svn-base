package com.shautvast.realtimegc.util;

import java.io.IOException;
import java.io.Reader;

/**
 * Buffering reader that can skip or include (multiple) regions, indicated by
 * marker strings. The markers are String literals (ie. no regex patterns!)
 * 
 * should not be used in multiple threads!
 * 
 * @author sander
 * 
 */
public class RegionReader extends Reader {

	private static final int DEFAULT_BUFFERSIZE = 8192;

	/**
	 * marking start and end of a region
	 */
	private String startMark, endMark;

	/**
	 * if skipping, the text between the markers will be skipped if !skipping,
	 * the text outside the markers will be skipped and the text between will be
	 * included
	 */
	private boolean skipping = true;

	/**
	 * if true, the markers themselves will be included in the output
	 */
	private boolean includingMarkers = false;

	/* internal use only */
	/**
	 * if true, the startMark has been read and the endMark not yet. if false,
	 * the startMark has not been read.
	 */
	private boolean inside = false;

	/**
	 * either startMark, or endMark, depending on the inside flag
	 */
	String key;
	/**
	 * the internal buffer
	 */
	private StringBuilder bufferOut;

	/**
	 * indicates end of file
	 */
	private boolean eof = false;

	/**
	 * the reader that supplies input
	 */
	private Reader nestedReader;

	public RegionReader(Reader reader, String start, String end) {
		super();
		this.nestedReader = reader;
		this.startMark = start;
		this.endMark = end;
		bufferOut = new StringBuilder(DEFAULT_BUFFERSIZE);
	}

	public RegionReader(Reader reader, int buffersize, String start, String end) {
		super();
		this.nestedReader = reader;
		this.startMark = start;
		this.endMark = end;
		bufferOut = new StringBuilder(buffersize);
	}

	@Override
	public void close() throws IOException {
		nestedReader.close();
	}

	@Override
	public int read(char[] buf, int off, int lengthNeeded) throws IOException {
		if (!eof) {
			/* flip the key depending on inside or not */
			key = inside ? endMark : startMark;

			// while (bufferOut.length() < lengthNeeded && !eof) {
			StringBuilder b = doRead();

			bufferOut.append(b);
		}
		if (eof && bufferOut.length() == 0)
			return -1;
		int len = Math.min(lengthNeeded, bufferOut.length());
		System.arraycopy(bufferOut.toString().toCharArray(), 0, buf, off, len);
		bufferOut.delete(0, len);
		return len;

	}

	/**
	 * reads characters from the nested reader tries to match the start/end
	 * Markers
	 * 
	 * @return a StringBuilder with the characters read that should be included
	 * @throws IOException
	 *             only if the underlying reader does.
	 */
	private StringBuilder doRead() throws IOException {
		int indexOfMatchStart = 0;
		boolean mInside = inside;// store current value;
		boolean matchFound = false;
		int keyIndex = 0;
		int c = 0;
		StringBuilder temp = new StringBuilder();
		for (boolean couldMatch = false; !matchFound;) {
			for (; c != -1 && keyIndex < key.length();) {
				c = nestedReader.read();
				/* always include eof indicator */
				if (((skipping && !inside) || (!skipping && inside)) && c != -1) {
					temp.append((char) c);
				}
				/* end of file */
				if (c == -1) {
					eof = true;
					return temp;
				}

				/*
				 * the labeled block will increment a counter every time the
				 * next character in the key sequence matches the current
				 * character in the stream. Because in case of an incomplete
				 * match, there is a possibility that the current char (which is
				 * not the next character in the key), is actually the first
				 * character of the same key. We have to check for that case.
				 * Hence the loop. Using a goto statement would be nicer.
				 */
				matchCharacter: for (;;) {
					if (key.charAt(keyIndex) == c) {
						/* we may have a match, go to next character in key */
						keyIndex++;
						if (!couldMatch) {
							indexOfMatchStart = temp.length();
							couldMatch = true;

						}
						break matchCharacter;
					} else {
						/* reject incomplete match */
						if (couldMatch && keyIndex < key.length()) {
							couldMatch = false;
							keyIndex = 0;// recheck
						} else
							break matchCharacter;
					}
				}
			}
			/* we´re at end of key, so there is a match */
			matchFound = true;
			mInside = inside;/* first: remember old value... */
			inside = inside ^ true;// toggle
			key = inside ? endMark : startMark;

			/*
			 * ...then: use old value, because we can´t execute the return
			 * statement first
			 */
			if ((skipping && !mInside) || (!skipping && mInside)) {
				if (includingMarkers) {
					/*
					 * reinclude startMark, because it´s lost in the stream
					 * itself, the endMark, by it´s nature is not
					 */
					temp.insert(0, startMark);
				} else {
					/* delete the startMark, because it´s included here */
					temp.delete(indexOfMatchStart - 1, temp.length());
				}
				return temp;
			} else {
				/* reset */
				matchFound = false;
				keyIndex = 0;
				couldMatch = false;
			}
		}
		throw new RuntimeException();// seems unreachable to me
	}

	public boolean isSkipping() {
		return skipping;
	}

	public void setSkipping(boolean skipping) {
		this.skipping = skipping;
	}

	@Override
	public int read() throws IOException {
		char[] c = new char[1];
		int n = read(c, 0, 1);
		if (n == -1)
			return -1;
		return c[0];
	}

	public boolean isIncludingMarkers() {
		return includingMarkers;
	}

	public void setIncludingMarkers(boolean includeMarkers) {
		this.includingMarkers = includeMarkers;
	}
}
