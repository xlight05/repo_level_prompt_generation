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

package com.izforge.soapdtc.servlet;

import com.izforge.soapdtc.SoapFault;
import com.izforge.soapdtc.SoapMessage;
import com.izforge.soapdtc.SoapMessageUtils;
import com.izforge.soapdtc.transport.HttpSoapTransport;
import com.izforge.soapdtc.transport.SoapTransportException;
import junit.framework.TestCase;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.util.NodeComparator;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Enumeration;

public class HttpSoapServiceServletTest extends TestCase
{
    private Server server;
    private HttpSoapServiceServlet servlet;
    private NodeComparator nodeComparator = new NodeComparator();
    private ServletHolder servletHolder;

    @SuppressWarnings({"UnusedAssignment"})
    public void testDoGetPost() throws Exception
    {
        servlet.addPathInfoMapping("/iya", new IServiceEndpointHandler()
        {
            public SoapMessage handleRequestResponse(SoapMessage inMessage, String soapAction)
            {
                return inMessage;
            }

            public SoapMessage handleSollicitResponse(String soapAction)
            {
                try
                {
                    return SoapMessageUtils.readSoapMessageFromStream(getClass().getResourceAsStream("soap-message.xml"));
                }
                catch (DocumentException e)
                {
                    return null;
                }
                catch (IOException e)
                {
                    return null;
                }
                catch (XmlPullParserException e)
                {
                    return null;
                }
            }

            public boolean isSoapHeaderHandled(QName headerQName)
            {
                return false;
            }
        });

        HttpSoapTransport soapTransport = new HttpSoapTransport();
        SoapMessage message;
        SoapMessage response;

        // Try without a header handler
        message = SoapMessageUtils.readSoapMessageFromStream(getClass().getResourceAsStream("soap-message.xml"));
        try
        {
            response = soapTransport.sendReceive("http://localhost:8085/dtc/iya", message, null);
            fail("There should have been an exception here!");
        }
        catch (SoapTransportException e)
        {
            SoapMessage msg = e.getSoapMessageWithFault();
            assertNotNull(msg);
            assertTrue(msg.containsFault());
            assertEquals("env:MustUnderstand", msg.getBodyElement().valueOf("env:Fault/env:Code"));
        }

        // Try with a header handler
        servlet.addHeaderHandlerMapping(QName.get("h:plop", "urn:plop:da:plop"), new ISoapHeaderHandler()
        {
            public SoapFault handleHeader(Element header, SoapMessage soapMessage)
            {
                return null;
            }
        });
        response = soapTransport.sendReceive("http://localhost:8085/dtc/iya", message, null);
        assertEquals(0, nodeComparator.compare(message, response));

        // Sollicit-request
        response = soapTransport.receive("http://localhost:8085/dtc/iya", null);
        assertEquals(0, nodeComparator.compare(message, response));

        // Try a non-existent URL
        try
        {
            soapTransport.sendReceive("http://localhost:8085/dtc/plop", message, null);
            fail("This pathinfo should not be handled");
        }
        catch (SoapTransportException e)
        {
        }

        // WSDL
        servlet.addWsdlProviderMapping("/iya", new StaticResourceWsdlProvider("not-a-wsdl.txt"));
        HttpClient httpClient = new HttpClient();
        HttpMethod method = new GetMethod("http://localhost:8085/dtc/iya/wsdl");
        httpClient.executeMethod(method);
        String data = method.getResponseBodyAsString();
        method.releaseConnection();
        assertEquals(200, method.getStatusCode());
        assertEquals("This is not a WSDL document.", data);

        // WSDL wrong case
        method = new GetMethod("http://localhost:8085/dtc/plop/wsdl");
        httpClient.executeMethod(method);
        method.releaseConnection();
        assertEquals(404, method.getStatusCode());
    }

    public void testBSFSetup() throws Exception
    {
        servlet.init(new ServletConfig()
        {
            public String getServletName()
            {
                return null;
            }

            public ServletContext getServletContext()
            {
                return null;
            }

            public String getInitParameter(String string)
            {
                if (HttpSoapServiceServlet.BSF_SETUP_RESOURCE_INIT_PARAM.equals(string))
                {
                    return "bsf-setup.properties";
                }
                return null;
            }

            public Enumeration getInitParameterNames()
            {
                return null;
            }
        });

        assertNotNull(servlet.getPathInfoMapping().get("/da-service"));
        assertNull(servlet.getPathInfoMapping().get("/iya"));
    }

    @Override
    protected void setUp() throws Exception
    {
        servlet = new HttpSoapServiceServlet();
        server = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8085);
        server.setConnectors(new Connector[]{connector});

        ServletHandler servletHandler = new ServletHandler();
        servletHolder = new ServletHolder(servlet);
        servletHandler.addServletWithMapping(servletHolder, "/dtc/*");
        server.setHandler(servletHandler);

        server.start();
    }

    @Override
    protected void tearDown() throws Exception
    {
        server.stop();
    }
}
