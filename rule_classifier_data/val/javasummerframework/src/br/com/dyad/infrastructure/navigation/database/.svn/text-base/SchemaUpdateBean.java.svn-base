package br.com.dyad.infrastructure.navigation.database;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import br.com.dyad.infrastructure.persistence.DatabaseConfig;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class SchemaUpdateBean {
	
	public static final String ACTION_UPDATE = "U";
	public static final String ACTION_CREATE = "C";
	private String database;
	private DatabaseConfig dbConfig;
	
	public SchemaUpdateBean(String database) {
		this.database = database; 
		this.dbConfig = PersistenceUtil.getDatabaseConfig(database);
	}
		
	public String getDataBaseChanges( String action ) throws Exception {
		String scriptSql = null;
		//try {
			String filePath = System.getProperty("user.dir")
			+ File.separator + "temp" + File.separator
			+ "update.sql";

			System.out.println("File path: " + filePath);
			boolean create = action != null && action.equals(SchemaUpdateBean.ACTION_CREATE);			
			
			if ( !create) {
				SchemaUpdate schema = new SchemaUpdate(dbConfig.getConf());
				schema.setOutputFile(filePath);
				schema.setDelimiter(";");
				schema.execute(true, false);
			} else {
				SchemaExport schema = new SchemaExport(dbConfig.getConf());
				schema.setOutputFile(filePath);
				schema.setDelimiter(";");
				schema.execute(true, true, false, true);
			}

			File file = new File(filePath);				
			scriptSql = FileUtils.readFileToString(file);
			
			//-- Ricardo em 27/10/09: Correcao de BUG. Requisito: 2967338 - Bug ao rodar o upgrade de estrutura.
			//scriptSql = StringUtils.replace(scriptSql, "\n", ";\n");

		//} catch (Exception e) {
		//	System.out.println("Exception: " + e.getMessage());
		//	throw AppException.createException(e);
		//}

		return scriptSql;
	}


	public void executeDataBaseChanges( String script ) throws Exception {
		if (script != null) {
			Session s = PersistenceUtil.getSession(database);
			s.beginTransaction();
			try {
				Query qy = s.createSQLQuery(script);
				qy.executeUpdate();
				s.getTransaction().commit();
			} catch (Exception e) {
				s.getTransaction().rollback();
				throw e;
			}
		} 
	}	
}
