package com.sun.facelets.compiler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerConfigurationException;

import org.jboss.seam.Component;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.seamlets.exceptions.ViewIdNotManagedBySeamletsException;
import org.seamlets.page.SeamletsPage;
import org.seamlets.xslt.DocumentTransformer;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.facelets.FaceletException;
import com.sun.facelets.FaceletHandler;
import com.sun.facelets.tag.Location;
import com.sun.facelets.tag.Tag;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributes;
import com.sun.facelets.tag.ui.UILibrary;

public class XSLTCompiler extends Compiler {

	private final static Log		logger				= Logging.getLog(XSLTCompiler.class);

	private final static Pattern	XmlDeclaration	= Pattern
															.compile("^<\\?xml.+?version=['\"](.+?)['\"](.+?encoding=['\"]((.+?))['\"])?.*?\\?>");

	private boolean					initialized		= false;
	
	private final DocumentTransformer documentTransformer;

	public class CompilationHandler extends DefaultHandler implements LexicalHandler {

		private final String				alias;

		private boolean						inDocument	= false;

		private Locator						locator;

		private final NamespaceManager		namespaceManager;

		private final CompilationManager	unit;
		
		public CompilationHandler(CompilationManager unit, String alias) {
			this.unit = unit;
			this.alias = alias;
			this.namespaceManager = new NamespaceManager();
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (this.inDocument) {
				this.unit.writeText(new String(ch, start, length));
			}
		}

		public void comment(char[] ch, int start, int length) throws SAXException {
			if (this.inDocument) {
				this.unit.writeComment(new String(ch, start, length));
			}
		}

		protected TagAttributes createAttributes(Attributes attrs) {
			int len = attrs.getLength();
			TagAttribute[] ta = new TagAttribute[len];
			for (int i = 0; i < len; i++) {
				ta[i] = new TagAttribute(this.createLocation(), attrs.getURI(i), attrs.getLocalName(i), attrs
						.getQName(i), attrs.getValue(i));
			}
			return new TagAttributes(ta);
		}

		protected Location createLocation() {
			return new Location(this.alias, 1, 1);
		}

		public void endCDATA() throws SAXException {
			if (this.inDocument) {
				this.unit.writeInstruction("]]>");
			}
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
		}

		public void endDTD() throws SAXException {
			this.inDocument = true;
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			this.unit.popTag();
		}

		public void endEntity(String name) throws SAXException {
		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
			this.unit.popNamespace(prefix);
			this.namespaceManager.popNamespace(prefix);
		}

		@Override
		public void fatalError(SAXParseException e) throws SAXException {
			if (this.locator != null) {
				throw new SAXException("Error Traced[line: " + this.locator.getLineNumber() + "] " + e.getMessage());
			}
			throw e;
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
			if (this.inDocument) {
				this.unit.writeWhitespace(new String(ch, start, length));
			}
		}

		@Override
		public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
			String dtd = "default.dtd";
			URL url = Thread.currentThread().getContextClassLoader().getResource(dtd);
			return new InputSource(url.toString());
		}

		@Override
		public void setDocumentLocator(Locator locator) {
			this.locator = locator;
		}

		public void startCDATA() throws SAXException {
			if (this.inDocument) {
				this.unit.writeInstruction("<![CDATA[");
			}
		}

		@Override
		public void startDocument() throws SAXException {
			this.inDocument = true;
		}

		public void startDTD(String name, String publicId, String systemId) throws SAXException {
			if (this.inDocument) {
				StringBuffer sb = new StringBuffer(64);
				sb.append("<!DOCTYPE ").append(name);
				if (publicId != null) {
					sb.append(" PUBLIC \"").append(publicId).append("\"");
					if (systemId != null) {
						sb.append(" \"").append(systemId).append("\"");
					}
				} else if (systemId != null) {
					sb.append(" SYSTEM \"").append(systemId).append("\"");
				}
				sb.append(" >\n");
				this.unit.writeInstruction(sb.toString());
			}
			this.inDocument = false;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (localName.contains(":")) {
				String[] prefix = localName.split(":");
				localName = prefix[1];
				uri = this.namespaceManager.getNamespace(prefix[0]);
			}
			if (uri == "") {
				uri = this.namespaceManager.getNamespace("");
			}
			this.unit.pushTag(new Tag(this.createLocation(), uri, localName, qName, this.createAttributes(attributes)));
		}

