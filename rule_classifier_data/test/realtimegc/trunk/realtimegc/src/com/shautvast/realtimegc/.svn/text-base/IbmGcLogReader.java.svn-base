package com.shautvast.realtimegc;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.shautvast.realtimegc.util.ConcatReader;
import com.shautvast.realtimegc.util.NBBufferedInputStreamReader;
import com.shautvast.realtimegc.util.RegionReader;
import com.shautvast.realtimegc.util.TailStream;

/**
 * first filters out all xml elements in the native_stderr.log uses xmlstream to
 * parse the xml elements, and retrieves relevant values
 * 
 * 
 * @author sander
 * 
 */
public class IbmGcLogReader implements GcLogReader {
	private String fileName;
	private XMLStreamReader xmlReader;

	private DateTimeFormatter[] formatters = new DateTimeFormatter[2];
	private volatile AtomicInteger dateTimeFormatterIndex=new AtomicInteger(0);

	public IbmGcLogReader(String fileName) {
		super();
		this.fileName = fileName;
		try {
			init();
		} catch (IOException e) {
			throw new GcLogViewException(e);
		} catch (XMLStreamException e) {
			throw new GcLogViewException(e);
		}
	}

	public Measurement read() {
		try {
			Measurement m = getGCValues(xmlReader);
			return m;
		} catch (XMLStreamException e) {
			throw new GcLogViewException(e);
		}
	}

	private Measurement getGCValues(XMLStreamReader reader)
			throws XMLStreamException {
		Measurement m = new Measurement();
		int ix = 0;
		String att;

		while (reader.hasNext()) {
			int eventId = reader.next();
			if (eventId == 1) {
				// TODO allow gencon gc

				if (reader.getLocalName().equals("af")) {
					for (int i = 0; i < reader.getAttributeCount(); i++) {
						if (reader.getAttributeLocalName(i).equals("timestamp")) {
							m.setTime(parseTime(reader.getAttributeValue(i)));
						}
					}
				}
				if (reader.getLocalName().equals("tenured")) {
					ix++;
					if (ix == 3) {
						ix = 0;
						for (int i = 0; i < reader.getAttributeCount(); i++) {
							if ((att = reader.getAttributeLocalName(i))
									.equals("freebytes")) {
								m.setFreeMem(Long.parseLong(reader
										.getAttributeValue(i)));
							} else if (att.equals("totalbytes")) {
								m.setTotalMem(Long.parseLong(reader
										.getAttributeValue(i)));
							}
						}

					}
				}
				if (m.getFreeMem() != -1 && m.getTotalMem() != -1
						&& m.getTime() != null) {
					return m;
				}
			}
		}
		return m;
	}

	/**
	 * on windows other format (it seems), but try both, if error occurs 
	 * @param attributeValue
	 * @return
	 */
	private Date parseTime(String attributeValue) {
		try {
			return formatters[dateTimeFormatterIndex.get()].parseDateTime(attributeValue).toDate();
		} catch (Exception e) {
			int i = dateTimeFormatterIndex.get();
			dateTimeFormatterIndex.compareAndSet(i, 1-i);//atomic
			return formatters[dateTimeFormatterIndex.get()].parseDateTime(attributeValue).toDate();
		}
	}

	private void init() throws IOException, XMLStreamException {
		DateTimeFormatter dateTimeFormatter1 = DateTimeFormat.forPattern(
				"MMM dd HH:mm:ss yyyy").withLocale(Locale.ENGLISH);
		DateTimeFormatter dateTimeFormatter2 = DateTimeFormat.forPattern(
				"EEE MMM dd HH:mm:ss yyyy").withLocale(Locale.ENGLISH);
		formatters[0] = dateTimeFormatter1;
		formatters[1] = dateTimeFormatter2;
		TailStream tailStream = new TailStream(fileName, true);

		RegionReader regionReader = new RegionReader(
				new NBBufferedInputStreamReader(tailStream), "<af", "</af>");
		// TODO allow <sys>...darn
		regionReader.setSkipping(false);
		regionReader.setIncludingMarkers(true);

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING,
				Boolean.FALSE);
		/*
		 * virtual root element is prepended to the stream, to avoid having
		 * multiple roottags
		 */
		xmlReader = xmlInputFactory.createXMLStreamReader(new ConcatReader(
				new StringReader("<root>"), regionReader));
	}

	public String getFileName() {
		return fileName;
	}

}
