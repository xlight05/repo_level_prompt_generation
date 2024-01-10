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

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

/**
 * The interface for SOAP Messages.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public class SoapMessage
{
    /**
     * The Dom4J elements factory.
     */
    private DocumentFactory documentFactory;

    /**
     * The actual SOAP message Dom4J document.
     */
    private Document soapDocument;

    /* .......................................................................................... */

    public SoapMessage()
    {
        documentFactory = DocumentFactory.getInstance();
        SoapMessageUtils.configureDocumentFactoryXPathNamespaceURIs(documentFactory);
        soapDocument = createSoapEnvelope();
    }

    public SoapMessage(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
        SoapMessageUtils.configureDocumentFactoryXPathNamespaceURIs(documentFactory);
        soapDocument = createSoapEnvelope();
    }

    public SoapMessage(Document soapDocument)
    {
        documentFactory = DocumentFactory.getInstance();
        SoapMessageUtils.configureDocumentFactoryXPathNamespaceURIs(documentFactory);
        this.soapDocument = soapDocument;
    }

    public SoapMessage(DocumentFactory documentFactory, Document soapDocument)
    {
        this.documentFactory = documentFactory;
        SoapMessageUtils.configureDocumentFactoryXPathNamespaceURIs(documentFactory);
        this.soapDocument = soapDocument;
    }

    /* .......................................................................................... */

    /**
     * Provides access to the SOAP Header element.
     *
     * @return the SOAP header element.
     */
    public Element getHeaderElement()
    {
        return (Element) soapDocument.selectSingleNode("//env:Header");
    }

    /**
     * Provides access to the SOAP body element.
     *
     * @return the SOAP body element.
     */
    public Element getBodyElement()
    {
        return (Element) soapDocument.selectSingleNode("//env:Body");
    }

    /**
     * Specifies wether the SOAP body contains a SOAP fault or not.
     *
     * @return <code>true</code> if there is a SOAP fault, <code>false</code> otherwise.
     */
    public boolean containsFault()
    {
        return soapDocument.selectSingleNode("//env:Fault") != null;
    }

    /**
     * Provides access to the SOAP fault element, if any.
     *
     * @return the SOAP fault element, or <code>null</code> if the SOAP body does not contain a SOAP fault.
     */
    public Element getFaultElement()
    {
        return (Element) soapDocument.selectSingleNode("//env:Fault");
    }

    /**
     * Provides access to the SOAP message document.
     *
     * @return the SOAP message document.
     */
    public Document getSoapDocument()
    {
        return soapDocument;
    }

    /**
     * Provides access to the Dom4J factory used for this message.
     *
     * @return the Dom4J factory.
     */
    public DocumentFactory getDocumentFactory()
    {
        return documentFactory;
    }

    /* .......................................................................................... */

    /**
     * Adds an element to the SOAP header, taking care of specifying wether it must be understood by the next node or not.
     *
     * @param element        the element to add.
     * @param mustUnderstand wether the header must be understood by the next node or not.
     */
    public void addHeaderElement(Element element, boolean mustUnderstand)
    {
        String value = mustUnderstand ? "1" : "0";
        Attribute mustUnderstandAttribute = documentFactory.createAttribute(element, "env:mustUnderstand", value);
        element.add(mustUnderstandAttribute);
        getHeaderElement().add(element);
    }

    /**
     * Adds an element to the SOAP header, but does not take care of specifying wether it needs to be understood by the next node or not.
     *
     * @param element the element to add.
     */
    public void addHeaderElement(Element element)
    {
        getHeaderElement().add(element);
    }

    /**
     * Adds an element to the SOAP body.
     *
     * @param element the element to add.
     */
    public void addBodyElement(Element element)
    {
        getBodyElement().add(element);
    }

    /* .......................................................................................... */

    /**
     * Creates a SOAP envelope for an empty SOAP message.
     *
     * @return a new SOAP message envelope.
     */
    private Document createSoapEnvelope()
    {
        soapDocument = documentFactory.createDocument();

        Element rootElement = documentFactory.createElement("env:Envelope", SoapConstants.SOAP12_ENV_NS);
        soapDocument.setRootElement(rootElement);

        rootElement.addNamespace(SoapConstants.SOAP12_ENV_PREFIX, SoapConstants.SOAP12_ENV_NS);
        rootElement.addNamespace(SoapConstants.XML_PREFIX, SoapConstants.XML_NS);
        rootElement.add(documentFactory.createElement("env:Header", SoapConstants.SOAP12_ENV_NS));
        rootElement.add(documentFactory.createElement("env:Body", SoapConstants.SOAP12_ENV_NS));

        return soapDocument;
    }
}
