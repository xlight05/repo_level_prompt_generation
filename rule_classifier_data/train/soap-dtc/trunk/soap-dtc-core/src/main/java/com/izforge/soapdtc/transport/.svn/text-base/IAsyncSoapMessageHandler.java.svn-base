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

/**
 * Interface for asynchronous SOAP messages handlers.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public interface IAsyncSoapMessageHandler
{
    /**
     * Handles a successful asynchronous response.
     *
     * @param response the response SOAP message.
     */
    public void handleAsyncResponse(SoapMessage response);

    /**
     * Handles a transport exception.
     *
     * @param exception the exception.
     */
    public void handleAsyncTransportException(SoapTransportException exception);
}
