package org.jjsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarFile;

import javax.servlet.Servlet;

import org.jjsc.modules.FileSystemModule;
import org.jjsc.modules.JarModule;
import org.jjsc.modules.OSGiModule;
import org.jjsc.utils.AssertUtils;
import org.jjsc.utils.FileSystem;
import org.osgi.framework.Bundle;
/**
 * Represents virtual machine of JJSC. Provides automatic modules loading, compilation
 * debugging and configuration. This is also command line entry point for the compiler.
 * As main class contains methods to parse and validate command line parameters.
 * 
 * The usage of this class can be got from #printUsage() method.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public final class JJSVM {
	public static final String VERSION = "0.0.1alfa";
	
	private static final String HELP_PARAM = "--help";
	private static final String SHUTDOWN_COMMAND = "shutdown";

	private static final String USER_DIR_SYSTEM_PROPERTY = "user.dir";

	private static final String JAR_EXTENSION = ".jar";
	
	public static final String PRECOMPILE_PROPERTY = "-precompile";
	public static final String USE_CACHE_PROPERTY = "-useCache";
	public static final String MODULES_PROPERTY = "-modules";
	private static final String WORKING_DIR_PROPERTY = "-workDir";
	
	private static JJSVM instance;
	/**
	 * Entry point for command line. Parses input parameters and than delegates 
	 * execution to {@link #launch(Properties, List)} method.
	 * @param args
	 * @throws LoadModuleException in case if some of the modules cannot be loaded
	 * @throws ConfigurationException in case of wrong configuration
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) 
			throws LoadModuleException, ConfigurationException {
		if(args.length==0||args[0].equals(HELP_PARAM)){
			printUsage();
			return;
		}
		launch(Arrays.asList(args).listIterator());
		waitForShutdown();
	}
	
	private static void waitForShutdown() {
		System.out.println("JJS Virtual Machine has been launched successfully. " +
				"Please type 'shutdown' to shut it down.");
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			while(!SHUTDOWN_COMMAND.equals(br.readLine()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * <p>Prints usage of the command line. The result looks like following:</p>
	 * <br/>
	 * <p>Usage:</p>
	 * <p><code>java -cp jjsc.jar org.jjsc.JJSVM [options] -modules [list of modules locations]</code></p>
	 * <p>At least one module should be specified when launching from command line.</p>
	 * <p>Options allowed are:</p>
	 * <p><i>-precompile</i> - if specified compiles all modules just after loading. 
	 * Otherwise compile modules only on first load (lazy).</p>
	 * <p><i>-useCache</i> - if specified uses cache of compilation and compile modules 
	 * only once. By default false and compile modules on each call. It is very useful 
	 * for the development but hard for performance.</p>
	 * <p><i>-workDir</i> - overrides the working directory of the virtual machine. By 
	 * default its current java working directory.</p>
	 */
	private static void printUsage() {
		PrintStream out = System.out;
		out.println("Usage:");
		out.println();
		out.println("java -cp jjsc.jar org.jjsc.JJSVM [options] "+MODULES_PROPERTY+
				" [list of modules locations]");
		out.println();
		out.println("Options allowed are:");
		out.println("  "+PRECOMPILE_PROPERTY+
			" - if specified compiles all modules just after loading. " + 
			"Otherwise compile modules only on first load (lazy).");
		out.println("  "+USE_CACHE_PROPERTY+
			" - if specified uses cache of compilation and compile modules " + 
			"only once. By default false and compile modules on each call. It is very useful " +
			"for the development but hard for performance.");
		out.println("  "+WORKING_DIR_PROPERTY+
			" - overrides the working directory of the virtual machine. By " + 
			"default its current java working directory.");
		out.println();
	}

	private static void launch(ListIterator<String> argsIterator)
			throws LoadModuleException, ConfigurationException {
		Properties params = new Properties();
		List<String> modules = new ArrayList<String>();
		while(argsIterator.hasNext()){
			String name = argsIterator.next();
			if(name.equals(MODULES_PROPERTY)){
				modules.addAll(getModules(argsIterator));
				return;
			}
			addProperty(name, params, argsIterator);
		}
		AssertUtils.assertNotEmpty(modules,
			"At least one module should be specified when launching from command line.");
		launch(params, modules);
	}

	private static void addProperty(String name, Properties params,
			ListIterator<String> argsIterator) {
		int eqIndex = name.indexOf('=');
		String value = Boolean.TRUE.toString();
		if(eqIndex >= 0){
			value = name.substring(eqIndex+1);
			name = name.substring(0,eqIndex);
		}
		params.setProperty(name, value);
	}

	private static List<String> getModules(ListIterator<String> argsIterator) {
		List<String> result = new ArrayList<String>();
		while(argsIterator.hasNext()){
			result.add(argsIterator.next());
		}
		return result;
	}
	/**
	 * Launches JJS virtual machine with properties passed and modules specified.
	 * Both parameters can be <code>null</code> for this method and you may (or may not)
	 * configure some them from code using setters.
	 * @param params
	 * @param modules
	 * @throws LoadModuleException in case of wrong module passed
	 * @throws ConfigurationException in case of invalid configuration parameter
	 */
	public static void launch(Properties params, List<String> modules) 
			throws LoadModuleException, ConfigurationException{
		JJSVM vm = new JJSVM();//to avoid synchronizing
		if(params == null){
			params = new Properties();
		}
		vm.configure(params);
		vm.modules.putAll(findModules(modules, vm.workDir));
		vm.launch();
		instance = vm;
	}
	
	private void configure(Properties params) throws ConfigurationException{
		synchronized(params){
			precompile = Boolean.parseBoolean(
					params.getProperty(PRECOMPILE_PROPERTY));
			useCache = Boolean.parseBoolean(
					params.getProperty(USE_CACHE_PROPERTY));
			workDir = findWorkDir(params);
		}
	}

	private static File findWorkDir(Properties params) throws ConfigurationException {
		String dir = params.getProperty(WORKING_DIR_PROPERTY, 
				System.getProperty(USER_DIR_SYSTEM_PROPERTY));
		File result = new File(dir);
		if(!result.isDirectory()){
			throw new ConfigurationException("Wrong working directory: "+result);
		}
		return result;
	}

	private static Map<String, Module> findModules(List<String> modules,
			File workDir) throws LoadModuleException {
		Map<String, Module> result = new HashMap<String, Module>();
		if(modules==null){
			return result;
		}
		for(String module : modules){
			Module m = getModuleForLocation(findLocation(module, workDir));
			result.put(m.getName(), m);
		}
		return result;
	}

	private static File findLocation(String modulePath, File workDir) {
		File loc = new File(modulePath);
		if(loc.exists()){
			return loc;
		}
		return new File(workDir, modulePath);
	}
	/**
	 * @return <i>singleton</i> instance of virtual machine (never <code>null</code>).
	 * @throws NotStartedException in case if virtual machine is not started yet.
	 */
	public static JJSVM get() throws NotStartedException {
		if(instance==null){
			throw new NotStartedException("VM is not started. Consider to calling " +
					"launch(Properties, List<String>) method before calling this one.");
		}
		return instance;
	}
	
	private boolean useCache;
	private boolean precompile;
	private File workDir;
	private File cacheDir;
	private Map<String,Module> modules;
	
	private JJSVM(){
		modules = new HashMap<String, Module>();
	}
	
	private void launch() throws NotStartedException, LoadModuleException {
		if(!precompile)return;
		for(Module m : modules.values()){
			m.compile();
		}
	}
	/**
	 * Adds module for the location specified. It will be automatically converted 
	 * to existing module implementation (e.g. FileSystemModule).
	 * @param location
	 * @throws LoadModuleException in case of loading problems
	 */
	public void addModule(File location) throws LoadModuleException {
		addModule(getModuleForLocation(location));
	}
	
	private static Module getModuleForLocation(File location) throws LoadModuleException {
		if(!location.exists()){
			throw new ModuleNotFoundException(
				"Unable to find module at location "+location);
		}
		if(location.isDirectory()){
			return new FileSystemModule(location);
		}
		if(!location.getName().endsWith(JAR_EXTENSION)){
			throw new ModuleNotFoundException(
				"Unable to discover module type - module should be either" +
				" jar or exitent dirrectory. Please check "+location);
		}
		try {
			return getJarModule(new JarFile(location));
		}
		catch(IOException ex){
			throw new LoadModuleException(ex);
		}
	}

	private static Module getJarModule(JarFile jarFile) {
		return new JarModule(jarFile);
	}
	/**
	 * Add jar module for the jar file specified.
	 * @param jarFile
	 * @throws LoadModuleException in case of loading problems
	 */
	public void addModule(JarFile jarFile) throws LoadModuleException {
		addModule(getJarModule(jarFile));
	}
	/**
	 * Adds module for OSGi bundle passed. All loading operations 
	 * will be delegated to the bundle so some OSGi specific exceptions may occur.  
	 * @param osgiBundle
	 * @throws LoadModuleException
	 */
	public void addModule(Bundle osgiBundle) throws LoadModuleException {
		addModule(new OSGiModule(osgiBundle));
	}
	/**
	 * Adds any type custom module. See the {@link Module} class for the 
	 * details about specific module implementation. 
	 * @param module
	 * @throws LoadModuleException in case of loading problems
	 */
	public void addModule(Module module) throws LoadModuleException {
		AssertUtils.assertNotNull(module, "Module is NULL");
		String name = module.getName();
		if(modules.get(name)!=null){
			throw new LoadModuleException(
				"Module with name ["+name+
				"] is already registered");
		}
		modules.put(name, module);
		if(precompile){
			try {
				module.compile();
			}
			catch(NotStartedException ex){
				throw new LoadModuleException(ex);
			}
		}
	}
	/**
	 * @param moduleName
	 * @return resource {@link Servlet} to register for module with specific name.
	 * @throws ModuleNotFoundException in case if there are no module with name specified.
	 */
	public Servlet getResourceServlet(String moduleName) 
			throws ModuleNotFoundException {
		return getModule(moduleName).getResourceServlet();
	}
	/**
	 * @param moduleName
	 * @return module with name specified.
	 * @throws ModuleNotFoundException in case if there are no module with such a name.
	 */
	public Module getModule(String moduleName) throws ModuleNotFoundException {
		Module mod = modules.get(moduleName);
		if(mod==null){
			throw new ModuleNotFoundException(
				"Unable to find module with name: "+moduleName);
		}
		return mod;
	}
	/**
	 * @return list of all the modules registered for this VM.
	 */
	public List<Module> getModules() {
		return Collections.unmodifiableList(
			new ArrayList<Module>(modules.values()));
	}
	/**
	 * @return <code>true</code> if this virtual machine caches compilation results.
	 * Otherwise returns <code>false</code>.
	 */
	public boolean isUseCache() {
		return useCache;
	}
	/**
	 * This method is used to allow or disallow the caching for this VM.
	 * @param useCache
	 */
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	/**
	 * @return working directory for thsi VM (never <code>null</code>).
	 */
	public File getWorkDir() {
		return workDir;
	}
	/**
	 * @return cache directory for this VM (never <code>null</code>).
	 */
	public File getCacheDir() {
		if(cacheDir==null){
			cacheDir = new File(workDir,"cache");
			cacheDir.mkdirs();
			FileSystem.markForDeletion(cacheDir);
		}
		return cacheDir;
	}
	/**
	 * Shutdown this VM.
	 */
	public synchronized void shutdown(){
		instance = null;
	}
}
