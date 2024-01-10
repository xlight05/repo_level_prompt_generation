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

/**
 * Various constants.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public interface SoapConstants
{
    /**
     * SOAP 1.2 Envelope namespace.
     */
    public static final String SOAP12_ENV_NS = "http://www.w3.org/2003/05/soap-envelope";

    /**
     * Prefix used in QName for the SOAP 1.2 Envelope namespace.
     */
    public static final String SOAP12_ENV_PREFIX = "env";

    /**
     * XML Schema namespace.
     */
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";

    /**
     * Prefix used in QName for the XML Schema namespace.
     */
    public static final String XSD_PREFIX = "xsd";

    /**
     * XML namespace.
     */
    public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";

    /**
     * Prefix used in QName for the XML namespace.
     */
    public static final String XML_PREFIX = "xml";

    /* .......................................................................................... */

    /**
     * SOAP fault code. See <a href="http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes">http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes</a>
     */
    public static final String SOAP12_FAULT_VERSION_MISMATCH = "env:VersionMismatch";

    /**
     * SOAP fault code. See <a href="http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes">http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes</a>
     */
    public static final String SOAP12_FAULT_MUST_UNDERSTAND = "env:MustUnderstand";

    /**
     * SOAP fault code. See <a href="http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes">http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes</a>
     */
    public static final String SOAP12_FAULT_DATA_ENCODING_UNKNOWN = "env:DataEncodingUnknown";

    /**
     * SOAP fault code. See <a href="http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes">http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes</a>
     */
    public static final String SOAP12_FAULT_SENDER = "env:Sender";

    /**
     * SOAP fault code. See <a href="http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes">http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#faultcodes</a>
     */
    public static final String SOAP12_FAULT_RECEIVER = "env:Receiver";

    /**
     * SOAP header for reporting not-understood headers.
     */
    public static final String SOAP12_NOT_UNDERSTOOD_HEADER = "env:NotUnderstood";
}
