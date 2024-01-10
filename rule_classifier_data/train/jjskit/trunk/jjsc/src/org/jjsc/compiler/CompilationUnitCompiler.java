package org.jjsc.compiler;

import java.io.Writer;
import java.net.URL;

import org.jjsc.utils.AssertUtils;
import org.objectweb.asm.ClassReader;
/**
 * <p>This class is entry point for the JJS compiler. 
 * Represents compilation of the single compilation unit.
 * Delegates work to other part of the compilation API.</p>
 * 
 * <p>Also this class can be used by concurrency API (java.util.concurrent package) as
 * atomic task to execute.</p>
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public final class CompilationUnitCompiler implements Runnable {
	private URL compilationUnit;
	private Writer resultWriter;
	private  CompilationErrorHandler errorHandler;
	/**
	 * Creates compiler for the compilation unit with url passed that 
	 * writes result to the resultWriter passed and handles exceptions with
	 * error handler from the third parameter.
	 * @param compilationUnit
	 * @param resultWriter
	 * @param errorHandler
	 */
	public CompilationUnitCompiler( 
			URL compilationUnit, Writer resultWriter, 
			CompilationErrorHandler errorHandler) {
		AssertUtils.assertNotNull(compilationUnit, "Compilation unit is NULL");
		AssertUtils.assertNotNull(resultWriter, "Result writer is NULL");
		AssertUtils.assertNotNull(errorHandler, "Error handler is NULL");
		this.compilationUnit = compilationUnit;
		this.resultWriter = resultWriter;
		this.errorHandler = errorHandler;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		ClassCompiler compiler = null;
		try {
			compiler = new ClassCompiler(null,resultWriter);
			ClassReader clazz = new ClassReader(compilationUnit.openStream());
			clazz.accept(compiler, 0);
		}
		catch(CompilationError error){
			if (compiler != null) {
				error.setTarget(compiler.getClassName(), compiler.getMethodName(), 
						compiler.getFileName(), compiler.getLineNumber());
			}
			errorHandler.onCompilationError(error);
		}
		catch(Throwable error){
			errorHandler.onError(error);
		}
	}
}
