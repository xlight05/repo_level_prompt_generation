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

import junit.framework.TestCase;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

/**
 * Base test case class for testing Http requests via a custom servlet.
 *
 * @author Julien Ponge (julien.ponge@gmail.com)
 */
public abstract class AbstractHttpSoapTransportTest extends TestCase
{
    private Server server;
    private Servlet servlet;
    private ServletHandler servletHandler;

    protected AbstractHttpSoapTransportTest(Servlet servlet)
    {
        this.servlet = servlet;

        server = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8085);
        server.setConnectors(new Connector[]{connector});

        servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(new ServletHolder(servlet), "/dtc");
        server.setHandler(servletHandler);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        server.start();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        server.stop();
    }
}
