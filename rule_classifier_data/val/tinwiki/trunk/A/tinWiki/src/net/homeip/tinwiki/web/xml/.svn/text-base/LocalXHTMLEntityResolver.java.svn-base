package net.homeip.tinwiki.web.xml;

import java.io.IOException;

import org.xml.sax.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalXHTMLEntityResolver implements EntityResolver {

	Map<String,String> myMap = new ConcurrentHashMap<String,String>();
	
	// fill the list of URLs
	public LocalXHTMLEntityResolver() {

		// The XHTML 1.0 DTDs
		myMap.put("-//W3C//DTD XHTML 1.0 Strict//EN", "http://pham.com/DTD/xhtml1-strict.dtd");
		myMap.put("-//W3C//DTD XHTML 1.0 Transitional//EN", "http://pham.com/DTD/xhtml1-transitional_with_lat1.dtd");
		myMap.put("-//W3C//DTD XHTML 1.0 Frameset//EN", "http://pham.com/DTD/xhtml1-frameset.dtd");

		// The XHTML 1.0 entity sets
		myMap.put("-//W3C//ENTITIES Latin 1 for XHTML//EN", "http://pham.com/DTD/xhtml-lat1.ent");
		myMap.put("-//W3C//ENTITIES Symbols for XHTML//EN", "http://pham.com/DTD/xhtml-symbol.ent");
		myMap.put("-//W3C//ENTITIES Special for XHTML//EN", "http://pham.com/DTD/xhtml-special.ent");

	}

	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		
		System.out.println("publicId=" + publicId);
		System.out.println("systemId=" + systemId);
		
		InputSource inputSource = null;

		if (myMap.containsKey(publicId)) {
			String url = (String) myMap.get(publicId);
			System.out.println("changed to local systemId=" + url);
		}
		
		return inputSource;
		
	}

}
