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

package com.izforge.soapdtc.transport;

import com.izforge.soapdtc.SoapMessage;
import com.izforge.soapdtc.SoapMessageTest;
import com.izforge.soapdtc.SoapMessageUtils;
import org.dom4j.DocumentException;
import org.dom4j.util.NodeComparator;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class HttpSoapTransportWithDummyServletTest extends AbstractHttpSoapTransportTest
{
    private NodeComparator nodeComparator = new NodeComparator();

    public HttpSoapTransportWithDummyServletTest()
    {
        super(new DummyServlet());
    }

    public void testSendReceive() throws DocumentException, IOException, XmlPullParserException, SoapTransportException
    {
        HttpSoapTransport soapTransport = new HttpSoapTransport();
        SoapMessage message = SoapMessageUtils.readSoapMessageFromStream(SoapMessageTest.class
                .getResourceAsStream("simple-soap-message.xml"));

        SoapMessage response = soapTransport.sendReceive("http://localhost:8085/dtc", message, null);
        assertEquals(0, nodeComparator.compare(message, response));
    }

    public void testAsyncSendReceive() throws DocumentException, IOException, XmlPullParserException, SoapTransportException, InterruptedException
    {
        HttpSoapTransport soapTransport = new HttpSoapTransport();
        final SoapMessage message = SoapMessageUtils.readSoapMessageFromStream(SoapMessageTest.class
                .getResourceAsStream("simple-soap-message.xml"));

        soapTransport.asyncSendReceive("http://localhost:8085/dtc", message, null, new IAsyncSoapMessageHandler()
        {

            public void handleAsyncResponse(SoapMessage response)
            {
                assertEquals(0, nodeComparator.compare(message, response));
            }

            public void handleAsyncTransportException(SoapTransportException exception)
            {
                fail(exception.toString());
            }
        });
        Thread.sleep(500);
    }

    public void testReceive() throws DocumentException, IOException, XmlPullParserException, SoapTransportException
    {
        HttpSoapTransport soapTransport = new HttpSoapTransport();
        SoapMessage expected = SoapMessageUtils.readSoapMessageFromStream(SoapMessageTest.class
                .getResourceAsStream("simple-soap-message.xml"));

        SoapMessage response = soapTransport.receive("http://localhost:8085/dtc", null);
        assertEquals(0, nodeComparator.compare(expected, response));
    }

    public void testAsyncReceive() throws DocumentException, IOException, XmlPullParserException, SoapTransportException, InterruptedException
    {
        HttpSoapTransport soapTransport = new HttpSoapTransport();
        final SoapMessage expected = SoapMessageUtils.readSoapMessageFromStream(SoapMessageTest.class
                .getResourceAsStream("simple-soap-message.xml"));

        soapTransport.asyncReceive("http://localhost:8085/dtc", null, new IAsyncSoapMessageHandler()
        {

            public void handleAsyncResponse(SoapMessage response)
            {
                assertEquals(0, nodeComparator.compare(expected, response));
            }

            public void handleAsyncTransportException(SoapTransportException exception)
            {
                fail(exception.toString());
            }
        });
        Thread.sleep(500);
    }
}
