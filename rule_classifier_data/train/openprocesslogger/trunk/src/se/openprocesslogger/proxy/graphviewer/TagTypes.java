package se.openprocesslogger.proxy.graphviewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TagTypes {
	private static String configPath = "graphviewer_tagtypes.xml";
	private static Properties types = null;
	
	public static String getTagType(String varName){
		if(types == null) loadTagTypes();
		String s = types.getProperty(varName);
		return s;
	}

	public static String getConfigPath() {
		return configPath;
	}


	public static void setConfigPath(String configPath) {
		TagTypes.configPath = configPath;
		loadTagTypes();
	}
	
	private static void loadTagTypes() {
		types = new Properties();
		loadClassPath();
		loadFile();
		types.list(System.out);
	}
	
	private static void loadFile() {
		InputStream is = null;
		try {
			is = new FileInputStream(new File(configPath));
		} catch (FileNotFoundException e) {
			return;
		}
		if (is == null) return;
		try {
			types.loadFromXML(is);
		} catch (InvalidPropertiesFormatException e) {
		} catch (IOException e) {
		}
	}
	
	private static void loadClassPath() {
		InputStream is = null;
		try {
			is = (new TagTypes()).getClass().getClassLoader().getResourceAsStream(configPath);
		}catch(Exception e){
		}
		if (is == null) return;
		try {
			types.loadFromXML(is);
		} catch (InvalidPropertiesFormatException e) {
		} catch (IOException e) {
		}
	}
}
