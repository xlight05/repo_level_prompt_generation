package dovetaildb.dbrepository;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import dovetaildb.api.ApiService;
import dovetaildb.dbservice.DbService;
import dovetaildb.servlet.DovetaildbServletClient;
import dovetaildb.util.Dirty.Listener;
import dovetaildb.util.Util;

public class DbRepositoryProxy extends DovetaildbServletClient implements DbRepository {

	public DbRepositoryProxy(String urlBase, String accesskey) {
		super(urlBase);
		setAccessKey(accesskey);
	}
	
	// service requests
	public ApiService newSession(String dbName, ParsedRequest request) {
		throw new UnsupportedOperationException();
	}
	public Object invokeFunction(String dbName, String functionName, Object[] args) {
		throw new UnsupportedOperationException();
		
	}
	public Object executeCode(ApiService api, String dbName, String code) {
		throw new UnsupportedOperationException();
	}
	
	// db management
	public DbService getDb(String dbName) {
		throw new UnsupportedOperationException();
	}
	public void setDb(String dbName, DbService dbService) {
		repoRequest("setDbService", new Object[]{dbName, dbService});
	}
	public boolean deleteDb(String dbName) {
		Object ret = repoRequest("deleteDb", new Object[]{dbName});
		return ((Boolean)ret).booleanValue();
	}
	public List<String> getDbNames() {
		Object ret = repoRequest("getDbNames", new Object[]{});
		return (List<String>) ret;
	}

	// code management
	public String getCodeFile(String dbName, String filename) {
		Object ret = repoRequest("getCodeFile", new Object[]{dbName, filename});
		return (String) ret;
	}
	public void setCodeFile(String dbName, String fileName, String content) {
		repoRequest("setCodeFile", new Object[]{dbName, fileName, content});
	}
	public void setAllCodeFiles(String dbName, Map<String, String> codeFiles) {
		repoRequest("setAllCodeFiles", new Object[]{dbName, codeFiles});
	}
	public boolean deleteCodeFile(String dbName, String fileName) {
		Object ret = repoRequest("deleteCodeFile", new Object[]{dbName, fileName});
		return ((Boolean)ret).booleanValue();
	}
	public Collection<String> getCodeFileNames(String dbName) {
		Object ret = repoRequest("getCodeFileNames", new Object[]{dbName});
		return ((List<String>)ret);
	}

	public void close() {
		repoRequest("close", new Object[]{});
	}

	public void force() {
	}
	
	// --- additional utility functions ---

	/**
	 * Creates a database and imports code into that database from a local directory.
	 * @see setDbService
	 * @see setCodeFromLocal
	 * @param dbName the name of the database to be assigned
	 * @param createExpression a string containing a javascript expression which, when evaluated on the server, 
	 *        will produce the {@link dovetaildb.dbservice.DbService DbService} to use. 
	 * @param localCodeDirectory a directory on the local machine that contains script files to be registered with the database.
	 */
	public void setDbWithCodeFromLocal(String dbName, String createExpression, String localCodeDirectory) {
		setCodeFromLocal(dbName, localCodeDirectory);
		execute("repo.setDbService("+Util.jsonEncode(dbName)+","+createExpression+")");
	}
	
	/**
	 * Replaces any existing code in a database with code files in a directory 
	 * on the local machine.
	 * @param dbName name of the database to alter
	 * @param localCodeDirectory a directory on the local machine that contains script files to be registered with the database.
	 */
	public void setCodeFromLocal(String dbName, String localCodeDirectory) {
		File dir = new File(localCodeDirectory);
		if (! dir.exists()) throw new RuntimeException("No directory exists at "+localCodeDirectory);
		if (! dir.isDirectory()) throw new RuntimeException(localCodeDirectory+" is not a directory");
		if (! dir.canRead()) throw new RuntimeException("Directory at "+localCodeDirectory+" is not readable");
		Map<String,String> files = new HashMap<String,String>();
		for(File file : dir.listFiles()) {
			if (! file.isFile()) continue;
			String fileName = file.getName();
			if (! fileName.matches(".*\\.\\w+")) {
				System.out.println("Ignoring \""+fileName+"\"");
			} else {
				String content = Util.readFully(file);
				files.put(fileName, content);
				System.out.println("Putting \""+fileName+"\"");
			}
		}
		// manually construct request cause we've got to do tricky stuff
	    StringBuffer code = new StringBuffer();
	    code.append("repo.setAllCodeFiles(");
	    code.append(Util.jsonEncode(dbName));
	    code.append(",Packages.dovetaildb.util.JsConversions.asJavaMap(");
	    code.append(Util.jsonEncode(files));
	    code.append("))");
		String body = execute(code.toString());
		System.out.println("Set "+files.size()+" code files in database "+dbName);
	}
	
	public void setCodeFromLocalFile(String dbName, String localPath) {
		File file = new File(localPath);
		if (! file.exists()) throw new RuntimeException("No file exists at "+localPath);
		if (! file.isFile()) throw new RuntimeException(localPath+" is not a regular file");
		if (! file.canRead()) throw new RuntimeException("File at "+localPath+" is not readable");
		String fileName = file.getName();
		String content = Util.readFully(file);
		repoRequest("setCodeFile", new Object[]{dbName, fileName, content});
		System.out.println("Set "+fileName+" in database "+dbName+" ("+content.length()+" characters)");
	}

	/**
	 * Creates a database using a javascript expression.
	 * @param dbName the created database will have this name.
	 * @param scriptExpression a string containing a javascript expression which, when evaluated on the server, 
	 *        will produce the {@link dovetaildb.dbservice.DbService DbService} to use.
	 * @param localCodeDirectory a directory on the local machine that contains script files to be registered with the database.
	 */
	public String setDbService(String dbName, String scriptExpression) {
		return execute("repo.setDbService("+Util.jsonEncode(dbName)+","+scriptExpression+")");
	}

	
	protected Listener dirtyListener;
	@Override
	public void setDirtyListener(Listener dirtyListener) {
		this.dirtyListener = dirtyListener;
	}

	@Override
	public void setDirty() {
		dirtyListener.setDirty();
	}
	
}
