package org.seamlets.identity.tools;

import org.jboss.seam.security.management.PasswordHash;


public class PasswordCreator {

	private PasswordHash passwordHash = new PasswordHash();
	
	public String generateSaltedHash(String password, String saltPhrase, String algorithm) {
		return passwordHash.generateSaltedHash(password, saltPhrase, algorithm);
	}
	
	public static void main(String[] args) {
		System.out.println(new PasswordCreator().generateSaltedHash("n0vember", "admin", PasswordHash.ALGORITHM_SHA));
	}

}
