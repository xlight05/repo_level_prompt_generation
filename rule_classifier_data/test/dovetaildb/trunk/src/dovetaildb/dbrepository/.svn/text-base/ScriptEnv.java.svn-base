/**
 * 
 */
package dovetaildb.dbrepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


import dovetaildb.api.ApiException;
import dovetaildb.dbrepository.StandardDbRepository.ScriptFile;

public class ScriptEnv {
	protected ScriptEngineManager manager = new ScriptEngineManager();
	protected ConcurrentHashMap<String, ScriptEngine> engines = new ConcurrentHashMap<String, ScriptEngine>();
	protected ConcurrentHashMap<String,Object> globals;
	
	public ScriptEnv(ConcurrentHashMap<String,Object> globals) {
		this.globals = globals;
	}
	
	public ScriptEngine getScriptEngine(String name) {
		ScriptEngine engine = engines.get(name);
		if (engine == null) {
			for(ScriptEngineFactory factory : manager.getEngineFactories()) {
				String curName = StandardDbRepository.getScriptEngineFactoryName(factory);
				if (curName.equals(name)) {
					engine = factory.getScriptEngine();
					for(Map.Entry<String,Object> entry : globals.entrySet()) {
						engine.put(entry.getKey(), entry.getValue());
					}
					if (factory.getParameter("THREADING") != null) {
						engines.put(name, engine);
					}
					break;
				}
			}
			if (engine == null) {
				ArrayList<String> availableNames = new ArrayList<String>();
				for(ScriptEngineFactory factory : manager.getEngineFactories()) {
					availableNames.add(StandardDbRepository.getScriptEngineFactoryName(factory));
				}
				throw new ApiException("UnknownScriptEngine", "\""+name+"\" is not a known script engine; available engines are: "+availableNames);
			}
		}
		return engine;
	}
	Object ingestScript(String filename, String code, Map<String,Object> env) {
		ScriptFile file = new ScriptFile(code);
		ScriptEngine engine = getScriptEngine(file.language);
		try {
			if (env != null) {
				Bindings bindings = engine.createBindings();
				for(Map.Entry<String,Object> entry : globals.entrySet()) {
					bindings.put(entry.getKey(), entry.getValue());
				}
				for(Map.Entry<String, Object> entry : env.entrySet()) {
					bindings.put(entry.getKey(), entry.getValue());
				}
				return engine.eval(code, bindings);
			} else {
				return engine.eval(code);
			}
		} catch (ScriptException e) {
			String desc = e.getMessage();
			desc = desc.substring(0, desc.lastIndexOf(" in "));
			desc += " in \""+filename+"\"";
			if (e.getLineNumber() != -1) {
				desc += ", line "+e.getLineNumber();
			}
			if (e.getColumnNumber() != -1) {
				desc += ", column "+e.getColumnNumber();
			}
			Throwable cause = e.getCause();
			if (cause != null && cause instanceof Exception) {
				throw new ApiException("ScriptException", desc, (Exception)cause);
			} else {
				throw new ApiException("ScriptException", desc, e);
			}
		}
	}
	<T>T getInterface(Class<T> iface, Object... args) {
		T impl = null;
		for(ScriptEngine engine : engines.values()) {
			if (engine instanceof Invocable) {
				T curImpl = ((Invocable)engine).getInterface(iface);
				if (impl != null) throw new RuntimeException("The "+iface.getSimpleName()+" interface is implemented multiple times in different scripting languages.");
				impl = curImpl;
			}
		}
		return impl;
	}
	public Object invokeFunction(String functionName, Object[] args) {
		int dotIdx = functionName.indexOf('.');
		boolean isMethod = dotIdx > -1;
		String objectName= null;
		if (isMethod) {
			objectName = functionName.substring(0, dotIdx);
			functionName = functionName.substring(dotIdx+1);
		}
		for(ScriptEngine engine : engines.values()) {
			if (engine instanceof Invocable) { 
				Invocable invocable = (Invocable)engine;
				try {
					if (isMethod) {
						Object target = engine.get(objectName);
						if (target != null) {
							try {
								return invocable.invokeMethod(target, functionName, args);
							} catch (NoSuchMethodException e) {
								throw new ApiException("UnknownFunction", "Method \""+functionName+"\" not defined on object \""+objectName+"\"");
							}
						}
					} else {
						try {
							return ((Invocable)engine).invokeFunction(functionName, args);
						} catch (NoSuchMethodException e) {}
					}
				} catch (ScriptException e) {
					throw new ApiException("ScriptException", e);
				}
			}
		}
		throw new ApiException("UnknownFunction", "Function not defined: \""+functionName+"\"");
	}
	public void putInAll(String name, Object value) {
		for(ScriptEngine engine : engines.values()) {
			engine.put(name, value);
		}
	}
}