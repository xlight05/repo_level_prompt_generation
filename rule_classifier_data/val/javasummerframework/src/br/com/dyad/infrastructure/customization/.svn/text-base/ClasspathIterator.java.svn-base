package br.com.dyad.infrastructure.customization;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import br.com.dyad.infrastructure.widget.WidgetListener;

/**
	*/
public class ClasspathIterator {	
	//Só inspeciona as classes de um projeto que contenha este arquivo
	private final String INSPECT_FILE = "dyad.inspect";
	private List<WidgetListener> listeners = new ArrayList<WidgetListener>();

	public void addListener(WidgetListener listener) {
		if (listener != null){			
			listeners.add(listener);
		}
	}
	
	private void notifyListeners(String clazz) throws Exception {
		for (WidgetListener listener : listeners) {
			listener.handleEvent(clazz);
		}
	}
	
	/**
	 * Iterate over the system classpath defined by "java.class.path" searching
	 * for all .class files available
	 * 
	 */
	public void listClasses() {
		String classpath = System.getProperty("java.class.path", "");
		
		if (classpath.equals("")) {
			throw new RuntimeException("error: classpath is not set");
		}
		
		try {
			StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);
			
			String dyadClassPath = System.getProperty("app.dyad.classpath");
			if (dyadClassPath != null && !dyadClassPath.trim().equals("")){
				loadClassesFromDir(new File(dyadClassPath));
			}
			
			while (st.hasMoreTokens()) {				
				String token = st.nextToken();
				File classpathElement = new File(token);
				
				if (classpathElement.isDirectory()){
					loadClassesFromDir(classpathElement);
				} else {
					loadClassesFromJar(classpathElement);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getClassDir(){
		String classpath = System.getProperty("java.class.path", "");
		
		if (classpath.equals("")) {
			throw new RuntimeException("error: classpath is not set");
		}
		
		try {
			StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);
			
			while (st.hasMoreTokens()) {				
				String token = st.nextToken();
				String[] items = StringUtils.split(token, File.separator);
				if (items != null && items.length > 0 && items[items.length - 1].equalsIgnoreCase("classes")){
					return token;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return ".";
	}

	//Pesquisa dentro das bibliotecas do sistema
	private void loadClassesFromJar(File jarFile) throws Exception {
		if (jarFile.getName().endsWith(".jar")) {
			Enumeration<JarEntry> fileNames;
			JarFile jar = new JarFile(jarFile);
			fileNames = jar.entries();
			JarEntry entry = null;
			
			if (jar.getEntry(INSPECT_FILE) != null){				
				while (fileNames.hasMoreElements()) {
					entry = fileNames.nextElement();
					if (entry.getName().endsWith(".class")) {					
						String className = entry.getName().replace('/', '.');
						className = className.replace(".class", "");
						
						notifyListeners(className);
					}
				}
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	//Pesquisa nos diretórios que estão no classpath
	private void loadClassesFromDir(File dir) {				
		File inspectFile = new File(dir.toString() + File.separator + ".." + File.separator + INSPECT_FILE);
		File inspectFile2 = new File(dir.toString() + File.separator + ".." + File.separator + "src" + File.separator + INSPECT_FILE);
		File inspectFile3 = new File(dir.toString() + File.separator + ".." + File.separator + ".." + File.separator + INSPECT_FILE);		
		
		if (inspectFile.exists() || inspectFile2.exists() || inspectFile3.exists()){			
			Collection<File> files = FileUtils.listFiles(dir, new String[]{"class"}, true);
			
			for (File item : files) {
				try{				
					String className = getClassNameFromFile(item);
					notifyListeners(className);
				} catch(Exception e) {		
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	private String getClassNameFromFile(File classFile) {
		String[] fullPath = StringUtils.split(classFile.toString(), File.separator);
		String name = "";
		boolean start = false;
		
		for (int i = 0; i < fullPath.length; i++) {
			if ((fullPath[i].equalsIgnoreCase("classes") && i == 0) || (fullPath[i].equalsIgnoreCase("classes") && fullPath[i-1].equalsIgnoreCase("WEB-INF"))){
				start = true;
			} else {				
				if (start){					
					String temp = "";
					if (i == fullPath.length - 1) {
						temp = fullPath[i].substring(0, fullPath[i].indexOf('.'));
					} else {
						temp = fullPath[i];
					}
					name = name + (name.length() == 0 ? "" : ".") + temp;
				}
			}			
		}
		
		return name;
	}
}