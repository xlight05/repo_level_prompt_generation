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

package com.lime.bean;

/**
 * A bean describing a modular application.
 * 
 * A bean of this type must be declared and initialized in 
 * Lime root context for each of the Modular Application 
 * in the domain.
 * 
 * 
 * @author Daniele Milani
 * 
 *
 */
public class ModularApplication {
	

	public final static String BEAN_NAME = "applicationDescriptor";
	
	private String name;
	
	private String[] requiredmodules;
	private String[] optionalmodules;
	private String commonCtxPattern;
	private String extCxtPattern;
	
	private final String classpathprefix = "classpath*:";
	
	
	
	
	/**
	 * @param name the name of the modular application
	 * @param commonCtxPattern the classpath pattern where the common context descriptors are stored within the application.
	 * @param extCxtPattern the classpath pattern where the module context descriptors are stored within the module.
	 * @param requiredmodules the list of required modules names
	 * @param optionalmodules the list of optional modules names
	 * @see <a href="http://static.springsource.org/spring/docs/3.0.0.RELEASE/reference/html/resources.html#resources-app-ctx-wildcards-in-resource-paths">Spring resource patterns</a>
	 */
	public ModularApplication(String name,String commonCtxPattern,String extCxtPattern,String[] requiredmodules,String[] optionalmodules){
		this.name = name.trim();
		this.commonCtxPattern = classpathprefix + commonCtxPattern.trim();
		this.extCxtPattern = classpathprefix + extCxtPattern.trim();
		this.requiredmodules = requiredmodules;
		this.optionalmodules = optionalmodules;
	}
	
	/**
	 * @param name the name of the modular application
	 * @param commonCtxPattern the classpath pattern where the common context descriptors are stored within the application.
	 * @param extCxtPattern the classpath pattern where the module context descriptors are stored within the module.
	 * @param requiredmodules the list of required modules names
	 * @see <a href="http://static.springsource.org/spring/docs/3.0.0.RELEASE/reference/html/resources.html#resources-app-ctx-wildcards-in-resource-paths">Spring resource patterns</a>
	 */
	public ModularApplication(String name,String commonCtxPattern,String extCxtPattern,String[] requiredmodules){
		this(name,commonCtxPattern,extCxtPattern,requiredmodules,new String[0]);
	}

	/**
	 * @return the list of required modules names
	 */
	public String[] getRequiredModules() {
		return requiredmodules;
	}
	
	/**
	 * @return the list of optional modules names
	 */
	public String[] getOptionalModules() {
		return optionalmodules;
	}

	/**
	 * @return the classpath pattern where the common context descriptors are stored within the application.
	 * @see <a href="http://static.springsource.org/spring/docs/3.0.0.RELEASE/reference/html/resources.html#resources-app-ctx-wildcards-in-resource-paths">Spring resource patterns</a>
	 */
	public String getCommonCtxPattern() {
		return commonCtxPattern;
	}

	/**
	 * @return the classpath pattern where the module context descriptors are stored within the module.
	 * @see <a href="http://static.springsource.org/spring/docs/3.0.0.RELEASE/reference/html/resources.html#resources-app-ctx-wildcards-in-resource-paths">Spring resource patterns</a>
	 */
	public String getExtCxtPattern() {
		return extCxtPattern;
	}
	
	/**
	 * @return the name of the modular application
	 */
	public String getName(){
		return name;
	}


	
}
