package net.caimito.tapestry.sesame;

import java.io.UnsupportedEncodingException;

import net.caimito.tapestry.sesame.providers.AuthenticationProvider;
import net.caimito.tapestry.sesame.util.SimpleXmlSerializer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RememberMe {
	private static Logger logger = LoggerFactory.getLogger(RememberMe.class) ;
	
	private static final String REMEMBER_ME_COOKIE = "net.caimito.tapestry.sesame.rememberMe";
	private Cookies cookies ;
	private AuthenticationProvider authenticationProvider ;
	
	public RememberMe(Cookies cookies, AuthenticationProvider authenticationProvider) {
		this.cookies = cookies ;
		this.authenticationProvider = authenticationProvider ;
	}
	
	public boolean hasRememberMeCookie() {
		return !StringUtils.isEmpty(getRememberMeCookie()) ;
	}

	private String getRememberMeCookie() {
		String value = cookies.readCookieValue(RememberMe.REMEMBER_ME_COOKIE) ;
		if (value == null)
			return null ;
		
		try {
			logger.debug("cookie value found = " + value) ;
			String decodedValue = new String(Base64.decodeBase64(value.getBytes("UTF-8"))) ;
			logger.debug("decoded cookie value = " + decodedValue) ;
			return decodedValue ;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage()) ;
			return null ;
		}
	}
	
	private void setRememberMeCookie(String value) {
		try {
			String encodedValue = new String(Base64.encodeBase64(value.getBytes("UTF-8"))) ;
			logger.debug("Encoded value = " + encodedValue) ;
			cookies.writeCookieValue(REMEMBER_ME_COOKIE, encodedValue) ;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage()) ;
		}
	}

	public void forgetAboutMe() {
	  cookies.removeCookieValue(REMEMBER_ME_COOKIE) ;
	}
	
	public void rememberMe(String userName, String password) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password) ;
		setRememberMeCookie(SimpleXmlSerializer.convertToString(usernamePasswordToken)) ;
	}
	
	public void reauthenticate(Session session) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = SimpleXmlSerializer.convertFromString(UsernamePasswordToken.class, 
				getRememberMeCookie()) ;
		if (usernamePasswordToken == null)
			throw new AuthenticationException("No valid remember-me token") ;
			
		try {
			AuthenticationToken authenticationToken = authenticationProvider.authenticate(usernamePasswordToken.getUserName(), usernamePasswordToken.getPassword()) ;
			session.setAttribute(SecurityChecker.AUTHENTICATION_TOKEN, authenticationToken) ;
			rememberMe(usernamePasswordToken.getUserName(), usernamePasswordToken.getPassword()) ;
		} catch (AuthenticationException e) {
			throw new AuthenticationException("Reauthentication based on remember-me cookie failed", e) ;
		}
	}

}
