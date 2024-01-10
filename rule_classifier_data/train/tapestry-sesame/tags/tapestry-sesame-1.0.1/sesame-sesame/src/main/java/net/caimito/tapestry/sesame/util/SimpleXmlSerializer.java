package net.caimito.tapestry.sesame.util;

import java.io.ByteArrayOutputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleXmlSerializer<T> {
	private static Logger logger = LoggerFactory.getLogger(SimpleXmlSerializer.class) ;
	
	public static <T> String convertToString(T xmlClass) {
		return convertToString(xmlClass, System.getProperty("file.encoding"));
	}

	public static <T> String convertToString(T xmlClass, String charsetName) {
		try {
			Serializer serializer = new Persister();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			serializer.write(xmlClass, outputStream);

			return outputStream.toString(charsetName);
		} catch (Exception e) {
			logger.error(e.getMessage()) ;
			return null ;
		}
	}
	
	public static <T> T convertFromString(Class<T> targetClass, String xml) {
		try {
			Serializer serializer = new Persister();
			return serializer.read(targetClass, xml);
		} catch (Exception e) {
			logger.error(e.getMessage()) ;
			return null ;
		}
	}
}
