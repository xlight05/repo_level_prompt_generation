package se.openprocesslogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class OplProperties {

	private OplProperties(){}
	
	/***
	 * These constants are in the installation script
	 */
	private static String configFileName = "OplConfig.xml";
	private static boolean isFile = false;
	
	private static final String dbPathName = "databasePath";
	private static final String templatePathName = "templatePath";
	private static final String odePathName = "odePath";
	private static final String templateFileName = "templateName";
	
	private static Properties prop;
	private static Logger l = Logger.getLogger(OplProperties.class);
	
	
	/***
	 * 
	 * @param name
	 * @param isFile true if file, false if classpath
	 */
	public static void setConfigFileName(String name, boolean isAbsolutePath){
		configFileName = name;
		isFile = isAbsolutePath;
		prop = null;
	}
	
	public static String getDbPath(){
		if(prop==null) loadProperties();
		String temp = prop.getProperty(dbPathName);
		return temp == null ? "" : temp;
	}
	
	public static String getTemplatePath(){
		if(prop==null) loadProperties();
		String temp = prop.getProperty(templatePathName);
		return temp == null ? "" : temp;
	}
	
	public static String getOdeName(){
		if(prop==null) loadProperties();
		String temp = prop.getProperty(odePathName);
		return temp == null ? "" : temp;
	}
	
	public static String getProperty(String property){
		if(prop==null) loadProperties();
		return prop.getProperty(property);
	}
	
	private static void loadProperties() {
		InputStream is = null;
		if(isFile){
			try {
				l.debug("Loading config file from file: "+configFileName);
				is = new FileInputStream(new File(configFileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				isFile = false;
				l.error("File not found. Searching on classpath");
			}
		}
		if(!isFile){
			l.debug("Loading config file from classpath: "+configFileName);
			is = (new OplProperties()).getClass().getClassLoader().getResourceAsStream(configFileName);
		}
		if(is!=null){
			try {
				prop = new Properties();
				prop.loadFromXML(is);
				for(Entry<Object, Object> e : prop.entrySet()){
					l.debug(e.getKey()+":"+e.getValue());
				}
			} catch (InvalidPropertiesFormatException e) {
				l.error("Error reading properties: "+e.getMessage());
			} catch (IOException e) {
				l.error("IOException while reading configuration: "+e.getMessage());
			}			
		}else{
			l.error("NO CONFIG FILE");
		}
	}
	
	public static void main(String[] args){
		l.debug(OplProperties.getDbPath());
		l.debug(OplProperties.getTemplatePath());
	}

	public static String getTemplateFileName() {
		return getProperty(templateFileName);
	}
}
