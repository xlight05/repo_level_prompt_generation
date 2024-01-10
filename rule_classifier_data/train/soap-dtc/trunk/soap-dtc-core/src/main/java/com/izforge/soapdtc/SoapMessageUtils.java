/*
 * Copyright (c) 2006,2007 Julien Ponge. All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.izforge.soapdtc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.XPP3Reader;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for SOAP messages.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public class SoapMessageUtils
{
    /**
     * Registers our namespaces URIs and prefixes for proper XPath queries evaluation.
     *
     * @param documentFactory the Dom4J factory to use.
     */
    public static void configureDocumentFactoryXPathNamespaceURIs(DocumentFactory documentFactory)
    {
        Map uris = loadOrCreateMap(documentFactory);
        uris.put(SoapConstants.SOAP12_ENV_PREFIX, SoapConstants.SOAP12_ENV_NS);
        uris.put(SoapConstants.XML_PREFIX, SoapConstants.XML_NS);
    }

    /**
     * Registers a namespace URI and prefix in a Dom4J factory for proper XPath queries evaluation.
     *
     * @param documentFactory the Dom4J factory to use.
     * @param prefix the XML prefix.
     * @param namespace the XML namespace.
     */
    public static void registerDocumentFactoryXPathNamespaceURIs(DocumentFactory documentFactory, String prefix, String namespace)
    {
        Map uris = loadOrCreateMap(documentFactory);
        uris.put(prefix, namespace);
    }

    /**
     * Loads a namespaces map, or create one if needed.
     *
     * @param documentFactory the Dom4J factory.
     * @return the namespaces map.
     */
    private static Map loadOrCreateMap(DocumentFactory documentFactory)
    {
        Map uris = documentFactory.getXPathNamespaceURIs();
        if (uris == null)
        {
            uris = new HashMap();
            documentFactory.setXPathNamespaceURIs(uris);
        }
        return uris;
    }

    /**
     * Reads a SOAP message from an input stream.
     *
     * @param in              the input stream.
     * @param documentFactory the Dom4J factory.
     * @return a SOAP message.
     * @throws DocumentException      if there is a problem with the document.
     * @throws IOException            if there is an I/O error.
     * @throws XmlPullParserException if the parser encounters a problem.
     */
    public static SoapMessage readSoapMessageFromStream(InputStream in, DocumentFactory documentFactory) throws DocumentException, IOException, XmlPullParserException
    {
        configureDocumentFactoryXPathNamespaceURIs(documentFactory);
        XPP3Reader reader = new XPP3Reader(documentFactory);
        Document envelope = reader.read(in);
        in.close();
        return new SoapMessage(documentFactory, envelope);
    }

    /**
     * Reads a SOAP message from an input stream.
     *
     * @param in the input stream.
     * @return a SOAP message.
     * @throws DocumentException      if there is a problem with the document.
     * @throws IOException            if there is an I/O error.
     * @throws XmlPullParserException if the parser encounters a problem.
     */
    public static SoapMessage readSoapMessageFromStream(InputStream in) throws DocumentException, IOException, XmlPullParserException
    {
        return readSoapMessageFromStream(in, DocumentFactory.getInstance());
    }
}
