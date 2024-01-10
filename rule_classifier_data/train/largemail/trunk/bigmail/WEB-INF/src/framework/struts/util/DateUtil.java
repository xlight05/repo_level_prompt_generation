package framework.struts.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil
{
	//private static Calendar today = Calendar.getInstance();
	
	/*
	 * String dateFormat  Timestamp ts <br>
	 *
	 * Examples
	 * The following examples show how date and time patterns are interpreted in the U.S. locale. 
	 * The given date and time are 2001-07-04 12:08:56 local time in the U.S. Pacific Time time zone. 
	 *
	 *		Date and Time Pattern          |          Result
	 *		"yyyy.MM.dd G 'at' HH:mm:ss z" | 2001.07.04 AD at 12:08:56 PDT  
	 *		"EEE, MMM d, ''yy"	           | Wed, Jul 4, '01  
	 *		"h:mm a"					   | 12:08 PM  
	 *		"hh 'o''clock' a, zzzz"		   | 12 o'clock PM, Pacific Daylight Time  
	 *		"K:mm a, z"					   | 0:08 PM, PDT  
	 *		"yyyyy.MMMMM.dd GGG hh:mm aaa" | 02001.July.04 AD 12:08 PM  
	 *		"EEE, d MMM yyyy HH:mm:ss Z"   | Wed, 4 Jul 2001 12:08:56 -0700  
	 *		"yyMMddHHmmssZ"				   | 010704120856-0700  
	 *		"yyyy-MM-dd'T'HH:mm:ss.SSSZ"   | 2001-07-04T12:08:56.235-0700  
	 *
	 *		Timestamp Format : yyyy-mm-dd hh:mm:ss
	 *		SimpleDateFormat : yyyy-MM-dd HH:mm:ss
	 *		NLS_DATE_Format : yyyy-mm-dd HH24:mi:ss
	 *
	 */

	 public static String getDate(Timestamp ts, String dateFormat){
		if(ts == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		return sdf.format(new Date(ts.getTime()));


	 }//getDate
	public static String getNow(String timeFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);

		return sdf.format(new Date());

	}
	
	public static String getDate(Date date, String timeFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		return sdf.format(date);
		
	}

	
}