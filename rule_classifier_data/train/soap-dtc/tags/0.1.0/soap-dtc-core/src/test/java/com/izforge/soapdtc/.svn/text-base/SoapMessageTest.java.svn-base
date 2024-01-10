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
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.util.NodeComparator;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SoapMessageTest extends TestCase
{
    private NodeComparator nodeComparator = new NodeComparator();

    private DocumentFactory documentFactory = DocumentFactory.getInstance();

    public void testCreateEnvelope() throws DocumentException, IOException, XmlPullParserException
    {
        SoapMessage message = new SoapMessage();
        SoapMessage expectedMessage = SoapMessageUtils.readSoapMessageFromStream(getClass()
                .getResourceAsStream("empty-soap-message.xml"), documentFactory);

        assertEquals(0, nodeComparator.compare(expectedMessage, message));
    }

    public void testEnvelopeContent() throws DocumentException, IOException, XmlPullParserException
    {
        SoapMessageUtils.registerDocumentFactoryXPathNamespaceURIs(documentFactory, "t", "urn:mytransaction");
        SoapMessageUtils.registerDocumentFactoryXPathNamespaceURIs(documentFactory, "s", "urn:sales");
        SoapMessageUtils.registerDocumentFactoryXPathNamespaceURIs(documentFactory, "h", "urn:plop:da:plop");
        SoapMessage message = SoapMessageUtils.readSoapMessageFromStream(getClass().
                getResourceAsStream("simple-soap-message.xml"), documentFactory);

        assertFalse(message.containsFault());
        assertNull(message.getFaultElement());

        assertEquals("123456789", message.getHeaderElement().valueOf("t:transaction/t:id/text()"));
        assertEquals("Banana split", message.getBodyElement().valueOf("s:sales/s:product"));
        assertEquals("1", message.getHeaderElement().valueOf("h:plop/@env:mustUnderstand"));

        message = SoapMessageUtils.readSoapMessageFromStream(getClass().
                getResourceAsStream("message-with-fault.xml"), documentFactory);
        assertTrue(message.containsFault());
    }
}
