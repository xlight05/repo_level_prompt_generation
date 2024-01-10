package org.jjsc.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class for working with {@link String}s.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public final class StringUtils {
	private StringUtils(){}
	/**
	 * Replaces all occurrences of search in target with replacement.
	 * This method doesn't uses {@link Pattern} for replacement and
	 * thats why have no troubles with pattern syntax. 
	 * But on the other side this method is not so flexible as default
	 * {@link String#replaceAll(String, String)}.
	 * @param target string to search
	 * @param search string that will be replaced
	 * @param replacement to be used
	 * @return new string with replaced search by replacement.
	 * @throws NullPointerException in case if replacement is <code>null</code>.
	 */
	public static String replaceAll(String target,String search,String replacement){
		if(replacement==null){
			throw new NullPointerException();
		}
		if(target==null||search==null||search.length()==0){
			return target;
		}
		StringBuilder result = new StringBuilder(target.length());
		int last = 0;
		int index = target.indexOf(search);
		while(index>=0){
			result.append(target, last, index);
			result.append(replacement);
			last = index+search.length();
			index = target.indexOf(search,last);
		}
		result.append(target, last, target.length());
		return result.toString();
	}
	/**
	 * Creates string using array of characters.
	 * @param chars
	 * @return new string object
	 */
	public static String create(char...chars) {
		return new String(chars);
	}
	/**
	 * Splits target with delimiter and returns the result as array of pieces.
	 * Doesn't use the regular expressions for this operation.
	 * @param target
	 * @param delimiter
	 * @return array of pieces (never <code>null</code>).
	 */
	public static String[] split(String target,String delimiter){
		if(delimiter==null||target==null){
			throw new NullPointerException();
		}
		int i = target.indexOf(delimiter);
		if(i<0){
			return new String[]{target};
		}
		List<String> result = new LinkedList<String>();
		final int LEN = delimiter.length();
		if(LEN==0){
			char[] tmp = target.toCharArray();
			for(;i<tmp.length;i++){
				result.add(create(tmp[i]));
			}
		}
		else {
			int start = 0;
			while(i>=0){
				result.add(target.substring(start,i));
				start = i+LEN;
				i = target.indexOf(delimiter,start);
			}
			result.add(target.substring(start,target.length()));
		}
		return result.toArray(new String[result.size()]);
	}
	/**
	 * @param string to test
	 * @return <code>true</code> if string passed is <code>null</code> or 
	 * empty string or contains white spaces only.
	 */
	public static boolean isEmpty(String str) {
		return isEmpty(str,true);
	}
	/**
	 * @param string to test
	 * @param trim 
	 * @return <code>true</code> if string passed is <code>null</code> or 
	 * empty string or contains white spaces only (optionally).
	 */
	public static boolean isEmpty(String str, boolean trim) {
		if(str==null||str.length()==0){
			return true;
		}
		if(!trim)return false;
		final int len = str.length();
		for(int i = 0;i < len;i++) {
		    if(str.charAt(i) > ' ')return false;
		}
		return true;
	}
	/**
	 * Escapes the passed string for the standard java escape sequences.
	 * @param str
	 * @return escaped string.
	 */
	public static String escape(String str){
		if(str==null||str.length()==0){
			return str;
		}
		char[] rez = new char[str.length()*2];
		char[] src = str.toCharArray();
		int j = 0;
		for(int i = 0;i<src.length;i++){
			switch(src[i]){
			case '\b':
				rez[j++] = '\\';
				rez[j++] = 'b';
				continue;
			case '\n':
				rez[j++] = '\\';
				rez[j++] = 'n';
				continue;
			case '\r':
				rez[j++] = '\\';
				rez[j++] = 'r';
				continue;
			case '\f':
				rez[j++] = '\\';
				rez[j++] = 'f';
				continue;
			case '\t':
				rez[j++] = '\\';
				rez[j++] = 't';
				continue;
			case '"':
			case '\'':
			case '\\':
				rez[j++] = '\\';
			}
			rez[j++] = src[i];
		}
		return new String(rez,0,j);
	}
	/**
	 * Join the array of objects in the single string with some glue string.
	 * @param array
	 * @param glue
	 * @return joined string
	 */
	public static String join(Object[] array, String glue) {
		StringBuilder result = new StringBuilder();
		for(int i=0;i<array.length;i++){
			if(i>0)result.append(glue);
			result.append(array[i]);
		}
		return result.toString();
	}
	/**
	 * Removes all the white spaces (space, tab, new line, etc) from passed string.
	 * 
	 * @param string to cut white spaces
	 * @return new string without white spaces.
	 */
	public static String cutWhiteSpaces(String str) {
		if(str==null||str.length()==0){
			return str;
		}
		char[] rez = new char[str.length()];
		char[] src = str.toCharArray();
		int j = 0;
		for(int i = 0;i<src.length;i++){
			switch(src[i]){
			case ' ':
			case '\t':
			case '\n':
			case 0x0B:
			case '\f':
			case '\r':
				continue;
			default:
				rez[j++] = src[i];
			}
		}
		return new String(rez,0,j);
	}
}