		public void startEntity(String name) throws SAXException {
		}

		@Override
		public void startPrefixMapping(String prefix, String uri) throws SAXException {
			this.namespaceManager.pushNamespace(prefix, uri);
			this.unit.pushNamespace(prefix, uri);
		}

		@Override
		public void processingInstruction(String target, String data) throws SAXException {
			if (this.inDocument) {
				StringBuffer sb = new StringBuffer(64);
				sb.append("<?").append(target).append(' ').append(data).append("?>\n");
				this.unit.writeInstruction(sb.toString());
			}
		}
	}

	public XSLTCompiler() {
		super();
		this.documentTransformer = (DocumentTransformer) Component.getInstance(DocumentTransformer.class);
	}

	public synchronized void initialize() {
		if (this.initialized)
			return;
		if (logger.isDebugEnabled())
			logger.debug("Initializing");
		try {
			TagLibraryConfig cfg = new TagLibraryConfig();
			cfg.loadImplicit(this);

			if (!this.createTagLibrary().containsNamespace(UILibrary.Namespace)) {
				logger
						.error("Missing Built-in Tag Libraries! Make sure they are included within the META-INF directory of Facelets' Jar");
			}

		} catch (IOException e) {
			logger.error("Compiler Initialization Error", e);
		} finally {
			this.initialized = true;
		}
		if (logger.isDebugEnabled())
			logger.debug("Initialization Successful");
	}

	@Override
	public FaceletHandler doCompile(URL src, String alias) throws IOException, FaceletException, ELException,
			FacesException {
		if (!this.initialized)
			this.initialize();
		CompilationManager mngr = null;
		InputStream is = null;
		String encoding = "UTF-8";
		try {
			is = new BufferedInputStream(src.openStream(), 1024);
			mngr = new CompilationManager(alias, this);
			encoding = writeXmlDecl(is, mngr);
			CompilationHandler handler = new CompilationHandler(mngr, alias);
			SAXParser parser = this.createSAXParser(handler);
			parser.parse(is, handler);
		} catch (SAXException e) {
			throw new FaceletException("Error Parsing " + alias + ": " + e.getMessage(), e.getCause());
		} catch (ParserConfigurationException e) {
			throw new FaceletException("Error Configuring Parser " + alias + ": " + e.getMessage(), e.getCause());
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return new EncodingHandler(mngr.createFaceletHandler(), encoding);
	}

	public FaceletHandler doXsltCompile(String alias, SeamletsPage seamletsPage) throws FaceletException, ELException,
			FacesException, ViewIdNotManagedBySeamletsException {
		if (!this.initialized)
			this.initialize();
		CompilationManager mngr = null;
		String encoding = "UTF-8";
		try {
			mngr = new CompilationManager(alias, this);
			CompilationHandler handler = new CompilationHandler(mngr, alias);
			documentTransformer.getTransformedCmsContent(seamletsPage, handler);
		} catch (TransformerConfigurationException e) {
			throw new FaceletException("Error Configuring Transformer " + alias + ": " + e.getMessage(), e.getCause());
		} catch (SAXException e) {
			throw new FaceletException("Error Parsing " + alias + ": " + e.getMessage(), e.getCause());
		}
		return new EncodingHandler(mngr.createFaceletHandler(), encoding);
	}

	protected static final String writeXmlDecl(InputStream is, CompilationManager mngr) throws IOException {
		is.mark(128);
		String encoding = "UTF-8";
		try {
			byte[] b = new byte[128];
			if (is.read(b) > 0) {
				String r = new String(b);
				Matcher m = XmlDeclaration.matcher(r);
				if (m.find()) {
					mngr.writeInstruction(m.group(0) + "\n");
					if (m.group(3) != null) {
						encoding = m.group(3);
					}
				}
			}
		} finally {
			is.reset();
		}
		return encoding;
	}

	private final SAXParser createSAXParser(CompilationHandler handler) throws SAXException,
			ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		factory.setFeature("http://xml.org/sax/features/validation", this.isValidating());
		factory.setValidating(this.isValidating());
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
		reader.setErrorHandler(handler);
		reader.setEntityResolver(handler);
		return parser;
	}

}
