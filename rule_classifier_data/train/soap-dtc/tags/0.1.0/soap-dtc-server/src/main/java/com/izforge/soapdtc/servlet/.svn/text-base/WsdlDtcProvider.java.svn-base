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

import com.izforge.soapdtc.SoapMessageUtils;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.XPP3Reader;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A WSDL provider that takes advantage of WSDL-DTC descriptions to generate WSDL documents.
 * A WSDL-DTC document captures the essence of a Document/Literal WSDL descrption and makes it
 * easier to write WSDL documents. The generated WSDL document will be a WSDL 1.1 SOAP 1.2 binding.
 *
 * <strong>Warning: WSDL-DTC is still experimental!</strong>  
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public class WsdlDtcProvider implements IWsdlProvider
{
    private String resource;

    private String endpointURL;

    private String wsdl;

    private Document wsdlDOM;
    
    private static final String WSDL_NS_URI = "http://schemas.xmlsoap.org/wsdl/";
    private static final String WSOAP12_NS_URI = "http://schemas.xmlsoap.org/wsdl/soap12/";
    private static final String XSD_NS_URI = "http://www.w3.org/2001/XMLSchema";
    private static final String WSDL_DTC_NS_URI = "http://www.izforge.com/soap-dtc/wsdl-dtc";

    /**
     * Constructor.
     *
     * @param resource the path to a resource containing the WSDL-DTC document.
     * @param endpointURL the service endpoint URL.
     */
    public WsdlDtcProvider(String resource, String endpointURL)
    {
        this.resource = resource;
        this.endpointURL = endpointURL;
    }


    public void writeWsdl(Writer writer) throws IOException
    {
        writer.write(getWsdl());
    }

    /**
     * Returns the string representation of the WSDL document.
     * @return the WSDL document.
     */
    protected String getWsdl()
    {
        if (wsdl == null)
        {
            generateWsdl();
        }
        return wsdl;
    }


    /**
     * Returns the DOM4J representation of the WSDL document.
     * @return the WSDL document.
     */
    protected Document getWsdlDOM()
    {
        return wsdlDOM;
    }

    /**
     * Generates the WSDL document from the WSDL-DTC specification.
     */
    protected void generateWsdl()
    {
        DocumentFactory factory = DocumentFactory.getInstance();

        SoapMessageUtils.configureDocumentFactoryXPathNamespaceURIs(factory);
        SoapMessageUtils.registerDocumentFactoryXPathNamespaceURIs(factory, "xsd", XSD_NS_URI);
        SoapMessageUtils.registerDocumentFactoryXPathNamespaceURIs(factory, "wsdl", WSDL_NS_URI);
        SoapMessageUtils.registerDocumentFactoryXPathNamespaceURIs(factory, "wsoap12", WSOAP12_NS_URI);
        SoapMessageUtils.registerDocumentFactoryXPathNamespaceURIs(factory, "wsdl-dtc", WSDL_DTC_NS_URI);

        XPP3Reader reader = new XPP3Reader();
        Document doc;
        try
        {
            doc = reader.read(getClass().getResourceAsStream(resource));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            wsdl = "Could not generate a WSDL document...";
            return;
        }
        String endpointName = doc.valueOf("wsdl-dtc:specification/wsdl-dtc:endpoint/wsdl-dtc:name/text()");
        String endpointNS = doc.valueOf("wsdl-dtc:specification/wsdl-dtc:endpoint/wsdl-dtc:namespace/text()");

        /*
         * Root
         */
        Document wsdlDoc = factory.createDocument();
        Element wsdlRoot = wsdlDoc.addElement("wsdl:definitions", WSDL_NS_URI);
        wsdlRoot.addNamespace("tns", endpointNS);
        wsdlRoot.addNamespace("wsoap12", WSOAP12_NS_URI);
        wsdlRoot.addNamespace("wsdl", WSDL_NS_URI);
        wsdlRoot.addNamespace("xsd", XSD_NS_URI);
        wsdlRoot.addAttribute("targetNamespace", doc.valueOf("//wsdl-dtc:endpoint/wsdl-dtc:namespace/text()"));

        /*
         * Copy the NS
         */
        List<Namespace> namespaces = doc.getRootElement().additionalNamespaces();
        for (Namespace ns : namespaces)
        {
            wsdlRoot.addNamespace(ns.getPrefix(), ns.getURI());
        }

        /*
         * Schema
         */
        Element types = wsdlRoot.addElement("wsdl:types", WSDL_NS_URI);
        types.add((Element)doc.selectSingleNode("//xsd:schema").clone());

        /*
         * Messages
         */
        List<Element> operationNodes = doc.selectNodes("//wsdl-dtc:operation");
        Map<String, Element> inputWsdlMessages = new HashMap<String, Element>();
        Map<String, Element> outputWsdlMessages = new HashMap<String, Element>();
        for (Element operationElement : operationNodes)
        {
            Element inBody = (Element) operationElement.selectSingleNode("wsdl-dtc:input/wsdl-dtc:body");
            if (inBody != null)
            {
                Element message = factory.createElement("wsdl:message", WSDL_NS_URI);
                message.addAttribute("name", operationElement.attributeValue("name") + "In");
                List<Element> parts = inBody.selectNodes("wsdl-dtc:part");
                for (Element part : parts)
                {
                    message.addElement("wsdl:part", WSDL_NS_URI).addAttribute("name", part.attributeValue("name")).addAttribute("element", part.attributeValue("element"));
                }
                wsdlRoot.add(message);
                inputWsdlMessages.put(operationElement.attributeValue("name"), message);
            }

            Element outBody = (Element) operationElement.selectSingleNode("wsdl-dtc:output/wsdl-dtc:body");
            if (outBody != null)
            {
                Element message = factory.createElement("wsdl:message", WSDL_NS_URI);
                message.addAttribute("name", operationElement.attributeValue("name") + "Out");
                List<Element> parts = outBody.selectNodes("wsdl-dtc:part");
                for (Element part : parts)
                {
                    message.addElement("wsdl:part", WSDL_NS_URI).addAttribute("name", part.attributeValue("name")).addAttribute("element", part.attributeValue("element"));
                }
                wsdlRoot.add(message);
                outputWsdlMessages.put(operationElement.attributeValue("name"), message);
            }
        }

        /*
         * Port Type
         */
        Element portType = wsdlRoot.addElement("wsdl:portType", WSDL_NS_URI);
        portType.addAttribute("name", endpointName + "PortType");
        for (Element operationElement : operationNodes)
        {
            Element op = portType.addElement("wsdl:operation", WSDL_NS_URI).addAttribute("name", operationElement.attributeValue("name"));
            Element in = inputWsdlMessages.get(operationElement.attributeValue("name"));
            Element out = outputWsdlMessages.get(operationElement.attributeValue("name"));

            if (in != null)
            {
                op.addElement("wsdl:input").addAttribute("message", "tns:" + in.attributeValue("name"));
            }
            if (out != null)
            {
                op.addElement("wsdl:output").addAttribute("message", "tns:" + out.attributeValue("name"));
            }
        }

        /*
         * Binding
         */
        Element binding = wsdlRoot.addElement("wsdl:binding", WSDL_NS_URI);
        binding.addAttribute("name", endpointName + "Soap12Binding");
        binding.addAttribute("type", "tns:" + portType.attributeValue("name"));
        Element bbind = binding.addElement("wsoap12:binding", WSOAP12_NS_URI);
        bbind.addAttribute("transport", "http://schemas.xmlsoap.org/soap/http");
        bbind.addAttribute("style", "document");
        for (Element operationElement : operationNodes)
        {
            Element op = binding.addElement("wsdl:operation", WSDL_NS_URI).addAttribute("name", operationElement.attributeValue("name"));
            Element in = inputWsdlMessages.get(operationElement.attributeValue("name"));
            Element out = outputWsdlMessages.get(operationElement.attributeValue("name"));

            Element bop = op.addElement("wsoap12:operation", WSOAP12_NS_URI);
            bop.addAttribute("soapAction", operationElement.attributeValue("soapAction"));
            bop.addAttribute("soapActionRequired", "true");
            bop.addAttribute("style", "document");
            if (in != null)
            {
                Element input = op.addElement("wsdl:input");

                List<Element> headerParts = operationElement.selectNodes("wsdl-dtc:input/wsdl-dtc:header/wsdl-dtc:part");
                for (Element headerPart : headerParts)
                {
                    Element h = input.addElement("wsoap12:header", WSOAP12_NS_URI);
                    h.addAttribute("message", headerPart.attributeValue("type"));
                    h.addAttribute("part", headerPart.attributeValue("name"));
                    h.addAttribute("use", "literal");
                }

                input.addElement("wsoap12:body", WSOAP12_NS_URI).addAttribute("use", "literal");
            }
            if (out != null)
            {
                Element output = op.addElement("wsdl:output");

                List<Element> headerParts = operationElement.selectNodes("wsdl-dtc:output/wsdl-dtc:header/wsdl-dtc:part");
                for (Element headerPart : headerParts)
                {
                    Element h = output.addElement("wsoap12:header", WSOAP12_NS_URI);
                    h.addAttribute("message", headerPart.attributeValue("type"));
                    h.addAttribute("part", headerPart.attributeValue("name"));
                    h.addAttribute("use", "literal");
                }

                output.addElement("wsoap12:body", WSOAP12_NS_URI).addAttribute("use", "literal");
            }
        }

        /*
         * Service
         */
        Element service = wsdlRoot.addElement("wsdl:service", WSDL_NS_URI).addAttribute("name", endpointName);
        Element port = service.addElement("wsdl:port", WSDL_NS_URI);
        port.addAttribute("name", endpointName + "Soap12");
        port.addAttribute("binding", "tns:" + endpointName + "Soap12Binding");
        port.addElement("wsoap12:address", WSOAP12_NS_URI).addAttribute("location", endpointURL);

        /*
         * Done!
         */
        wsdlDOM = wsdlDoc;
        wsdl = wsdlDoc.asXML();
    }
}
