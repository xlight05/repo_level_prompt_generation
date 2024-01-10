/*
 Copyright (c) 2010 Daniele Milani

 Permission is hereby granted, free of charge, to any person
 obtaining a copy of this software and associated documentation
 files (the "Software"), to deal in the Software without
 restriction, including without limitation the rights to use,
 copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the
 Software is furnished to do so, subject to the following
 conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 OTHER DEALINGS IN THE SOFTWARE.
 
 */

package com.lime.proxy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * Proxy Filter that redirects requests to a spring bean in your application context.
 * The name of the bean has to be the same as the filter name in the web.xml, and must implement the javax.servlet.Filter interface.
 * 
 * @author Daniele Milani
 * @see <a href="http://static.springsource.org/spring/docs/3.0.1.RELEASE/javadoc-api/org/springframework/web/filter/DelegatingFilterProxy.html">DelegatingFilterProxy</a>
 */
public class FilterProxy extends GenericFilterBean  {


	private SingletonBeanProxy<Filter> proxy;


	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// Let the delegate perform the actual doFilter operation.
		if(this.proxy.get()!=null){
			this.proxy.get().doFilter(arg0, arg1, arg2);			
		}
	}



	@Override
	protected void initFilterBean() throws ServletException {
		proxy=new SingletonBeanProxy<Filter>(getFilterName());
		proxy.init(getServletContext());
	}



	@Override
	public void destroy() {
		proxy.destroy(getServletContext());
	}
	
	

}
