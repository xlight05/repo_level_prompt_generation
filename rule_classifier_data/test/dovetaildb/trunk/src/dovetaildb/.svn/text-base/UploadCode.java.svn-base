package dovetaildb;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dovetaildb.servlet.DovetaildbServletClient;
import dovetaildb.util.Util;

public class UploadCode extends DovetaildbServletClient {

	
	public UploadCode(String urlBase) {
		super(urlBase);
	}
	
	/**
	 * Replaces any existing code in a database with code files in a directory 
	 * on the local machine.
	 * @param dbName name of the database to alter.
	 * @param localCodeDirectory a directory on the local machine that contains script files to be registered with the database.
	 */
	public void setCodeFromLocal(String dbName, List<String> localCodeFiles) {
		Map<String,String> files = new HashMap<String,String>();
		for(String fullFileName : localCodeFiles) {
			File file = new File(fullFileName);
			if (! file.exists()) throw new RuntimeException("No file exists at "+fullFileName);
			if (! file.isFile()) throw new RuntimeException(fullFileName+" is not a regular file");
			if (! file.canRead()) throw new RuntimeException("File at "+fullFileName+" is not readable");
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
	    code.append(",");
	    code.append(Util.jsonEncode(files));
	    code.append(")");
		String body = execute(code.toString());
		System.out.println("Set "+files.size()+" code files in database "+dbName);
	}
}
