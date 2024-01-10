package net.caimito.tapestry.sesame.providers;

import net.caimito.tapestry.sesame.AuthenticationException;
import net.caimito.tapestry.sesame.AuthenticationToken;
import net.caimito.tapestry.sesame.UsernamePasswordToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeDatabaseAuthenticationProvider implements AuthenticationProvider {
	private static Logger logger = LoggerFactory.getLogger(FakeDatabaseAuthenticationProvider.class) ;
	
	public AuthenticationToken authenticate(String username, String password) throws AuthenticationException {
		// TODO implement real database lookup
		logger.info("Simulating successful database lookup. username=" + username + " password=" + password) ;
		
		return new UsernamePasswordToken(username, password) ;
	}

}
