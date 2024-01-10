/**
 * 
 */
package dovetaildb.dbrepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


import dovetaildb.api.ApiService;
import dovetaildb.api.PluginList;
import dovetaildb.dbservice.DbService;
import dovetaildb.util.Dirty;

class DbRecord extends Dirty.Abstract implements Serializable {

	private static final long serialVersionUID = -1307143029380559827L;
	
	protected DbService db;
	protected HashMap<String,String> code = new HashMap<String,String>();
	protected transient ConcurrentHashMap<String,Object> globals = new ConcurrentHashMap<String,Object>();
	protected transient ScriptEnv scriptEnv = new ScriptEnv(globals);
	protected transient PluginList plugins = new PluginList();

	public String toString() {
		return db+","+code.keySet();
	}
	
	public DbRecord() {
	}

	public void setDbService(DbService db) {
		if (this.db != null) this.db.setDirtyListener(null);
		this.db = db;
		if (db != null) db.setDirtyListener(this);
		setDirty();
	}
	
	public void signalCodeChange() {
		plugins = new PluginList();
		globals.put("apiplugins", plugins);
		ScriptEnv newEnv;
		try {
			newEnv = StandardDbRepository.buildScriptEnv(code, globals);
		} finally {
			globals.remove("apiplugins");
		}
		plugins.validatePlugins();
		scriptEnv = newEnv; // validation ok
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// transients have to be explicitly re-initialized (bummer)
		globals = new ConcurrentHashMap<String,Object>();
		scriptEnv = new ScriptEnv(globals);
		signalCodeChange();
	}
	
	public void putInAll(String key, Object value) {
		globals.put(key, value);
		scriptEnv.putInAll(key, value);
		setDirty();
	}

	public ApiService wrapApiService(ApiService api, ParsedRequest request) {
		return plugins.wrapApiService(api, request);
	}
}