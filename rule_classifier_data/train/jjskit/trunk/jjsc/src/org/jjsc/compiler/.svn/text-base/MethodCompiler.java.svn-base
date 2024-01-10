package org.jjsc.compiler;

import java.lang.reflect.Modifier;

import org.jjsc.compiler.namespace.InstanceMethodNamespace;
import org.jjsc.compiler.namespace.LocalNamespace;
import org.objectweb.asm.MethodAdapter;
/**
 * Compiler implementation for methods. Delegates compilation to other classes.
 * 
 * {@link StringBuilder} is used as buffer for compilation output since this is fastest 
 * implementation (and this class doesn't need to be synchronized).
 * 
 * @see ExceptionFlowGraphBuilder
 * @see OpcodesGraphBuilder 
 * @author alex.bereznevatiy@gmail.com
 */
final class MethodCompiler extends MethodAdapter {
	static final String CONSTRUCTOR_METHOD_NAME = "<init>";
	static final String STATIC_BLOCK_METHOD_NAME = "<clinit>";
	
	private String name;
	private String signature;
	private StringBuilder compilationBuffer;
	private boolean compiled = false;

	MethodCompiler(int access, String name, String signature, 
			StringBuilder compilationBuffer, Namespace namespace) {
		super(new ExceptionFlowGraphBuilder(getLocalNamespace(
				namespace, signature, access), compilationBuffer));
		this.name = name;
		this.signature = signature;
		this.compilationBuffer = compilationBuffer;
	}

	private static LocalNamespace getLocalNamespace(
			Namespace parent, String signature, int access) {
		if(Modifier.isStatic(access)) {
			return new LocalNamespace(signature, parent);
		}
		return new InstanceMethodNamespace(signature, parent);
	}

	public void visitCode() {
		if (!name.equals(STATIC_BLOCK_METHOD_NAME)) {
			compilationBuffer.append("\tthis['");
			compilationBuffer.append(name);
			compilationBuffer.append(signature, 0, signature.lastIndexOf(')')+1);
			compilationBuffer.append("']=");
		} else {
			compilationBuffer.append('(');
		}
		compilationBuffer.append("function(){\n");
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		compilationBuffer.append("\t}");
		if (name.equals(STATIC_BLOCK_METHOD_NAME)) {
			compilationBuffer.append(")()");
		}
		compilationBuffer.append(";\n\n");
		compiled = true;
	}
	/**
	 * @return current opcodes graph builder (for testcases).
	 */
	OpcodesGraphBuilder getCurrentBuilder() {
		if (mv instanceof OpcodesGraphBuilder){
			return (OpcodesGraphBuilder)mv;
		}
		if (mv instanceof ExceptionFlowGraphBuilder) {
			return ((ExceptionFlowGraphBuilder)mv).getBuilder();
		}
		return null;
	}
	
	StringBuilder getCompilationBuffer() {
		return compilationBuffer;
	}

	boolean isCompiled() {
		return compiled;
	}

	String getName() {
		return name;
	}

	int getLine() {
		if (compiled) {
			return -1;
		}
		try {
			return ((ExceptionFlowGraphBuilder)mv).getLineNumber();
		}
		catch (Throwable th) {
			return -1;
		}
	}
}