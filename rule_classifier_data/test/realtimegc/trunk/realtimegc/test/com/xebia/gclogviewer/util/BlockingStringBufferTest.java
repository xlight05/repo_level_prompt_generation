package com.xebia.gclogviewer.util;

import junit.framework.Assert;

import org.junit.Test;

import com.shautvast.realtimegc.util.BlockingStringBuffer;

public class BlockingStringBufferTest {
	@Test
	public void testBlockingSttingBuffer() {
		final BlockingStringBuffer buffer = new BlockingStringBuffer(10);
		final String text = "public class ConnectionPool {"
				+ " private List<Connection> connections = createConnections();"
				+ " " + " private List<Connection> createConnections() {"
				+ "  List<Connection> conns = new ArrayList<Connection>(5);"
				+ " for (int i = 0; i < 5; i++) {"
				+ "  ... add a Connection to conns" + "}" + "return conns;"
				+ "}" + "}";
		Thread a, b;
		(a = new Thread() {
			public void run() {
				for (int i = 0; i < text.length(); i++) {
					try {
						Thread.sleep((int) (Math.random() * 10));
						buffer.add(text.charAt(i));
					} catch (InterruptedException e) {
					}

				}
			}
		}).start();

		final StringBuilder output = new StringBuilder();
		(b = new Thread() {
			public void run() {
				for (int i = 0; i < text.length(); i++) {
					try {
						Thread.sleep((int) (Math.random() * 10));
						char c = buffer.poll();
						output.append(c);
					} catch (InterruptedException e) {
					}

				}
			}
		}).start();
		try {
			a.join();
			b.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(text, output.toString());
	}
}
