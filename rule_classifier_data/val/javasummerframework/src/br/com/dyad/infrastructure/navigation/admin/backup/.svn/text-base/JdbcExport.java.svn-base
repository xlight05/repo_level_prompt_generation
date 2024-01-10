package br.com.dyad.infrastructure.navigation.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javassist.Modifier;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.grid.FieldType;
import br.com.dyad.commons.file.DesEncrypter;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.commons.zip.ZipUtils;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.reflect.ObjectConverter;

public class JdbcExport {	
	
	private BufferedWriter outputFile;
	@SuppressWarnings("unchecked")
	private List<Class> classes = null;
	private List<String> ignoredTables = new ArrayList<String>();
	private boolean negativeKeysOnly;
	private String password = null;
	private String database;
	private List<Long> licenses;
			
	public boolean getNegativeKeysOnly() {
		return negativeKeysOnly;
	}

	public void setNegativeKeysOnly(boolean negativeKeysOnly) {
		this.negativeKeysOnly = negativeKeysOnly;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public JdbcExport(String database) {
		this.database = database;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getIgnoredTables() {
		return ignoredTables;
	}

	public void setIgnoredTables(List<String> ignoredClasses) {
		this.ignoredTables = ignoredClasses;
	}

	public BufferedWriter getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(BufferedWriter outputFile) {
		this.outputFile = outputFile;
	}

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}			
	
	@SuppressWarnings("unchecked")
	public void exportFile(String path, String filePassword, List<Long> licenses) {
		this.licenses = licenses;
		String filename = path; 
		if (!File.separator.equals("" + filename.charAt(filename.length()-1))){				
			filename += File.separator;
		}
		filename += JdbcBackup.BACKUP_FILE;
		
		String zipFilename = path; 
		if (!File.separator.equals("" + zipFilename.charAt(zipFilename.length()-1))){				
			zipFilename += File.separator;
		}
		zipFilename += JdbcBackup.BACKUP_FILE + ".zip";
		
		try {
			setOutputFile( new BufferedWriter(new FileWriter(filename)) );
			try{				
				 setClasses(PersistenceUtil.getAnnotatedClasses(getDatabase()));
				
				for (Class clazz : getClasses()) {					
					getOutputFile().write(getHeader(clazz) + "\n");
					exportClass(clazz);
				}
			} finally {				
				getOutputFile().close();
			}
			
			
			String md5FilePath = new File(filename).getParentFile().toString(); 
			if (!File.separator.equals("" + md5FilePath.charAt(md5FilePath.length()-1))){				
				md5FilePath += File.separator;
			}
			md5FilePath += JdbcBackup.MD5_FILE;
			BufferedWriter md5File = new BufferedWriter(new FileWriter(md5FilePath));
			try {
				md5File.write(JdbcBackup.getFileMd5(filename));
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				md5File.close();
			}
			
			ZipUtils.zip(zipFilename, new String[]{filename, md5FilePath});
			
			if (filePassword != null && !filePassword.equals("")){				
				DesEncrypter desEncrypter = new DesEncrypter(filePassword);
				desEncrypter.encrypt(zipFilename, zipFilename + ".secret");			
				FileUtils.forceDelete(new File(zipFilename));
				FileUtils.moveFile(new File(zipFilename + ".secret"), new File(zipFilename));
			}
			
			FileUtils.forceDelete(new File(filename));
			FileUtils.forceDelete(new File(md5FilePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void exportClass(Class clazz) {
		String dbTable = PersistenceUtil.getClassTableName(getDatabase(), clazz);
		if (ignoredTables.indexOf(dbTable) != -1){
			return;			
		}
		
		String whereLicense = licenses.size() > 0 ? " and licenseid in (" : "";
		
		for (int i = 0; i < licenses.size(); i++) {			
			whereLicense += licenses.get(i).toString() + (i == licenses.size() - 1 ? ")" : ",");
		}		
		
		Field fld = null;
		try{			
			fld = ReflectUtil.getDeclaredField(clazz, "licenseId");
		} catch(Exception e) {			
		}
		
		if (fld == null && !whereLicense.equals("")){
			return;
		}
		
		List list = new ArrayList();
		int offset = 0;

		try {
			String classId = "";
			
			try{				
				classId = BaseEntity.getClassIdentifier(clazz);
			} catch(Exception e) {
				//não tem classId
			}
			
			Session session = PersistenceUtil.getSession(getDatabase());			
			Query query = null;			
			
			if (classId != null && !classId.equals("")){
				query = session.createQuery("from " + clazz.getName()
											+ " where classId = '"
											+ classId + "' "  
											+ (getNegativeKeysOnly() ? " and id < 0 " : "")
											+ whereLicense
											+ " order by id");
			} else {
				if (getNegativeKeysOnly()){										
					query = session.createQuery("from " + clazz.getName() + " where id < 0 " + whereLicense + " order by id");
				} else {
					query = session.createQuery("from " + clazz.getName() + (!whereLicense.equals("") ? "where " + whereLicense : "") + " order by id");
				}
			}
			
			while(true){				
				query.setFirstResult(offset);
				query.setMaxResults(JdbcBackup.QTD_REGISTROS);			
				list = query.list();
				
				if (list.size() <= 0){
					break;
				}
				
				for (Object obj : list) {
					getOutputFile().write(getContent(obj) + JdbcBackup.END_LINE);
				}
				offset += JdbcBackup.QTD_REGISTROS;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	private String getContent(Object obj) {
				
		try {
			List<String> props = getSortedProperties(obj.getClass());
			String ret = "";
			
			for (String name : props) {			
				Object value = ReflectUtil.getFieldValue(obj, name);
				String strValue = (value == null ? JdbcBackup.NULL_VALUE : null);
								
				Field field = ReflectUtil.getDeclaredField(obj.getClass(), name);
				FieldType fieldType = ObjectConverter.getInstance().getDataModelType(field);
				
				if (value != null){				
					if (fieldType.equals(FieldType.DATE) || 
						fieldType.equals(FieldType.TIME) || 
						fieldType.equals(FieldType.TIMESTAMP)){
						
						strValue = "" + ((Date)value).getTime();
					} else if (fieldType.equals(FieldType.CLASS)) {
						strValue = ReflectUtil.getFieldValue(value, "id").toString();
					} else if (fieldType.equals(FieldType.DOUBLE)) {						
						strValue = value.toString();
					} else if (fieldType.equals(FieldType.ARRAY)) {
						strValue = ArrayUtils.toString(value);
					} else {	
						strValue = value.toString();
					}
				}
				
				ret += strValue.length() + JdbcBackup.FIELD_SEPARATOR + strValue; 
			}
			
			return ret.length() + JdbcBackup.FIELD_SEPARATOR + ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private String getHeader(Class clazz) {		
		List<String> props = getSortedProperties(clazz);
		String header = "";
		
		for (String name : props) {
			header += "," + name;
		}
		
		header = JdbcBackup.CLASS_DEF + clazz.getName() + "," + header.substring(1);		
		return header.length() + JdbcBackup.FIELD_SEPARATOR + header;
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getSortedProperties(Class clazz) {		 		
		List<Field> props = ReflectUtil.getClassFields(clazz, true);
		List<String> ret = new ArrayList<String>();
		
		for (Field field : props) {
			if (!Modifier.isTransient(field.getModifiers()) && 
				!Modifier.isStatic(field.getModifiers())){
				
				 if (!ObjectConverter.getInstance().getDataModelType(field).equals(FieldType.DETAIL)){					 					 
					 ret.add(field.getName());
				 }
			}
		}
		
		Collections.sort(ret);
		return ret; 
	}			

}
