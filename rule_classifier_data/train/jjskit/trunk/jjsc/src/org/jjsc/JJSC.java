package org.jjsc;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.jjsc.utils.AssertUtils;
import org.jjsc.utils.Log;
import org.jjsc.utils.Name;

/**
 * Entry-point class for the JJS compiler. Provides utility for the 
 * modules compilation and stores result in the target folder.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public final class JJSC {
	private static final String JS_FILE_EXT = ".js";
	private static final String HELP_PARAM = "--help";
	private static final String TARGET_DIR_PARAM = "--target";
	private static final String TARGET_FILE_PARAM = "--target-file";
	private static final String LOG_LEVEL_PARAM = "--log-level";
	private static final String USER_DIR_SYSTEM_PROPERTY = "user.dir";
	
	private static final Set<String> ALLOWED_PARAMS = new HashSet<String>(
			Arrays.asList(HELP_PARAM, TARGET_DIR_PARAM, LOG_LEVEL_PARAM, TARGET_FILE_PARAM));
	
	/**
	 * Command line entry point.
	 * @param args
	 * @throws JJSException in case of troubles
	 */
	public static void main(String[] args) throws JJSException{
		if(args.length==0||HELP_PARAM.equals(args[0])){
			printUsage();
			return;
		}
		ListIterator<String> params = Arrays.asList(args).listIterator();
		Map<String,String> optionsMap = new HashMap<String, String>();
		while (params.hasNext()) {
			String param = params.next();
			if (!ALLOWED_PARAMS.contains(param)) {
				params.previous();
				break;
			}
			optionsMap.put(param, params.hasNext() ? params.next() : null);
		}
		if(optionsMap.containsKey(HELP_PARAM)){
			printUsage();
			return;
		}
		compile(Collections.unmodifiableMap(optionsMap), params);
	}

	private static void compile(Map<String,String> optionsMap, ListIterator<String> modulesIterator)
			throws JJSException {
		if (optionsMap.get(LOG_LEVEL_PARAM) != null) {
			Log.setDefaultLevel(optionsMap.get(LOG_LEVEL_PARAM));
		}
		compile(getTargetDir(optionsMap), optionsMap.get(TARGET_FILE_PARAM), getModules(modulesIterator));
	}
	
	private static void printUsage() {
		PrintStream out = System.out;
		out.println("Usage:");
		out.println();
		out.println("java -jar jjsc.jar ["+TARGET_DIR_PARAM+" target] ["+LOG_LEVEL_PARAM+" log level] [modules list]");
		out.println();
		out.println("Note that at least one module is required to launch this tool.");
		out.println("Options are:");
		out.println("  "+TARGET_DIR_PARAM+" - the path to the output directory " +
				"where compilation result will be stored (may be relative to current directory).");
		out.println();
	}

	private static File getTargetDir(Map<String,String> optionsMap) {
		if(optionsMap.get(TARGET_DIR_PARAM) != null){
			return new File(optionsMap.get(TARGET_DIR_PARAM));
		}
		return new File(System.getProperty(USER_DIR_SYSTEM_PROPERTY));
	}

	private static List<String> getModules(Iterator<String> modulesIterator) {
		List<String> modules = new LinkedList<String>();
		while(modulesIterator.hasNext()){
			modules.add(modulesIterator.next());
		}
		return modules;
	}
	/**
	 * 
	 * Compiles the modules passed to the target directory.
	 * @param targetDir
	 * @param modules
	 * @throws JJSException
	 */
	public static void compile(File targetDir, String...modules) throws JJSException{
		compile(targetDir, null, Arrays.asList(modules));
	}
	/**
	 * Compiles the modules passed to the target directory.
	 * @param targetDir
	 * @param fileName 
	 * @param modules
	 * @throws JJSException
	 */
	public static void compile(File targetDir, String fileName, List<String> modules) throws JJSException {
		AssertUtils.assertNotEmpty(modules, "At least one module should be specified");
		if(!targetDir.isDirectory()){
			throw new ConfigurationException("Wrong working directory: "+targetDir);
		}
		JJSVM.launch(null, modules);
		Set<String> binaries = new HashSet<String>();
		for(Module module : JJSVM.get().getModules()){
			module.compile(getOutputFileForModule(targetDir,fileName,module,binaries));
		}
	}

	private static File getOutputFileForModule(File targetDir, String fileName, Module module, Set<String> binaries) {
		return new File(targetDir,ensureUnique(binaries, fileName == null ? module.getName() : fileName));
	}

	private static String ensureUnique(Set<String> binaries, String fileName) {
		String name = fileName.replace(Name.PACKAGE_SEPARATOR, '_');
		while(binaries.contains(name)){
			name = fileName+System.currentTimeMillis();
		}
		binaries.add(name);
		return name+JS_FILE_EXT;
	}
}
