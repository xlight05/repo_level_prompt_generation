package net.caimito.tapestry.sesame;

import org.apache.tapestry5.services.RequestGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityChecker {
	protected static final String AUTHENTICATION_TOKEN = "SnsSecurityAuthenticationToken" ;
	
	private static Logger logger = LoggerFactory.getLogger(SecurityChecker.class) ;
	
	private RequestGlobals requestGlobals ;

	public SecurityChecker(RequestGlobals requestGlobals) {
		this.requestGlobals = requestGlobals ;
	}
	
	public boolean checkAccess() {
		logger.info("Checking access") ;
		AuthenticationToken token = (AuthenticationToken) requestGlobals.getRequest().getSession(true).getAttribute(AUTHENTICATION_TOKEN) ;
		if (token != null) {
			logger.info("Authenticated") ;
			return true ;
		} else {
			logger.info("NOT Authenticated") ;
			return false ;
		}
	}
	
	public AuthenticationToken getCurrentAuthenticationToken() {
	  if (requestGlobals.getRequest().getSession(false) != null)
	    return (AuthenticationToken) requestGlobals.getRequest().getSession(false).getAttribute(AUTHENTICATION_TOKEN) ;
	  else
	    return null ;
	}
}
