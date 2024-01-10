package com.xebia.gclogviewer.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Assert;

import nl.kvk.vt.monitoring.gc.TestUtils;

import org.junit.Test;

import com.shautvast.realtimegc.util.NBBufferedInputStreamReader;
import com.shautvast.realtimegc.util.NBInputStreamReader;

public class NBInputstreamReaderTest {

	@Test
	public void readingHello() {
		ByteArrayInputStream bais = new ByteArrayInputStream("hello".getBytes());
		NBBufferedInputStreamReader nbInputStreamReader = new NBBufferedInputStreamReader(bais);
		String hello = TestUtils.readAll(nbInputStreamReader);
		Assert.assertEquals("hello", hello);
	}

	@Test
	public void readingFile() {
		long t0 = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			InputStream is = getClass().getResourceAsStream(
					"/native_stderr.log");
			NBInputStreamReader nbInputStreamReader = new NBInputStreamReader(
					is);
			TestUtils.readAll(nbInputStreamReader);
		}
		long t1 = System.nanoTime();
		System.out.println("non-buffering:"+(t1 - t0));
	}
	
	@Test
	public void readingBufferedFile() {
		long t0 = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			InputStream is = getClass().getResourceAsStream(
					"/native_stderr.log");
			NBBufferedInputStreamReader nbInputStreamReader = new NBBufferedInputStreamReader(
					is);
			TestUtils.readAll(nbInputStreamReader);
		}
		long t1 = System.nanoTime();
		System.out.println("buffering:"+(t1 - t0));
	}
}
