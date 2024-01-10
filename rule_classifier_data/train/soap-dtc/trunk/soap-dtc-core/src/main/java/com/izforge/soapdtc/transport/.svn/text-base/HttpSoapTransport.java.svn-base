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
import com.izforge.soapdtc.SoapMessageUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HostParams;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.XMLWriter;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * SOAP transport over HTTP / HTTPS. It supports the send/receive and receive message exchange patterns.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public class HttpSoapTransport
{
    /**
     * Dom4J factory.
     */
    private DocumentFactory documentFactory;

    /**
     * HttpClient agent configuration.
     */
    private HostConfiguration hostConfiguration;

    /**
     * Http agent.
     */
    private HttpClient httpClient;

    public HttpSoapTransport()
    {
        this.documentFactory = DocumentFactory.getInstance();
    }

    public HttpSoapTransport(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
    }

    public HttpSoapTransport(HostConfiguration hostConfiguration)
    {
        this.hostConfiguration = hostConfiguration;
    }

    public HttpSoapTransport(DocumentFactory documentFactory, HostConfiguration hostConfiguration)
    {
        this.documentFactory = documentFactory;
        this.hostConfiguration = hostConfiguration;
    }

    /**
     * Provides access to the Dom4J factory in use.
     *
     * @return the Dom4J factory.
     */
    public DocumentFactory getDocumentFactory()
    {
        return documentFactory;
    }

    /**
     * Provides access to the Http agent configuration.
     *
     * @return the Http agent configuration.
     */
    public HostConfiguration getHostConfiguration()
    {
        return hostConfiguration;
    }

    /**
     * Sets the new Http client configuration.
     *
     * @param hostConfiguration the new Http client configuration.
     */
    public void setHostConfiguration(HostConfiguration hostConfiguration)
    {
        this.hostConfiguration = hostConfiguration;
    }

    /**
     * Delegate method.
     *
     * @param httpHost the Http host.
     * @see HostConfiguration#setHost(org.apache.commons.httpclient.HttpHost)
     */
    public void setHostConfigurationHost(HttpHost httpHost)
    {
        hostConfiguration.setHost(httpHost);
    }

    /**
     * Delegate method.
     *
     * @param uri the Http host uri.
     * @see HostConfiguration#setHost(org.apache.commons.httpclient.HttpHost)
     */
    public void setHostConfigurationHost(URI uri)
    {
        hostConfiguration.setHost(uri);
    }

    /**
     * Delegate method.
     *
     * @param proxyHost the proxy host.
     * @see HostConfiguration#setProxyHost(org.apache.commons.httpclient.ProxyHost)
     */
    public void setHostConfigurationProxyHost(ProxyHost proxyHost)
    {
        hostConfiguration.setProxyHost(proxyHost);
    }

    /**
     * Delegate method.
     *
     * @param inetAddress the inet address.
     * @see HostConfiguration#setLocalAddress(java.net.InetAddress)
     */
    public void setHostConfigurationLocalAddress(InetAddress inetAddress)
    {
        hostConfiguration.setLocalAddress(inetAddress);
    }

    /**
     * Delegate method.
     *
     * @param hostParams the host parameters.
     * @see HostConfiguration#setParams(org.apache.commons.httpclient.params.HostParams)
     */
    public void setHostConfigurationParams(HostParams hostParams)
    {
        hostConfiguration.setParams(hostParams);
    }

    /**
     * Lazy instanciation access to the Http agent.
     *
     * @return the Http agent.
     */
    private HttpClient getHttpClient()
    {
        if (httpClient == null)
        {
            httpClient = new HttpClient();
        }
        return httpClient;
    }

    /**
     * Send then receive pattern for the SOAP HTTP binding.
     *
     * @param uri         the endpoint uri.
     * @param soapMessage the SOAP message to sendReceive.
     * @param soapAction  the <code>soapAction</code> parameter, or <code>null</code>
     * @return the response SOAP message.
     * @throws SoapTransportException if an error occurs, including a SOAP fault when appropriate.
     */
    public SoapMessage sendReceive(String uri, SoapMessage soapMessage, String soapAction) throws SoapTransportException
    {
        PostMethod postMethod = new PostMethod(uri);
        try
        {
            encodeSoapAction(postMethod, soapAction);
            postMethod.setRequestEntity(createRequestEntity(soapMessage));
            getHttpClient().executeMethod(hostConfiguration, postMethod);
        }
        catch (Throwable t)
        {
            postMethod.releaseConnection();
            throw new SoapTransportException(t);
        }
        return readSoapResponse(postMethod.getStatusCode(), postMethod);
    }

    /**
     * Asynchronous version of <code>sendReceive</code>.
     *
     * @param uri         the endpoint uri.
     * @param soapMessage the SOAP message.
     * @param soapAction  the optional <code>soapAction</code> parameter.
     * @param handler     the asynchronous handler.
     */
    public void asyncSendReceive(final String uri, final SoapMessage soapMessage, final String soapAction, final IAsyncSoapMessageHandler handler)
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    SoapMessage response = sendReceive(uri, soapMessage, soapAction);
                    handler.handleAsyncResponse(response);
                }
                catch (SoapTransportException e)
                {
                    handler.handleAsyncTransportException(e);
                }
            }
        });
        thread.start();
    }

    /**
     * Receive pattern for the SOAP HTTP binding.
     *
     * @param uri        the endpoint uri.
     * @param soapAction the <code>soapAction</code> parameter, or <code>null</code>
     * @return the response SOAP message.
     * @throws SoapTransportException if an error occurs, including a SOAP fault when appropriate.
     */
    public SoapMessage receive(String uri, String soapAction) throws SoapTransportException
    {
        GetMethod getMethod = new GetMethod(uri);
        try
        {
            encodeSoapAction(getMethod, soapAction);
            getHttpClient().executeMethod(hostConfiguration, getMethod);
        }
        catch (Throwable t)
        {
            getMethod.releaseConnection();
            throw new SoapTransportException(t);
        }
        return readSoapResponse(getMethod.getStatusCode(), getMethod);
    }

    /**
     * Asynchronous version of <code>receive</code>.
     *
     * @param uri        the endpoint uri.
     * @param soapAction the optional <code>soapAction</code> parameter.
     * @param handler    the asynchronous handler.
     */
    public void asyncReceive(final String uri, final String soapAction, final IAsyncSoapMessageHandler handler)
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    SoapMessage response = receive(uri, soapAction);
                    handler.handleAsyncResponse(response);
                }
                catch (SoapTransportException e)
                {
                    handler.handleAsyncTransportException(e);
                }
            }
        });
        thread.start();
    }

    /**
     * Reads a SOAP nessage from a HTTP reponse, or throws an exception if an error occurs.
     *
     * @param statusCode the HTTP status code.
     * @param method     the HTTP method.
     * @return the SOAP message in case of success.
     * @throws SoapTransportException if an error has occured.
     */
    private SoapMessage readSoapResponse(int statusCode, HttpMethod method)
            throws SoapTransportException
    {
        // 200, 202 and 500 have a SOAP payload
        if (statusCode == 200 || statusCode == 202 || statusCode == 500)
        {
            try
            {
                SoapMessage response = SoapMessageUtils.readSoapMessageFromStream(method.getResponseBodyAsStream(), documentFactory);
                method.releaseConnection();
                if (statusCode == 500)
                {
                    throw new SoapTransportException(response);
                }
                else
                {
                    return response;
                }
            }
            catch (DocumentException e)
            {
                method.releaseConnection();
                throw new SoapTransportException(e);
            }
            catch (IOException e)
            {
                method.releaseConnection();
                throw new SoapTransportException(e);
            }
            catch (XmlPullParserException e)
            {
                method.releaseConnection();
                throw new SoapTransportException(e);
            }
        }

        // This is for 4xx errors
        String statusText = method.getStatusText();
        method.releaseConnection();
        throw new SoapTransportException(statusText);
    }

    /**
     * Quote-encoding of a <code>soapAction</code>.
     *
     * @param method     the HTTP method.
     * @param soapAction thd soapAction value, or <code>null</code>.
     */
    private void encodeSoapAction(HttpMethod method, String soapAction)
    {
        if (soapAction != null)
        {
            String action = soapAction;
        	if (!(soapAction.startsWith("\"") && soapAction.endsWith("\"")))
            {
                action = "\"" + soapAction + "\"";
            }
            method.setRequestHeader("SOAPAction", action);
        }
    }

    /**
     * Creates a request entity to sendReceive the SOAP message over HTTP POST.
     *
     * @param soapMessage the SOAP message.
     * @return the request entity.
     * @throws IOException if an I/O error occurs.
     */
    private RequestEntity createRequestEntity(SoapMessage soapMessage) throws IOException
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XMLWriter xmlWriter = new XMLWriter(byteArrayOutputStream);
        xmlWriter.write(soapMessage.getSoapDocument());
        xmlWriter.close();
        byteArrayOutputStream.close();

        return new ByteArrayRequestEntity(byteArrayOutputStream.toByteArray(), "text/xml; charset=UTF-8");
    }
}
