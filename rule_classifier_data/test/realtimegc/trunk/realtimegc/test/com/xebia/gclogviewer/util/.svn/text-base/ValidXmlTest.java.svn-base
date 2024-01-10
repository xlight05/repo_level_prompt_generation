package com.xebia.gclogviewer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.EventFilter;
import javax.xml.stream.Location;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.junit.Test;

import com.shautvast.realtimegc.util.ConcatReader;
import com.shautvast.realtimegc.util.NBBufferedInputStreamReader;
import com.shautvast.realtimegc.util.RegionReader;
import com.shautvast.realtimegc.util.TailStream;

public class ValidXmlTest {

	
	public void testXMLSTream() throws XMLStreamException, IOException {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING,
				Boolean.FALSE);
		RegionReader regionReader = new RegionReader(new InputStreamReader(
				new FileInputStream(getClass().getResource("/native_stderr.log")
						.getFile())), "<af", "</af>");
		regionReader.setSkipping(false);
		regionReader.setIncludingMarkers(true);
		ConcatReader concatReader=new ConcatReader(new StringReader("<root>"), regionReader,new StringReader("</root>"));
		XMLStreamReader reader = xmlInputFactory
				.createXMLStreamReader(concatReader);
		StreamFilter filter = new ElementOnlyFilter();
		reader = xmlInputFactory.createFilteredReader(reader, filter);

		getGCValues(reader);
	}

	private int getGCValues(XMLStreamReader reader) throws XMLStreamException {
		int counter = 1;
		int ix=0;
		String att;
		while (reader.hasNext()) {
			int eventId = reader.next();
			if (eventId==1){
				if (reader.getLocalName().equals("tenured")){
					ix++;
					if (ix==3){
						ix=0;
						for (int i=0;i<reader.getAttributeCount();i++){
							if ((att=reader.getAttributeLocalName(i)).equals("freebytes")){
								System.out.println("free:"+reader.getAttributeValue(i));
							} else if (att.equals("totalbytes")){
								System.out.println("total:"+reader.getAttributeValue(i));
							}
							
						}
						
					}
				}
				
			}
			counter++;
		}
		return counter;
	}

	public void testReader() throws XMLStreamException, IOException {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING,
				Boolean.FALSE);
		XMLStreamReader reader = xmlInputFactory
				.createXMLStreamReader(new InputStreamReader(
						new FileInputStream(getClass().getResource("/gc.log")
								.getFile())));
		while (reader.hasNext()) {
			int eventType = reader.next();
			// System.out.println(reader.getLocalName());
		}
	}

	@Test
	public void testTailingParsing() throws IOException, XMLStreamException,
			ParserConfigurationException {
		String file = "somelog.txt";
		LogProducer logProducer = new LogProducer(file);
		String text = "<af type=\"tenured\" id=\"1\" timestamp=\"Thu Dec 17 22:30:28 2009\" intervalms=\"0.000\">\r\n"
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
		logProducer.setText(text);
		logProducer.start();
		
		TailStream tailStream = new TailStream(file, false);
		
		RegionReader regionReader = new RegionReader(new NBBufferedInputStreamReader(
				tailStream), "<af", "</af>");
		regionReader.setSkipping(false);
		regionReader.setIncludingMarkers(true);

		// DocumentBuilder builder =
		// DocumentBuilderFactory.newInstance().newDocumentBuilder();

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING,
				Boolean.FALSE);
		XMLStreamReader reader = xmlInputFactory
				.createXMLStreamReader(new ConcatReader(new StringReader("<root>"),regionReader));
		getGCValues(reader);
		
	}

	class ElementOnlyFilter implements EventFilter, StreamFilter {
		public boolean accept(XMLEvent event) {
			return accept(event.getEventType());
		}

		public boolean accept(XMLStreamReader reader) {
			return accept(reader.getEventType());
		}

		private boolean accept(int eventType) {
			return eventType == XMLStreamConstants.START_ELEMENT
					|| eventType == XMLStreamConstants.END_ELEMENT;
		}
	}
}
