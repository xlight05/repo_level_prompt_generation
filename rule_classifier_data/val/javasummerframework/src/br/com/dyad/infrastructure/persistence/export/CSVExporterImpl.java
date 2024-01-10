package br.com.dyad.infrastructure.persistence.export;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.KeyGenerator;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class CSVExporterImpl implements CSVExporter{
	String database;
	String[] columns;
	String[] columnLabels;
	Integer maxRecordCount;
	Boolean fillFirstLineWithHeader = true;
	
	public CSVExporterImpl(String database) {
		this.database = database;
	}
	
	public CSVExporterImpl(String database, String[] columns) {
		this.database = database;
		this.columns = columns;
	}
	
	public CSVExporterImpl(String database, String[] columns, String[] columnLabels) {
		this(database,columns);
		this.columnLabels = columnLabels;
	}
	
	public CSVExporterImpl(String database, String[] columns, String[] columnLabels, Integer maxRecordCount ) {
		this(database,columns,columnLabels);
		this.maxRecordCount = maxRecordCount;
	}

	@Override
	public Object export(List list) {		
		String lines = "";		
		lines += configureHeader(list);
				
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if ( columns != null ){
				for (int i = 0; i < columns.length; i++) {
					String separator = i == columns.length - 1 ? "\n" : ";";
					Object fieldValue = ReflectUtil.getFieldValue(object, columns[i]);
					lines += getStringFieldValue(fieldValue) + separator;
				}			
			} else {
				if ( list.size() > 0 ){
					List<String> classProperties = ReflectUtil.getClassProperties(list.get(0).getClass(), true);
					for (Iterator props = classProperties.iterator(); props.hasNext();) {
						String propName = (String) props.next();
						String separator = props.hasNext() ? ";" : "\n";
						Object fieldValue = ReflectUtil.getFieldValue(object, propName);
						lines += getStringFieldValue(fieldValue) + separator;
					}
				}
			}
		}
		
		System.out.println(lines);
		
		try {
			Long fileKey = KeyGenerator.getInstance(database).generateKey(KeyGenerator.KEY_RANGE_NO_LICENSE);
			File tempDir = new File(SystemInfo.getInstance().getTempDir() + File.separator + database + File.separator + "downloads");
			tempDir.mkdir();
			File targetFile = new File(tempDir.getPath() + File.separator + fileKey + ".csv");
			byte[] data = lines.getBytes();
			FileUtils.writeByteArrayToFile(targetFile, data);
			System.out.println(targetFile.getAbsolutePath());
			return targetFile.getAbsolutePath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private String getStringFieldValue(Object fieldValue) {
		String value = "";
		if ( fieldValue == null ){
			return value;
		}
		if ( fieldValue instanceof BaseEntity ){
			value = getToStringBean((BaseEntity)fieldValue);
		} else if ( fieldValue instanceof String ){
			value = (String) fieldValue;
		} else if ( fieldValue instanceof Integer ){
			value = fieldValue.toString();
		} else if ( fieldValue instanceof Long ){
			value = fieldValue.toString();
		} else if ( fieldValue instanceof Date ){
			SimpleDateFormat spf = new SimpleDateFormat("dd/mm/yyyy");
			value = spf.format(fieldValue);
		} else {
			value = fieldValue.toString();
		}
		return value;
	}

	private String configureHeader(List list) {
		String lines = "";
		if ( columns == null || columns.length == 0 ){
			if ( list.size() > 0 ){
				List<String> classProperties = ReflectUtil.getClassProperties(list.get(0).getClass(), true);
				for (Iterator props = classProperties.iterator(); props.hasNext();) {
					String propName = (String) props.next();
					String separator = props.hasNext() ? ";" : "\n";
					lines += propName + separator;
				}
			}
			
		} else {
			for (int i = 0; i < columns.length; i++) {
				String separator = i == columns.length - 1 ? "\n" : ";"; 
				if ( columnLabels != null ){
					lines += columnLabels[i] + separator;
				} else {
					lines += columns[i] + separator;
				}
			}
		}	
		return lines;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String[] getColumnLabels() {
		return columnLabels;
	}

	public void setColumnLabels(String[] columnLabels) {
		this.columnLabels = columnLabels;
	}

	public Integer getMaxRecordCount() {
		return maxRecordCount;
	}

	public void setMaxRecordCount(Integer maxRecordCount) {
		this.maxRecordCount = maxRecordCount;
	}

	public Boolean getFillFirstLineWithHeader() {
		return fillFirstLineWithHeader;
	}

	public void setFillFirstLineWithHeader(Boolean fillFirstLineWithHeader) {
		this.fillFirstLineWithHeader = fillFirstLineWithHeader;
	}
	
	public String getToStringBean( BaseEntity bean ){
		return bean.toString();
	}
	
	/*public static void main(String[] args) throws Exception {
		DataExport de = new DataExport();
		Session session = PersistenceUtil.getSession("INFRA");
		Query q = session.createQuery("from User");
		List userList = q.list();
		de.setDataList(userList);
		de.toCSV(new CSVExporterImpl("INFRA", new String[]{"id", "login", "password"}, null, null));
	}*/
	
}
