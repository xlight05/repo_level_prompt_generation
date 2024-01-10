package com.lime.proxy;

import javax.servlet.ServletContext;

public interface WebProxy<T> {
	
	public T get();
	
	public void init(ServletContext ctx);
	
	public void destroy(ServletContext ctx);

}
