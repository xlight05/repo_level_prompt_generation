package com.itzg.quidsee;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.cyberneko.html.parsers.DOMParser;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpResponseFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class QuiBidsAuctionPageResponseFilter implements HttpResponseFilter {

	private final AuctionTracker tracker;
	
	private XPathFactory xpathFactory = XPathFactory.newInstance();
	
	private static final Pattern backgroundImgStyle = Pattern.compile("background-image:url\\((.+)\\)");
	private static final Pattern auctionIdChunk = Pattern.compile("#(\\d+)");

	public QuiBidsAuctionPageResponseFilter(AuctionTracker tracker) {
		this.tracker = tracker;
	}

	public HttpResponse filterResponse(HttpResponse response, String requestUri) {
		ChannelBufferInputStream in = new ChannelBufferInputStream(response.getContent());
		DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(in));
			Document doc = parser.getDocument();
			XPath xpath = xpathFactory.newXPath();
			xpath.setNamespaceContext(new NamespaceContext() {
				
				public Iterator getPrefixes(String namespaceURI) {
					return Collections.singletonList(getPrefix(namespaceURI)).iterator();
				}
				
				public String getPrefix(String namespaceURI) {
					return "http://www.w3.org/1999/xhtml".equals(namespaceURI) ? "h" : null;
				}
				
				public String getNamespaceURI(String prefix) {
					if ("h".equals(prefix)) {
						return "http://www.w3.org/1999/xhtml";
					}
					return XMLConstants.NULL_NS_URI;
				}
			});
			Node auctionIdDiv = (Node) xpath.evaluate("/h:HTML/h:BODY//h:DIV[@id='auction-id']", 
					doc, XPathConstants.NODE);
			String title = (String) xpath.evaluate("//h:DIV[@id='auction-id-descr']/h:H1", 
					auctionIdDiv, XPathConstants.STRING);
			String imageStyleDefn = (String)xpath.evaluate("//h:DIV[@id='prodimg']/@style", auctionIdDiv, XPathConstants.STRING);
			Matcher matcher = backgroundImgStyle.matcher(imageStyleDefn);
			String productImageUrl = null;
			if (matcher.find()) {
				productImageUrl = matcher.group(1);
			}
			
			String auctionIdSpan = (String) xpath.evaluate("//h:SPAN[@id='auction-id-ttl-span']", 
					auctionIdDiv, XPathConstants.STRING);
			Matcher auctionIdMatcher = auctionIdChunk.matcher(auctionIdSpan);
			int auctionId = 0;
			if (auctionIdMatcher.find()) {
				auctionId = Integer.parseInt(auctionIdMatcher.group(1));
			}
			
			tracker.handleAuctionInfo(auctionId, title, productImageUrl);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {}
			response.getContent().resetReaderIndex();
		}
		
		return response;
	}

}
