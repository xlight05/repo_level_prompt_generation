package org.jjsc.compiler.namespace;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jjsc.compiler.Namespace;
import org.jjsc.utils.Name;
/**
 * Represents java type scope namespace. Provides methods to access the
 * imported classes and declarations.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class JavaTypesNamespace extends SimpleNamespace implements Namespace {
	protected final Map<String,String> imports;
	protected final NamesGenerator generator;
	
	public JavaTypesNamespace(Namespace parent) {
		super(parent);
		imports = new HashMap<String, String>();
		generator = NamesGenerator.createForTypesNames();
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.namespace.SimpleNamespace#resolve(java.lang.String)
	 */
	@Override
	public String resolve(final String qName) {
		if(!Name.isInternalName(qName)){
			return super.resolve(qName);
		}
		String result = get(qName);
		if(result!=null){
			return result;
		}
		return add(qName);
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.namespace.SimpleNamespace#add(java.lang.String)
	 */
	@Override
	public String add(String qName) {
		String result = generator.get(Name.fromInternalName(qName)); 
		imports.put(qName, result);
		return result;
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.namespace.SimpleNamespace#get(java.lang.String)
	 */
	@Override
	public String get(String qName) {
		if(imports.get(qName)!=null){
			return imports.get(qName);
		}
		return super.get(qName);
	}
	/**
	 * @return map presentation of this namespace 
	 * where fully qualified names are the keys and 
	 * short names are the values.
	 */
	public Map<String,String> getImports(){
		return Collections.unmodifiableMap(imports);
	}
}
