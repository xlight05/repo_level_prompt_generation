package br.com.dyad.infrastructure.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String encryptValue( String value ){
		try {
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			m.update(value.getBytes("UTF8"));
		    byte s[] = m.digest();
		    String encryptValue = "";
		    
		    for (int i = 0; i < s.length; i++) {
		    	encryptValue += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
		    }
		    return encryptValue;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
