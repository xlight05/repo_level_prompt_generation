package dovetaildb.dbrepository;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngineFactory;


import dovetaildb.api.ApiException;
import dovetaildb.api.ApiService;
import dovetaildb.api.ChangesetBuffer;
import dovetaildb.dbservice.DbService;
import dovetaildb.dbservice.EmptyDbService;
import dovetaildb.util.Dirty;
import dovetaildb.util.Util;

public class StandardDbRepository extends Dirty.Abstract implements DbRepository, Serializable {
	
	private static final long serialVersionUID = -5610780359316342863L;

	//public static final String DEFAULT_SCRIPT_ENGINE = "Mozilla Rhino:1.6 release 2";
	public static final String DEFAULT_SCRIPT_ENGINE = "Mozilla Rhino:Rhino 1.7 release 3 2012 05 01";
	
	
	protected ConcurrentHashMap<String, DbRecord> repo;
	protected String defaultScriptEngine = DEFAULT_SCRIPT_ENGINE;
	
	protected StandardDbRepository() {}

	static class ScriptFile {
		String code;
		String language = DEFAULT_SCRIPT_ENGINE;
		static final Pattern propRegex = Pattern.compile("\\bDDB_(\\w+)\\s*=\\s*(\\S+)\\b");
		static final Pattern nlRegex = Pattern.compile("[\\r\\n]+");
		public ScriptFile(String code) {
			this.code = code;
			Matcher nlMatcher = nlRegex.matcher(code);
			int firstNewline;
			if (nlMatcher.find()) {
				firstNewline = nlMatcher.start();
				if (nlMatcher.find()) { //in the first two lines
					firstNewline = nlMatcher.start();
				}
			} else {
				firstNewline = code.length();
			}
			Matcher matcher = propRegex.matcher(code);
			while(matcher.find()) {
				if (matcher.start() > firstNewline) break;
				String key = matcher.group(1);
				String val = matcher.group(2);
				if (key.equals("language")) language = val;
				else throw new RuntimeException("Unsupported code parameter: \""+key+"\"");
			}
		}
	}

	static String getScriptEngineFactoryName(ScriptEngineFactory factory) {
		return factory.getEngineName()+":"+factory.getEngineVersion();
	}
	
	static ScriptEnv buildScriptEnv(Map<String,String> codeFiles, ConcurrentHashMap<String,Object> globals) {
		ScriptEnv env = new ScriptEnv(globals);
		String[] filenames = codeFiles.keySet().toArray(new String[]{});
		Arrays.sort(filenames);
		for(String filename : filenames) {
			env.ingestScript(filename, codeFiles.get(filename), null);
		}
		return env;
	}

	private DbRecord getDbRec(String name) {
		DbRecord rec = repo.get(name);
		if (rec == null) throw new ApiException("UnknownDb", "There is no database named \""+name+"\"");
		return rec;
	}
	
	private DbRecord getOrCreateDbRec(String dbName) {
		DbRecord rec = repo.get(dbName);
		if (rec == null) {
			rec = new DbRecord();
			rec.setDirtyListener(this);
			repo.put(dbName, rec);
			setDirty();
		}
		return rec;
	}

	public ApiService newSession(String db, ParsedRequest request) {
		DbRecord rec = getDbRec(db);
		ApiService api = new ChangesetBuffer(rec.db);
		return rec.wrapApiService(api, request);
	}

	public Object invokeFunction(String dbName, String functionName, Object[] args) {
		DbRecord rec = getDbRec(dbName);
		return rec.scriptEnv.invokeFunction(functionName, args);
	}
	
	public Object executeCode(ApiService api, String dbName, String code) {
		DbRecord rec = getDbRec(dbName);
		return rec.scriptEnv.ingestScript("code", code, Util.literalSMap().p("api", api));
	}
	
	public DbService getDb(String db) {
		DbRecord rec = getDbRec(db); 
		return (rec==null) ? null : rec.db;
	}

	public void setDb(String dbName, DbService dbService) {
		DbRecord rec = getOrCreateDbRec(dbName);
		rec.setDbService(dbService);
	}

	public boolean deleteDb(String dbName) {
		if (!repo.containsKey(dbName)) return false;
		DbRecord rec = getDbRec(dbName);
		rec.db.drop();
		rec.setDirtyListener(null);
		DbRecord last = repo.remove(dbName);
		setDirty();
		return (last != null);
	}

	@Override
	public void setCodeFile(String dbName, String fileName, String content) {
		DbRecord rec = getDbRec(dbName);
		rec.code.put(fileName, content);
		rec.signalCodeChange();
		setDirty();
	}

	@Override
	public void setAllCodeFiles(String dbName, Map<String, String> codeFiles) {
		DbRecord rec = getDbRec(dbName);
		rec.code = new HashMap<String,String>(codeFiles);
		rec.signalCodeChange();
		setDirty();
	}
	
	public boolean deleteCodeFile(String dbName, String fileName) {
		DbRecord rec = getDbRec(dbName);
		boolean removed = (rec.code.remove(fileName) != null);
		rec.signalCodeChange();
		setDirty();
		return removed;
	}
	public List<String> getCodeFileNames(String dbName) {
		DbRecord rec = getDbRec(dbName);
		return new ArrayList<String>(rec.code.keySet());
	}

	public String getCodeFile(String dbName, String filename) {
		return getDbRec(dbName).code.get(filename);
	}
	
	public void close() {}

	public List<String> getDbNames() {
		return new ArrayList<String>(repo.keySet());
	}

	public void initMeta() {
		if (repo.get("_meta") == null) {
			setDb("_meta", new EmptyDbService());
			setDirty();
		}
		getDbRec("_meta").putInAll("repo", this);
	}

	public void force() {}

}
