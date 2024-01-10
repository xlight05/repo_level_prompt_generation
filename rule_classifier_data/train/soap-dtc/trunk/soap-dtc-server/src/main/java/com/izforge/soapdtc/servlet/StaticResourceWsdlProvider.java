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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

/**
 * A simple WSDL provider that delivers a static WSDL document from a resource.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public class StaticResourceWsdlProvider implements IWsdlProvider
{
    /**
     * The path to a resource containing the WSDL document.
     */
    private String resource;

    /**
     * The WSDL document.
     */
    private String wsdl;

    /**
     * Constructor.
     *
     * @param resource the resource to grab the WSDL from.
     */
    public StaticResourceWsdlProvider(String resource)
    {
        this.resource = resource;
    }

    /**
     * Getter for the WSDL string, with a lazzy-loading from the resource.
     *
     * @return the WSDL document as a string.
     * @throws java.io.IOException thrown in case of an I/O error.
     */
    protected String getWsdl() throws IOException
    {
        if (wsdl == null)
        {
            InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource));
            char[] buffer = new char[1024];
            int read = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while (read != -1)
            {
                read = reader.read(buffer, 0, 1024);
                if (read > 0) stringBuffer.append(buffer, 0, read);
            }
            reader.close();
            wsdl = stringBuffer.toString();
        }
        return wsdl;
    }

    public void writeWsdl(Writer writer) throws IOException
    {
        writer.write(getWsdl());
    }
}
