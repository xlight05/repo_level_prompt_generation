package dovetaildb.dbrepository;

import java.util.Collection;

import java.util.List;
import java.util.Map;


import dovetaildb.api.ApiService;
import dovetaildb.dbservice.DbService;
import dovetaildb.util.Dirty;

public interface DbRepository extends Dirty.Able, Dirty.Listener {
	
	// service requests
	public ApiService newSession(String dbName, ParsedRequest request);
	public Object invokeFunction(String dbName, String functionName, Object[] args);
	public Object executeCode(ApiService api, String dbName, String code);
	
	// db management
	public DbService getDb(String dbName);
	public void setDb(String dbName, DbService dbService);
	public boolean deleteDb(String dbName);
	public List<String> getDbNames();

	// code management
	public String getCodeFile(String dbName, String filename);
	public void setCodeFile(String dbName, String fileName, String content);
	public boolean deleteCodeFile(String dbName, String fileName);
	public Collection<String> getCodeFileNames(String dbName);
	public void setAllCodeFiles(String dbName, Map<String,String> codeFiles);

	// administrative
	public void close();
	public void force();
}
