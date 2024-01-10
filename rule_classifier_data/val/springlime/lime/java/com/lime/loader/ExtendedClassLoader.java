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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.WeakHashMap;

import sun.misc.CompoundEnumeration;

/**
 * Classloader that acts as an Aggregator of several classloaders.
 * Implementation uses a weakhashmap, to not interfere with 
 * classloaders lifecycle and grant uniqueness
 * Delegates any operation to the inner loaders.
 * 
 * @author Daniele Milani
 *
 */
public class ExtendedClassLoader extends ClassLoader{


	private WeakHashMap<ClassLoader,Object> loaders;
	
	/**
	 *  Creates an ExtendedClassLoader.
	 */
	public ExtendedClassLoader() {
		loaders = new WeakHashMap<ClassLoader,Object>();
	}
	
	/**
	 *  Creates an ExtendedClassLoader and adds all the passed loaders to the set.
	 */
	public ExtendedClassLoader(Collection<ClassLoader> loaders){
		this.loaders = new WeakHashMap<ClassLoader,Object>();
		for(ClassLoader loader : loaders){
			addLoader(loader);
		}
	}
	
	/**
	 * Adds a classloader to the set.
	 * 
	 * @param cl the Classloader
	 */
	public synchronized void addLoader(ClassLoader cl){
		loaders.put(cl,null);
	}
	
	/**
	 * Adds the calling thread's classloader to the set.
	 */
	public synchronized void addLoader(){
		loaders.put(Thread.currentThread().getContextClassLoader(),null);
	}
	
	/**
	 * Removes a classloader from the set.
	 * 
	 * @param cl the Classloader
	 */
	public synchronized void removeLoader(ClassLoader cl){
		loaders.remove(cl);
	}
	
	/**
	 * Removes the calling thread's classloader from the set.
	 * 
	 */
	public synchronized void removeLoader(){
		loaders.remove(Thread.currentThread().getContextClassLoader());
	}
	

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#getResource(java.lang.String)
	 */
	@Override
	public URL getResource(String name) {
		URL url;
		for(ClassLoader cl : loaders.keySet()){
			if((url=cl.getResource(name))!=null)return url;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
	 */
	@Override
	public InputStream getResourceAsStream(String name) {
		InputStream is;
		for(ClassLoader cl : loaders.keySet()){
			if((is=cl.getResourceAsStream(name))!=null)return is;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#getResources(java.lang.String)
	 */
	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		Enumeration[] tempurls = new Enumeration[loaders.size()];
		int i =0;
		for(ClassLoader loader : loaders.keySet()){
			tempurls[i++]=loader.getResources(name);
		}
		return new CompoundEnumeration<URL>(tempurls);
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> clazz;
		for(ClassLoader cl : loaders.keySet()){
			try{
				clazz=cl.loadClass(name);
				return clazz;
			}
			catch(ClassNotFoundException e){				
			}
		}
		throw new ClassNotFoundException(name + " Not found in Extended Class Loader");
	}
}
