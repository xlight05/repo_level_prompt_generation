package com.xebia.gclogviewer;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Scrap {
	public static void main(String[] args) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormat
				.forPattern("EEE MMM dd HH:mm:ss yyyy");
		DateTime parseDateTime = dateTimeFormatter.withLocale(Locale.ENGLISH).parseDateTime("Tue Jan 12 15:49:18 2010");
		System.out.println(parseDateTime);
		DateTime dateTime = new DateTime();
		System.out.println(dateTimeFormatter.withLocale(Locale.ENGLISH).print(dateTime));
	}
}
