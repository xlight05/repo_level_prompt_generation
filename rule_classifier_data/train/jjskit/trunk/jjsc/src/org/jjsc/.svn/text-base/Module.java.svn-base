package org.jjsc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jjsc.compiler.CompilationError;
import org.jjsc.compiler.CompilationErrorHandler;
import org.jjsc.compiler.CompilationUnitCompiler;
import org.jjsc.compiler.javascript.JavaScriptLang;
import org.jjsc.utils.FileSystem;
import org.jjsc.utils.IO;
import org.jjsc.utils.Log;
/**
 * This class instance encapsulates single module (that will be compiled in single .js file).
 * Implementors should only take care about implementing abstract methods.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public abstract class Module {
	protected static final String CLASS_FILE_EXT = ".class";
	
	private File cacheFile;
	private CompilationWorker compilationTask = new CompilationWorker();
	/**
	 * @return servlet to load compilation result of this module though web.
	 */
	public final Servlet getResourceServlet() {
		return new ResourceServlet();
	}
	/**
	 * Requests module compilation. If compilation is already in process - simply returns.
	 * @throws NotStartedException in case if VM is shutdown.
	 * @throws LoadModuleException in case of loading or compilation module failure.
	 */
	public final void compile() throws NotStartedException, LoadModuleException {
		if(!compilationTask.isStarted()){
			compilationTask.startCompilation();
		}
	}
	/**
	 * Compiles this module and stores result in the file passed.
	 * @param output file to store result
	 * @throws NotStartedException in case if VM is shutdown.
	 * @throws LoadModuleException in case of loading or compilation module failure.
	 */
	public final void compile(File output) throws NotStartedException, LoadModuleException {
		try {
			compile();
			compilationTask.waitForResult();
			FileSystem.write(output, compilationTask.getResult());
		}
		catch(IOException ex){
			throw new LoadModuleException("Unable to store compilation result",ex);
		}
	}
	/**
	 * @return unque name for this module.
	 */
	public abstract String getName();
	/**
	 * @return list of {@link URL}s to all classes of this module to compile.
	 * @throws LoadModuleException
	 */
	protected abstract List<URL> getAllCompilationUnits() throws LoadModuleException;

	private InputStream getCompiledResult() 
			throws NotStartedException, LoadModuleException {
		if(JJSVM.get().isUseCache()&&
			cacheFile!=null&&cacheFile.exists()){
			return getFromCache();
		}
		return compiledResult();
		
	}

	private InputStream compiledResult() throws NotStartedException,
			LoadModuleException {
		boolean startCompilation = !compilationTask.isStarted();
		if(startCompilation){
			compilationTask.startCompilation();
		}
		compilationTask.waitForResult();
		if(startCompilation&&JJSVM.get().isUseCache()){
			cacheFile = compilationTask.createCache();
			return getFromCache();
		}
		return compilationTask.getResult();
	}

	private InputStream getFromCache() throws LoadModuleException {
		try {
			return new FileInputStream(cacheFile);
		}
		catch(IOException ex){
			throw new LoadModuleException(ex);
		}
	}
	/**
	 * This class can be used to access the compilation result through web.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private class ResourceServlet extends HttpServlet {
		private static final long serialVersionUID = 3649263679185569793L;
		private static final String CONTENT_MIME_TYPE = "text/javascript";

		public void service(HttpServletRequest request, HttpServletResponse responce)
				throws ServletException, IOException {
			try {
				responce.setContentType(CONTENT_MIME_TYPE);
				IO.flow(getCompiledResult(), responce.getOutputStream());
			}
			catch(JJSException ex){
				throw new ServletException(ex);
			}
		}
	}
	/**
	 * This worker controls compilation process and concurrency.
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private class CompilationWorker extends Thread implements CompilationErrorHandler {
		private boolean working = false;
		private File result;
		private List<Throwable> errors;
		
		void startCompilation() throws LoadModuleException{
			if(isStarted()){
				throw new LoadModuleException(
					getClass().getName()+" is already started");
			}
			working = true;
			start();
		}
		
		File createCache() {
			return result;
		}

		synchronized void waitForResult() {
			try {
				wait();
			}
			catch(InterruptedException ex){}
		}

		boolean isStarted(){
			return working;
		}

		InputStream getResult() throws LoadModuleException {
			if(errors!=null&&!errors.isEmpty()){
				throw new CompilationModuleException(errors);
			}
			Log.info("Compilation successfull (see cache "+result.getAbsolutePath()+')');
			try {
				return new FileInputStream(result);
			} 
			catch (FileNotFoundException e) {
				throw new LoadModuleException(e);
			}
		}

		@Override
		public void run() {
			try {
				compile(getAllCompilationUnits(), getResultTempFile());
			}
			catch(RuntimeException ex){
				throw ex;
			}
			catch(Exception ex){
				throw new RuntimeException(ex);
			}
		}

		private void compile(List<URL> cUnits, File result)
				throws IOException, InterruptedException {
			FileWriter resultWriter = new FileWriter(result);
			try {
				errors = Collections.synchronizedList(new LinkedList<Throwable>());
				compile(cUnits, resultWriter);
				this.result = result;
				this.working = false;
			}
			catch(Throwable th){
				errors.add(th);
			}
			finally{
				result.deleteOnExit();
				resultWriter.close();
			}
			synchronized(this){
				notifyAll();
			}
		}

		private void compile(List<URL> cUnits, FileWriter resultWriter)
				throws IOException, InterruptedException {
			ThreadPoolExecutor threadPool = getExecutor();
			resultWriter.write(JavaScriptLang.registerClassLoader());
			resultWriter.write("{\n");
			for(URL unit : cUnits){
				threadPool.execute(
					new CompilationUnitCompiler(unit,resultWriter,this));
			}
			threadPool.shutdown();
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
			resultWriter.write("'':JJS.emptyFn()\n});");
		}
		
		private ThreadPoolExecutor getExecutor(){
			return new ThreadPoolExecutor(1,10,60,TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory());
		}

		private File getResultTempFile() throws NotStartedException, IOException {
			return File.createTempFile("compilation", 
				String.valueOf(System.currentTimeMillis()), 
				JJSVM.get().getCacheDir());
		}

		public void onCompilationError(CompilationError error) {
			errors.add(error);
		}

		public void onError(Throwable error) {
			Log.error(error);
			errors.add(error);
		}
	}
}
