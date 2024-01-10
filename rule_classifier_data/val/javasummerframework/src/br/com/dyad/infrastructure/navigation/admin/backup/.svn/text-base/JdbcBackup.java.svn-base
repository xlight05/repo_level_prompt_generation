package br.com.dyad.infrastructure.navigation.admin.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import br.com.dyad.infrastructure.persistence.DatabaseConfig;
import br.com.dyad.infrastructure.persistence.DatabaseConnection;

public class JdbcBackup {
	
	public static final int QTD_REGISTROS = 1000;
	public static final String FIELD_SEPARATOR = ":";
	public static final String END_LINE = "\n";
	public static final String CLASS_DEF = "#CLASS#";
	public static final String NULL_VALUE = "NULL";
	public static final String BACKUP_FILE = "backup";
	public static final String MD5_FILE = "MD5";
	
	private String database;
	private DatabaseConfig databaseConfig;
	
	public DatabaseConfig getDatabaseConfig() {
		return databaseConfig;
	}

	public void setDatabaseConfig(DatabaseConfig databaseConfig) {
		this.databaseConfig = databaseConfig;
	}

	public JdbcBackup(String database) {
		this.database = database;
	}
	
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public void connect() throws Exception {		
		databaseConfig = DatabaseConnection.connect(getDatabase(), true);		
	}	
	
	public static String getFileMd5(String filename) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			File f = new File(filename);
			InputStream is = new FileInputStream(f);				
			byte[] buffer = new byte[8192];
			int read = 0;
			try {
				while( (read = is.read(buffer)) > 0) {
					digest.update(buffer, 0, read);
				}		
				byte[] md5sum = digest.digest();
				BigInteger bigInt = new BigInteger(1, md5sum);
				String output = bigInt.toString(16);


				return output;
			}
			catch(IOException e) {
				throw new RuntimeException("Unable to process file for MD5", e);
			}
			finally {
				try {
					is.close();
				}
				catch(IOException e) {
					throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
