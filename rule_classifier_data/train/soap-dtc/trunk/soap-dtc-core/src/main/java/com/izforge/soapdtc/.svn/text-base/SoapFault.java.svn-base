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

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

import java.util.ArrayList;
import java.util.List;

/**
 * SOAP fault helper class. By default, the error code will be set to env:Sender, and you will need to at least
 * add one reason to produce a valid SOAP fault XML element.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public class SoapFault
{
    /**
     * The Dom4J factory.
     */
    private DocumentFactory documentFactory;

    /**
     * Models env:Code.
     */
    private FaultCode code = new FaultCode();

    /**
     * Models env:Reason.
     */
    private List<FaultReason> reasons = new ArrayList<FaultReason>();

    /**
     * Models env:Node.
     */
    private String node;

    /**
     * Models env:Role.
     */
    private String role;

    /**
     * Models env:Detail.
     */
    private Element detail;

    /* .......................................................................................... */

    public SoapFault()
    {
        this.documentFactory = DocumentFactory.getInstance();
        SoapMessageUtils.configureDocumentFactoryXPathNamespaceURIs(documentFactory);
    }

    public SoapFault(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
    }

    /**
     * Sets the fault code.
     *
     * @param value          should be taken from SoapConstants if possible
     * @param subcodeValue   optional subcode
     * @param subcodeSubcode optinal subcode subcode
     */
    public void setCode(String value, String subcodeValue, String subcodeSubcode)
    {
        code.value = value;
        code.subcodeValue = subcodeValue;
        code.subcodeSubcode = subcodeSubcode;
    }

    /**
     * Adds a human-readable fault description.
     *
     * @param lang the language in which the text is written (ex: en, fr, ...).
     * @param text the text.
     */
    public void addReason(String lang, String text)
    {
        reasons.add(new FaultReason(lang, text));
    }

    /**
     * Sets the optional node information.
     *
     * @param node the node information.
     */
    public void setNode(String node)
    {
        this.node = node;
    }

    /**
     * Sets the optional role information.
     *
     * @param role the role information.
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * Sets the optional detail information, with application-specific content.
     *
     * @param detail the namespaced XML content to be added under env:Detail.
     */
    public void setDetail(Element detail)
    {
        this.detail = detail;
    }

    /**
     * Converts the SOAP fault to an XML element, for example to add it to a SOAP body.
     *
     * @return the fault as an XML element.
     */
    public Element toXmlElement()
    {
        Element faultElement = documentFactory.createElement("env:Fault", SoapConstants.SOAP12_ENV_NS);
        createCodeElement(faultElement);
        createReasonElement(faultElement);
        createNodeElement(faultElement);
        createRoleElement(faultElement);
        createDetailElement(faultElement);
        return faultElement;
    }

    private void createDetailElement(Element faultElement)
    {
        if (detail != null)
        {
            Element e = documentFactory.createElement("env:Detail", SoapConstants.SOAP12_ENV_NS);
            e.add(detail);
            faultElement.add(e);
        }
    }

    private void createRoleElement(Element faultElement)
    {
        if (role != null)
        {
            Element e = documentFactory.createElement("env:Role", SoapConstants.SOAP12_ENV_NS);
            e.setText(role);
            faultElement.add(e);
        }
    }

    private void createNodeElement(Element faultElement)
    {
        if (node != null)
        {
            Element e = documentFactory.createElement("env:Node", SoapConstants.SOAP12_ENV_NS);
            e.setText(node);
            faultElement.add(e);
        }
    }

    private void createReasonElement(Element faultElement)
    {
        Element reasonElement = documentFactory.createElement("env:Reason", SoapConstants.SOAP12_ENV_NS);
        for (FaultReason reason : reasons)
        {
            Element e = documentFactory.createElement("env:Text", SoapConstants.SOAP12_ENV_NS);
            e.add(documentFactory.createAttribute(e, QName.get("xml:lang", SoapConstants.XML_NS), reason.lang));
            e.setText(reason.text);
            reasonElement.add(e);
        }
        faultElement.add(reasonElement);
    }

    private void createCodeElement(Element faultElement)
    {
        Element codeElement = documentFactory.createElement("env:Code", SoapConstants.SOAP12_ENV_NS);
        Element codeValue = documentFactory.createElement("env:Value", SoapConstants.SOAP12_ENV_NS);
        codeValue.setText(code.value);
        codeElement.add(codeValue);
        if (code.subcodeValue != null)
        {
            Element subCode = documentFactory.createElement("env:Subcode", SoapConstants.SOAP12_ENV_NS);
            Element e = documentFactory.createElement("env:Value", SoapConstants.SOAP12_ENV_NS);
            e.setText(code.subcodeValue);
            subCode.add(e);

            if (code.subcodeSubcode != null)
            {
                e = documentFactory.createElement("env:Subcode", SoapConstants.SOAP12_ENV_NS);
                e.setText(code.subcodeSubcode);
                subCode.add(e);
            }
            codeElement.add(subCode);
        }
        faultElement.add(codeElement);
    }

    /* .......................................................................................... */

    private static class FaultCode
    {
        String value = SoapConstants.SOAP12_FAULT_SENDER;
        String subcodeValue;
        String subcodeSubcode;
    }

    private static class FaultReason
    {
        String lang;
        String text;

        public FaultReason(String lang, String text)
        {
            this.lang = lang;
            this.text = text;
        }
    }
}
