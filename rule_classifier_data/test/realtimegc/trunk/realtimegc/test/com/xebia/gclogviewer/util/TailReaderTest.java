package com.xebia.gclogviewer.util;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.shautvast.realtimegc.util.TailStream;

public class TailReaderTest {
	@Test
	public void testTailing() throws IOException {
		String file = "somelog.txt";
		LogProducer logProducer = new LogProducer(file);
		String text="<af type=\"tenured\" id=\"1\" timestamp=\"Thu Dec 17 22:30:28 2009\" intervalms=\"0.000\">\r\n"
			+ "  <minimum requested_bytes=\"32\" />\r\n"
			+ "  <time exclusiveaccessms=\"0.011\" />\r\n"
			+ "  <tenured freebytes=\"2621440\" totalbytes=\"52428800\" percent=\"5\" >\r\n"
			+ "    <soa freebytes=\"0\" totalbytes=\"49807360\" percent=\"0\" />\r\n"
			+ "    <loa freebytes=\"2621440\" totalbytes=\"2621440\" percent=\"100\" />\r\n"
			+ "  </tenured>\r\n"
			+ "  <gc type=\"global\" id=\"1\" totalid=\"1\" intervalms=\"0.000\">\r\n"
			+ "    <classloadersunloaded count=\"0\" timetakenms=\"0.513\" />\r\n"
			+ "    <refs_cleared soft=\"139\" threshold=\"32\" weak=\"113\" phantom=\"0\" />\r\n"
			+ "    <finalization objectsqueued=\"24\" />\r\n"
			+ "    <timesms mark=\"12.931\" sweep=\"1.479\" compact=\"0.000\" total=\"14.994\" />\r\n"
			+ "    <tenured freebytes=\"41559200\" totalbytes=\"52428800\" percent=\"79\" >\r\n"
			+ "      <soa freebytes=\"38937760\" totalbytes=\"49807360\" percent=\"78\" />\r\n"
			+ "      <loa freebytes=\"2621440\" totalbytes=\"2621440\" percent=\"100\" />\r\n"
			+ "    </tenured>\r\n"
			+ "  </gc>\r\n"
			+ "  <tenured freebytes=\"41559168\" totalbytes=\"52428800\" percent=\"79\" >\r\n"
			+ "    <soa freebytes=\"38937728\" totalbytes=\"49807360\" percent=\"78\" />\r\n"
			+ "    <loa freebytes=\"2621440\" totalbytes=\"2621440\" percent=\"100\" />\r\n"
			+ "  </tenured>\r\n"
			+ "  <time totalms=\"15.229\" />\r\n"
			+ "</af>\r\n";
		logProducer
				.setText(text);
		logProducer.start();
		TailStream tailReader = new TailStream(file, false);
		StringBuilder sb=new StringBuilder();
		StringBuilder sbFromFile=new StringBuilder();
		for (int i = 0; i < 5; i++) {
			String section = tailReader.readEof();
			System.out.println(section);
			sbFromFile.append(section);
			sb.append(text);
		}
		Assert.assertEquals(sb.toString(), sbFromFile.toString());
	}
}
