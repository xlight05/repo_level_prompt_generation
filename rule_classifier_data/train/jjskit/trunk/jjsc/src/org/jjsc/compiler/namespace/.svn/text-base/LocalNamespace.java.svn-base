package org.jjsc.compiler.namespace;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jjsc.compiler.Namespace;
import org.jjsc.utils.Name;
/**
 * This namespace provides names for local-scoped block of bytecode.
 * Such blocks are represented in java language by static blocks and 
 * static methods. It cannot be applied directly for instance methods 
 * and constructors. Use {@link InstanceMethodNamespace} for such purposes.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public class LocalNamespace extends SimpleNamespace {
	protected final Map<String,String> debug;
	protected final NamesGenerator generator;
	private Map<Integer,Integer> vars;
	/**
	 * Creates namespace for local scope with descriptor passed 
	 * in first parameter and parent namespace passed in second 
	 * parameter.
	 * @param desc
	 * @param parent
	 */
	public LocalNamespace(String desc,Namespace parent) {
		super(parent);
		debug = new HashMap<String, String>();
		generator = NamesGenerator.createLocal();
		loadParams(desc);
	}

	private void loadParams(String desc) {
		vars = new HashMap<Integer, Integer>();
		Iterator<String> params = getParameters(desc).iterator();
		for(int i=0;params.hasNext();i++){
			vars.put(i, vars.size());
			String p = params.next();
			if(p.equals("L")||p.equals("D")){
				i++;
			}
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.namespace.SimpleNamespace#resolve(java.lang.String)
	 */
	@Override
	public String resolve(final String qName) {
		if(Name.isInternalName(qName)){
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
		String result = generator.get(qName); 
		debug.put(qName, result);
		return result;
	}
	/**
	 * @return new unique identifier never used before 
	 */
	public String add() {
		return generator.get(null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.jjsc.compiler.namespace.SimpleNamespace#get(java.lang.String)
	 */
	@Override
	public String get(String qName) {
		if(debug.get(qName)!=null){
			return debug.get(qName);
		}
		return super.get(qName);
	}
	/**
	 * @return map presentation of this namespace 
	 * where fully qualified names are the keys and 
	 * short names are the values.
	 */
	public Map<String,String> getDebugMap(){
		return Collections.unmodifiableMap(debug);
	}
	/**
	 * @param var
	 * @return name of the local variable with index specified.
	 */
	public String getLocalVar(int var) {
		if(vars.get(var)==null){
			vars.put(var,vars.size());
		}
		return "arguments["+vars.get(var)+']';
	}
	/**
	 * Parses the method or constructor description and returns 
	 * the list of parameter types.
	 * @param description
	 * @return the list of parameter types (never <code>null</code>).
	 */
	public List<String> getParameters(String desc) {
		List<String> result = new LinkedList<String>();
		final int len = desc.length();
		char[] array = desc.toCharArray();
		for(int i=1,check = 1;i<len;){
			switch(array[i]){
			case ')':
				return result;
			case '[':
				i++;
				continue;
			case 'L':
				while(array[++i]!=';');
			}
			result.add(desc.substring(check,++i));
			check = i;
		}
		throw new IllegalStateException("Internal error");
	}
}
