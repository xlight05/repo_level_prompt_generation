package se.openprocesslogger.proxy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransformUtilities {

	public static Date buildDate(String date, String time) throws ParseException{
		if(date == null || date == "") date = "1970-01-01";
		if(time == null || time == "") time = "00:00";
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd mm:ss");
		return df1.parse(date+" "+time);
	}
	public static String dateToDojoTimeString(Date d){
		DateFormat df1 = new SimpleDateFormat("HH:mm");
		return df1.format(d);
	}
	public static String dateToDojoDateString(Date d){
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		return df1.format(d);
	}
	
	/***
	 * 
	 * @param date yyyy-MM-dd 
	 * @param time 'T'HH:mm:ss
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDojoDateTime(String date, String time) throws ParseException{
		System.out.println("Parsing: ."+date+"." +"."+time+".");
		if(date == null || date == "") date = "1970-01-01";
		if(time == null || time == "") time = "T00:00:00";
		return parseDojoDateTime(date +" "+time);
	}
	/***
	 * 
	 * @param dateTime yyyy-MM-dd 'T'HH:mm:ss
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDojoDateTime(String dateTime) throws ParseException{
		if(dateTime==null || dateTime == "") dateTime = "1970-01-01 T00:00:00";
		
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd 'T'HH:mm:ss");
		try {
			return df1.parse(dateTime);
		} catch (ParseException e) {
			//e.printStackTrace();
			throw(e);
		}
	}
	
	public static void main(String[] args) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		System.out.println(df.format(df.parse("13:45")));
	}
}

