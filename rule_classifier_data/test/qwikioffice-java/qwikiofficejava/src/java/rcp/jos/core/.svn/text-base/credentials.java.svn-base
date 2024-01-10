/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rcp.jos.core;

import java.security.*;
import java.math.*;

/**
 *
 * @author rcrespo
 */
public class credentials {

    public static String md5_encrypt(String pass) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		byte[] data = pass.getBytes();
		m.update(data,0,data.length);
		BigInteger i = new BigInteger(1,m.digest());
		return String.format("%1$032x", i);
	}

}
