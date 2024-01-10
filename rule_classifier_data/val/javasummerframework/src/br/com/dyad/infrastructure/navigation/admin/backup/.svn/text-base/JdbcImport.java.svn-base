package br.com.dyad.infrastructure.navigation.admin.backup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.dyad.client.grid.FieldType;
import br.com.dyad.commons.file.DesEncrypter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.commons.zip.ZipUtils;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.reflect.ObjectConverter;

public class JdbcImport {
	
	private List<HashMap<String, String>> disabledConstraints = new ArrayList<HashMap<String, String>>();
	private AnnotationConfiguration hibernateConf;
	private Session session;

	public JdbcImport(AnnotationConfiguration hibernateConf, Session session) {
		setHibernateConf(hibernateConf);
		setSession(session);
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	
	public AnnotationConfiguration getHibernateConf() {
		return hibernateConf;
	}

	public void setHibernateConf(AnnotationConfiguration hibernateConf) {
		this.hibernateConf = hibernateConf;
	}	
	
	public List<HashMap<String, String>> getDisabledConstraints() {
		return disabledConstraints;
	}

	public void setDisabledConstraints(
			List<HashMap<String, String>> disabledConstraints) {
		this.disabledConstraints = disabledConstraints;
	}

	private void disableConstraints(Session session) {
		List<String> tables = getTables(session);
		
		for (String tbl : tables) {
			List<HashMap<String, String>> constraints = getTableConstraints(session, tbl);
			
			for (HashMap<String, String> props : constraints){
				String sql = getDropSql(props);
				getDisabledConstraints().add(props);
				
				SQLQuery query = session.createSQLQuery(sql);
				query.executeUpdate();
			}
		}
	}
	
	private void enableConstraints(Session session) {
		for (HashMap<String, String> props : getDisabledConstraints()) {
			String sql = getCreateSql(props);
			SQLQuery query = session.createSQLQuery(sql);
			query.executeUpdate();
		}
	}
	
	private String getDropSql(HashMap<String, String> props) {
		return "alter table " + props.get("fktable_name") + " drop constraint " +  props.get("fk_name");		
	}
	
	private String getCreateSql(HashMap<String, String> props) {
		return "alter table " + props.get("fktable_name") + 
		       " add constraint " + props.get("fk_name") + 
		       " foreign key(" + props.get("fkcolumn_name") + 
		       ") references " + props.get("pktable_name") + 
		       "(" + props.get("pkcolumn_name") + ")";
	}
	
	private List<String> getTables(Session session) {
		ArrayList<String> list = new ArrayList<String>();		
		
		try	{
			String[] tipos = new String[1];
			tipos[0] = "TABLE";
			ResultSet rs = session.connection().getMetaData().getTables(null, null, "%", tipos);
			
			while (rs.next()) {
				list.add(rs.getString("table_name"));								
			}			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return list;
	}
	
	private List<HashMap<String, String>> getTableConstraints(Session session, String table) {
		List<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>(); 			
		try	{
			ResultSet rs = session.connection().getMetaData().getImportedKeys(null, null, table);
			
			String[] fields = {
					"pktable_cat",
	                "pktable_schem",
	                "pktable_name",
	                "pkcolumn_name",
	                "fktable_cat",
	                "fktable_schem",
	                "fktable_name",
	                "fkcolumn_name",
	                "key_seq",
	                "update_rule",
	                "delete_rule",
	                "fk_name",
	                "pk_name",
	                "deferrability"};

			
			while (rs.next()) {
				HashMap<String, String> props = new HashMap<String, String>();
				for (String field : fields) {
					props.put(field, rs.getString(field));
				}
				ret.add(props);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ret; 
	}
	
	private String getNextLine(Scanner scanner) {
		String line = scanner.nextLine();		
		String inicio = StringUtils.substringBefore(line, JdbcBackup.FIELD_SEPARATOR);
		int lineSize = Integer.parseInt(inicio);		
		line = line.substring(line.indexOf(JdbcBackup.FIELD_SEPARATOR) + JdbcBackup.FIELD_SEPARATOR.length());
		
		while (lineSize > line.length()){
			line += "\n" + scanner.nextLine();
		}
		
		return line;
	}	
	
	private void validateMd5(String filename) {		
		try {
			String md5File = new File(filename).getParent();
			if (!File.separator.equals("" + md5File.charAt(md5File.length()-1))){				
				md5File += File.separator;
			}
			md5File += JdbcBackup.MD5_FILE;
			
			String storedMd5 = FileUtils.readFileToString(new File(md5File));
			if (!storedMd5.equals(JdbcBackup.getFileMd5(filename))){
				throw new RuntimeException("Bad checksum!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void importFile(String zipFilename, String filePassword) {				
		String filename = new File(zipFilename).getParent();
		String path = filename;
		try{			
			if (filePassword != null && !filePassword.equals("")){
				DesEncrypter desEncrypter = new DesEncrypter(filePassword);
				desEncrypter.decrypt(zipFilename, zipFilename + ".secret");
				zipFilename += ".secret";
			}
		} catch(Exception e){
			throw new RuntimeException(e);			
		}
		
		ZipUtils.unzip(zipFilename, filename);
		
		try {
			if (filePassword != null && !filePassword.equals("")){				
				FileUtils.forceDelete(new File(zipFilename));
			}
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		
		if (!File.separator.equals("" + filename.charAt(filename.length()-1))){				
			filename += File.separator;
		}
		filename += JdbcBackup.BACKUP_FILE;
		
		validateMd5(filename);
		
		File file = new File(filename);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        Scanner scanner = null;
        String[] fields = null;
        int transactions = 0;
                
        try {
            fis = new FileInputStream(file);

            bis = new BufferedInputStream(fis);
            scanner = new Scanner(bis);

            Class currentClass = null;
            Connection con = session.connection();
            disableConstraints(session);            
            //session.beginTransaction();
            con.setAutoCommit(false);
            try{            	
            	while (scanner.hasNext()) {
            		String line = getNextLine(scanner);
            		
            		if (StringUtils.startsWith(line, JdbcBackup.CLASS_DEF)){
            			fields =  StringUtils.split(line.substring(JdbcBackup.CLASS_DEF.length()), ','); 
            			currentClass = Class.forName(fields[0]);
            		} else {
            			PreparedStatement stm = importLine(con, currentClass, fields, line);
            			stm.execute();
            			transactions++;
            		}
            		
            		if (transactions >= JdbcBackup.QTD_REGISTROS){
            			con.commit();
            			transactions = 0;
            		}
            	}
            	enableConstraints(session);
            } catch(Exception e) {
            	if (transactions > 0){
            		con.rollback();
            	}
            	throw new RuntimeException(e);
            } finally {
            	if (transactions > 0){
            		con.commit();
            	}
            }

            fis.close();
            bis.close();
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	removeTempFiles(path);
        }
	}
	
	private PreparedStatement importLine(Connection con, Class currentClass, String[] fields, String line) {
		try{			  	
			String insertFields = "";
			String params = "";
			for (int i = 1; i < fields.length; i++) {
				insertFields += "\"" + PersistenceUtil.getColumnName(getHibernateConf(), currentClass, fields[i]) + "\"" + (fields.length - 1 == i ? "" : ",");
				params += "?" + (fields.length - 1 == i ? "" : ",");
			}
			String sql = "insert into " + PersistenceUtil.getClassTableName(getHibernateConf(), currentClass) + 
			             "(" + insertFields + ") values(" + params + ")";								
			
			PreparedStatement stm = con.prepareStatement(sql);			
			List<String> list = getListValue(line);
			
			for (int i = 1; i < fields.length; i++) {
				String fieldValue = list.get(i);
				if (!fieldValue.equals(JdbcBackup.NULL_VALUE)){                			
					Field field = ReflectUtil.getDeclaredField(currentClass, fields[i]); //currentClass.getDeclaredField(fields[i]);
					FieldType fieldType = ObjectConverter.getInstance().getDataModelType(field);
					
					if (fieldType.equals(FieldType.DATE) || 
							fieldType.equals(FieldType.TIME) || 
							fieldType.equals(FieldType.TIMESTAMP)){
						
						long time = Long.parseLong(fieldValue);
						java.sql.Date sqlDate = new java.sql.Date(time);
						stm.setDate(i, sqlDate);
					} else if (fieldType.equals(FieldType.CLASS)) {
						long id = Long.parseLong(fieldValue);
						stm.setLong(i, id);
					} else if (fieldType.equals(FieldType.DOUBLE)) {                				
						Double dbl = Double.parseDouble(fieldValue);
						stm.setDouble(i, dbl);
					} else if (fieldType.equals(FieldType.INTEGER)) {                				
						Long intValue = Long.parseLong(fieldValue);
						stm.setLong(i, intValue);
					} else if (fieldType.equals(FieldType.BOOLEAN)) {                				
						Boolean boolValue = Boolean.parseBoolean(fieldValue);
						stm.setBoolean(i, boolValue);
					} else if (fieldType.equals(FieldType.ARRAY)) {         
						fieldValue =  fieldValue.substring(1, fieldValue.length() - 1);						
						String[] items = StringUtils.split(fieldValue, ",");
						byte[] arrValue = new byte[items.length];
						
						for (int j = 0; j < items.length; j++) {
							arrValue[i] = Byte.parseByte(items[i]);
						}
						
						stm.setBytes(i, arrValue);
					}  else {	
						stm.setObject(i, fieldValue);
					}
					
					
				} else {
					stm.setObject(i, null);
				}
			}
			
			return stm;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private ArrayList<String> getListValue(String line) {
		ArrayList<String> data = new ArrayList<String>();
		data.add(null);
		
		while (line.length() > 0){			
			//pega o tamanho do campo
			String inicio = StringUtils.substringBefore(line, JdbcBackup.FIELD_SEPARATOR);
			//pega o campo inteiro com tamanho e tudo
			String field = line.substring(0, inicio.length()
					+ JdbcBackup.FIELD_SEPARATOR.length()
					+ Integer.parseInt(inicio));
			//retira o campo da string
			line = line.substring(field.length());
			//retira o tamanho do campo, deixando somente o conteudo e coloca no array
			field = field.substring(field.indexOf(JdbcBackup.FIELD_SEPARATOR) + JdbcBackup.FIELD_SEPARATOR.length());
			data.add(field);
		}
		
		return data;
	}		
	
	private void removeTempFiles(String path) {
		if (!File.separator.equals("" + path.charAt(path.length()-1))){				
			path += File.separator;
		}
		
		FileUtils.deleteQuietly(new File(path + JdbcBackup.BACKUP_FILE));
		FileUtils.deleteQuietly(new File(path + JdbcBackup.MD5_FILE));
	}

}
