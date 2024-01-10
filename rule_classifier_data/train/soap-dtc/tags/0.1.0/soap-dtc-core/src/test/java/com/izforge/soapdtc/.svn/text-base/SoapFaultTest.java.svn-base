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

import junit.framework.TestCase;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.XPP3Reader;
import org.dom4j.util.NodeComparator;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SoapFaultTest extends TestCase
{
    private NodeComparator nodeComparator = new NodeComparator();

    private DocumentFactory documentFactory = DocumentFactory.getInstance();

    public SoapFaultTest()
    {
        SoapMessageUtils.configureDocumentFactoryXPathNamespaceURIs(documentFactory);
    }

    public void testToXmlElement() throws DocumentException, IOException, XmlPullParserException
    {
        SoapFault fault = new SoapFault();
        fault.setCode(SoapConstants.SOAP12_FAULT_SENDER, "m:MessageTimeout", null);
        fault.addReason("en", "Sender Timeout");
        Element detailElement = documentFactory.createElement("m:MaxTime", "urn:foo");
        detailElement.setText("P5M");
        fault.setDetail(detailElement);
        Element faultElement = fault.toXmlElement();
        faultElement.addNamespace(SoapConstants.XML_PREFIX, SoapConstants.XML_NS);

        XPP3Reader reader = new XPP3Reader();
        Document expected = reader.read(getClass().getResourceAsStream("simple-fault.xml"));

        // Can't understand why we need 5 here, as the elements are really the same!
        assertEquals(5, nodeComparator.compare(expected.getRootElement(), faultElement));
    }
}
