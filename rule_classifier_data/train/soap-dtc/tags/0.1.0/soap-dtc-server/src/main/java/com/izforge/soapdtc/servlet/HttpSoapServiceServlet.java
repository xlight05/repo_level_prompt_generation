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

import com.izforge.soapdtc.SoapConstants;
import com.izforge.soapdtc.SoapFault;
import com.izforge.soapdtc.SoapMessage;
import com.izforge.soapdtc.SoapMessageUtils;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.bsf.util.IOUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.XMLWriter;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * A servlet to process SOAP messages.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public class HttpSoapServiceServlet extends HttpServlet
{
    /**
     * Maps path info to an endpoint handler.
     */
    private Map<String, IServiceEndpointHandler> pathInfoMapping = new HashMap<String, IServiceEndpointHandler>();

    /**
     * Maps path info to a WSDL provider.
     */
    private Map<String, IWsdlProvider> wsdlProviderMapping = new HashMap<String, IWsdlProvider>();

    /**
     * Maps QNames to SOAP header handlers.
     */
    private Map<QName, ISoapHeaderHandler> headerHandlersMapping = new HashMap<QName, ISoapHeaderHandler>();

    /**
     * The Dom4J factory.
     */
    private DocumentFactory documentFactory = DocumentFactory.getInstance();

    /**
     * The servlet init parameter to define a properties file resource for BSF-based configuration.
     */
    public static final String BSF_SETUP_RESOURCE_INIT_PARAM = "bsf.setup.resource";

    /**
     * Key for the BSF language name.
     */
    public static final String BSF_SETUP_LANGUAGE = "language";

    /**
     * Key for the BSF engine class full name.
     */
    public static final String BSF_SETUP_ENGINE = "engine";

    /**
     * Key for the BSF language file extensions, comma-separated.
     */
    public static final String BSF_SETUP_EXTENSIONS = "extensions";

    /**
     * The resource containing the script to set-up the servlet.
     */
    public static final String BSF_SETUP_RESOURCE = "resource";

    @Override
    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        if (servletConfig.getInitParameter(BSF_SETUP_RESOURCE_INIT_PARAM) != null)
        {
            bsfConfigureFromResource(servletConfig.getInitParameter(BSF_SETUP_RESOURCE_INIT_PARAM));
        }
    }

    /**
     * Configures the servlet from a resource file.
     *
     * @param resource the resource containing the properties for BSF.
     */
    private void bsfConfigureFromResource(String resource)
    {
        Properties bsfProps = new Properties();
        try
        {
            bsfProps.load(getClass().getResourceAsStream(resource));
            String language = (String) bsfProps.get(BSF_SETUP_LANGUAGE);
            String engine = (String) bsfProps.get(BSF_SETUP_ENGINE);
            String script = (String) bsfProps.get(BSF_SETUP_RESOURCE);

            BSFManager.registerScriptingEngine(language, engine, ((String) bsfProps.get(BSF_SETUP_EXTENSIONS)).split(","));
            InputStreamReader scriptReader = new InputStreamReader(getClass().getResourceAsStream(script));

            BSFManager bsfManager = new BSFManager();
            bsfManager.declareBean("servlet", this, HttpSoapServiceServlet.class);
            bsfManager.exec(language, script, 0, 0, IOUtils.getStringFromReader(scriptReader));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (BSFException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Changes the document factory.
     *
     * @param documentFactory the new document factory to use.
     */
    public void setDocumentFactory(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
    }

    /**
     * Adds a new mapping between a service endpoint handler and a path info.
     *
     * @param pathInfo the path info.
     * @param handler  the service endpoint handler.
     */
    public void addPathInfoMapping(String pathInfo, IServiceEndpointHandler handler)
    {
        pathInfoMapping.put(pathInfo, handler);
    }

    /**
     * Adds a new mapping between a SOAP header handler and a QName.
     *
     * @param headerQName the header XML QName.
     * @param handler     the handler.
     */
    public void addHeaderHandlerMapping(QName headerQName, ISoapHeaderHandler handler)
    {
        headerHandlersMapping.put(headerQName, handler);
    }

    /**
     * Adds a new mapping between a WSDL provider and a path info (without '/wsdl' appended!).
     *
     * @param pathInfo     the path info.
     * @param wsdlProvider the WSDL provider.
     */
    public void addWsdlProviderMapping(String pathInfo, IWsdlProvider wsdlProvider)
    {
        wsdlProviderMapping.put(pathInfo, wsdlProvider);
    }

    /**
     * Returns the endpoint handlers mapping as an unmodifiable map.
     *
     * @return the endpoint handlers mapping as an unmodifiable map.
     */
    public Map<String, IServiceEndpointHandler> getPathInfoMapping()
    {
        return Collections.unmodifiableMap(pathInfoMapping);
    }

    /**
     * Returns the WSDL providers mapping as an unmodifiable map.
     *
     * @return the WSDL providers mapping as an unmodifiable map.
     */
    public Map<String, IWsdlProvider> getWsdlProviderMapping()
    {
        return Collections.unmodifiableMap(wsdlProviderMapping);
    }

    /**
     * Returns the headers handlers mapping as an unmodifiable map.
     *
     * @return the headers handlers mapping as an unmodifiable map.
     */
    public Map<QName, ISoapHeaderHandler> getHeaderHandlersMapping()
    {
        return Collections.unmodifiableMap(headerHandlersMapping);
    }

    /**
     * Gets the Dom4J factory.
     *
     * @return the Dom4J factory.
     */
    public DocumentFactory getDocumentFactory()
    {
        return documentFactory;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        if (request.getPathInfo().endsWith("/wsdl"))
        {
            handleWsdlRequest(request, httpServletResponse);
            return;
        }

        IServiceEndpointHandler endpointHandler = obtainServiceEndpointHandler(request, httpServletResponse);
        if (endpointHandler == null) return;

        SoapMessage outMessage = endpointHandler.handleSollicitResponse(request.getParameter("SOAPAction"));
        reply(httpServletResponse, outMessage);
    }

    /**
     * Handles a WSDL document request.
     *
     * @param request             the HTTP request.
     * @param httpServletResponse the HTTP response.
     * @throws java.io.IOException thrown in case of an I/O error. 
     */
    private void handleWsdlRequest(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException
    {
        String pathInfo = request.getPathInfo().substring(0, request.getPathInfo().lastIndexOf("/wsdl"));
        IWsdlProvider provider = wsdlProviderMapping.get(pathInfo);
        if (provider == null)
        {
            httpServletResponse.sendError(404, "There is no WSDL document provider for: " + pathInfo);
            return;
        }

        httpServletResponse.setContentType("text/xml");
        httpServletResponse.setCharacterEncoding("UTF-8");
        OutputStreamWriter writer = new OutputStreamWriter(httpServletResponse.getOutputStream());
        provider.writeWsdl(writer);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        IServiceEndpointHandler endpointHandler = obtainServiceEndpointHandler(request, httpServletResponse);
        if (endpointHandler == null) return;

        try
        {
            SoapMessage inMessage = SoapMessageUtils.readSoapMessageFromStream(request.getInputStream(), documentFactory);
            SoapFault headerFault = processHeaders(inMessage, endpointHandler);
            SoapMessage outMessage;
            if (headerFault != null)
            {
                outMessage = new SoapMessage(documentFactory);
                outMessage.getBodyElement().add(headerFault.toXmlElement());
            }
            else
            {
                outMessage = endpointHandler.handleRequestResponse(inMessage, request.getParameter("SOAPAction"));
            }
            reply(httpServletResponse, outMessage);
        }
        catch (DocumentException e)
        {
            throw new ServletException(e);
        }
        catch (XmlPullParserException e)
        {
            throw new ServletException(e);
        }
    }

    /**
     * Process the SOAP headers, and generates a fault if needed.
     *
     * @param inMessage       the SOAP message.
     * @param endpointHandler the service endpoint handler.
     * @return a fault if an error occurs, <code>null</code> otherwise.
     */
    private SoapFault processHeaders(SoapMessage inMessage, IServiceEndpointHandler endpointHandler)
    {
        Iterator hit = inMessage.getHeaderElement().elementIterator();
        while (hit.hasNext())
        {
            Element header = (Element) hit.next();
            ISoapHeaderHandler handler = headerHandlersMapping.get(header.getQName());
            if (headerCannotBeUnderstood(handler, header, endpointHandler))
            {
                SoapFault fault = new SoapFault(documentFactory);
                fault.setCode(SoapConstants.SOAP12_FAULT_MUST_UNDERSTAND, null, null);
                fault.addReason("en", "A mandatory SOAP header cannot be understood: " + header.toString());
                return fault;
            }
            if (handler != null)
            {
                SoapFault fault = handler.handleHeader(header, inMessage);
                if (fault != null)
                {
                    return fault;
                }
            }
        }
        return null;
    }

    /**
     * Checks wether a header has to be understood, but no handler has been defined in the servlet or the endpoint handler.
     *
     * @param handler         the header handler, can be <code>null</code>.
     * @param header          the header element.
     * @param endpointHandler the endpoint handler.
     * @return wether it could not be handled while it was mandatory.
     */
    private boolean headerCannotBeUnderstood(ISoapHeaderHandler handler, Element header, IServiceEndpointHandler endpointHandler)
    {
        return handler == null &&
                "1".equals(header.valueOf("@env:mustUnderstand")) &&
                !endpointHandler.isSoapHeaderHandled(header.getQName());
    }

    /**
     * Sends a SOAP message as a reply. If the SOAP message contains a fault, a HTTP 500 status code
     * is used.
     *
     * @param httpServletResponse the servlet response.
     * @param outMessage          the SOAP message to sendReceive.
     * @throws IOException when an I/O exception occurs.
     */
    private void reply(HttpServletResponse httpServletResponse, SoapMessage outMessage)
            throws IOException
    {
        if (outMessage.getFaultElement() != null)
        {
            httpServletResponse.setStatus(500);
        }
        httpServletResponse.setContentType("text/xml");
        httpServletResponse.setCharacterEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(httpServletResponse.getOutputStream());
        writer.write(outMessage.getSoapDocument());
        writer.close();
    }

    /**
     * Obtains a service endpoint handler, or sends a 404 error if none could be found.
     *
     * @param request             the request object.
     * @param httpServletResponse the response object.
     * @return the service endpoint handler.
     * @throws IOException when an I/O error occurs.
     */
    private IServiceEndpointHandler obtainServiceEndpointHandler(HttpServletRequest request, HttpServletResponse httpServletResponse)
            throws IOException
    {
        String pathInfo = request.getPathInfo();
        IServiceEndpointHandler endpointHandler = pathInfoMapping.get(pathInfo);
        if (endpointHandler == null)
        {
            httpServletResponse.sendError(404, "There is no service endpoint handler for " + pathInfo);
        }
        return endpointHandler;
    }
}
