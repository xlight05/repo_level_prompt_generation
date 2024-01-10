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


package com.lime.loader;

import java.util.HashMap;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.support.ServletContextResource;

import com.lime.bean.Module;


/**
 * ResourceLoader implementation, loads resources from any of the application modules.
 * The resource implementation used to retrieve resources is ServletContextResource,
 * if a resource of such type is not found, the loading is delegated to a DefaultResourceLoader.
 * 
 * @author Daniele Milani
 * @see <a href="http://static.springsource.org/spring/docs/3.0.1.RELEASE/javadoc-api/org/springframework/core/io/ResourceLoader.html">ResourceLoader</a>
 * @see <a href="http://static.springsource.org/spring/docs/3.0.1.RELEASE/javadoc-api/org/springframework/core/io/DefaultResourceLoader.html">DefaultResourceLoader</a>
 */
public class ModularApplicationResourceLoader implements ResourceLoader{

	public final static String BEAN_NAME = "applicationResourceLoader";

	private HashMap<String, Module> modules;
	
	/**
	 * Instatiates a loader for an application.
	 * 
	 * @param modules all the modules of the application.
	 */
	public ModularApplicationResourceLoader(HashMap<String, Module> modules){
		this.modules = modules;
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.core.io.ResourceLoader#getClassLoader()
	 */
	public ClassLoader getClassLoader() {
		return this.getClassLoader();
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.core.io.ResourceLoader#getResource(java.lang.String)
	 */
	public Resource getResource(String path) {
		Resource res = null;
		for(Module mod : modules.values()){
			res=loadFromModule(path,mod);
			if(res!=null&& res.exists())break;
		}
		return res;
	}

	/**
	 * Return a Resource handle for the specified resource from the specified module.
	 * 
	 * @param path  the resource location
	 * @param modulename the name of the module 
	 * @return a corresponding Resource handle
	 */
	public Resource getResource(String path,String modulename) {
		Module module = modules.get(modulename);
		if(module!=null)return loadFromModule(path,module);
		else return null;
	}
	
	/**
	 * Loads a Resource from the specified path in the specified module.
	 * 
	 * @param path  the resource location
	 * @param modulename the name of the module 
	 * @return a corresponding Resource handle
	 */
	private Resource loadFromModule(String path,Module module){
		ServletContextResource res = new ServletContextResource(module.getServletContext(),path);
		if(res!=null && res.exists()){
			return res;
		}
		else{
			return module.getResloader().getResource(path);
		}
	}
	
}
