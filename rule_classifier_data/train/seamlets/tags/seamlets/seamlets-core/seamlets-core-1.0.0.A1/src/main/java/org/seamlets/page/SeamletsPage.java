package org.seamlets.page;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Bean that holds basic meta information about a Seamlet Pages
 * 
 * it is abstract so Seamlet vendors must implement it; they may feel free to 
 * use constructors or setters for setting the values, add private fields to
 * represent references to make the getSAXContent possible, etc.
 * 
 * Seamlets Provider must decide everything:
 *  - http redirects to other pages
 *  - http error codes
 * 
 * @author dzwicker
 *
 */
public abstract class SeamletsPage {
	protected String viewID;
	protected Integer httpErrorCode;
	protected String redirectURL;
	protected String templateKey;
	
	
	
	public SeamletsPage(String templateKey) {
		this.templateKey = templateKey;
	}

	/**
	 * Diese Methode gibt den kompletten Inhalt des referenzierten Dokuments
	 * in Form von SAX-Events and den übergebenen ContentHandler weiter.
	 * 
	 * @param ch ContentHandler, in den die SAX Events geschrieben werden.
	 * @throws SAXException 
	 */
	public abstract void getSAXContent(ContentHandler ch) throws SAXException;
	
	/**
	 * @return true if an error should be thrown
	 */
	public boolean isError() {
		return (httpErrorCode!=0);
	}
	
	/**
	 * @return true if a redirect should be sent
	 */
	public boolean isRedirect() {
		return (redirectURL!=null);
	}
	
	/* ***************** Getters for this package ******************** */
	
	/**
	 * @return the viewID
	 */
	public String getViewID() {
		return viewID;
	}

	/**
	 * @return the httpErrorCode
	 */
	public Integer getHttpErrorCode() {
		return httpErrorCode;
	}

	/**
	 * @return the redirectURL
	 */
	public String getRedirectURL() {
		return redirectURL;
	}

	public String getTemplateKey() {
		return templateKey;
	}
	
}
