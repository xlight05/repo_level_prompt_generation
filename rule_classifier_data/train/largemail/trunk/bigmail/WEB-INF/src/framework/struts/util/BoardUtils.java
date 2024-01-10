package framework.struts.util;

import java.io.UnsupportedEncodingException;

public class BoardUtils {

	/*
	 * \r\nを htmlの <br>に変更
	 */
	public static String convertHtmlBr(String comment) {
		//**********************************************************************
		if (comment == null)
			return "";
		int length = comment.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			String tmp = comment.substring(i, i + 1);
			if ("\r".compareTo(tmp) == 0) {
				tmp = comment.substring(++i, i + 1);
				if ("\n".compareTo(tmp) == 0)
					buffer.append("<br>\r");
				else
					buffer.append("\r");
			}
			buffer.append(tmp);
		}
		return buffer.toString();
	}
	
	/*change encoding : asc --> utf-8*/
	public static String ascToutf(String str) throws UnsupportedEncodingException{
		
		if(str==null || str=="")
			return null;
		return new String(str.getBytes("8859_1"), "UTF-8");
	}
	
	/*change encoding : utf-8 --> ascii */
	public static String utfToasc(String str) throws UnsupportedEncodingException{
		
		if(str==null || str=="")
			return null;
		return new String(str.getBytes("UTF-8"), "8859_1");
	}
	
	
	/* replace '\\.' witch '%2e' in an original file name
	 * @param regex - the regular expression to which this string is to be matched 
	 * @param replacement - the string with strings to replace.
	 * @param subject - the string with strings to search and replace.
	 * @return replaced string
	 */	
	public static String replace(String regex, String replacement, String subject){
		String replaced = "";
		// last position of pattern
		int index = 0;
				
		StringBuffer sf = new StringBuffer();
		
		index = subject.lastIndexOf(".");
		replaced = new String(subject.substring(0, index));
		
		replaced = replaced.replaceAll(regex, replacement);
		
		sf.append(replaced);
		sf.append(subject.subSequence(index, subject.length()));
		
		return sf.toString();
	}
	
}
