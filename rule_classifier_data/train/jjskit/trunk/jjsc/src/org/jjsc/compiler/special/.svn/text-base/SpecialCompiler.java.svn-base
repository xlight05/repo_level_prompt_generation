package org.jjsc.compiler.special;

import org.jjsc.compiler.namespace.JavaTypesNamespace;
import org.objectweb.asm.MethodVisitor;
/**
 * Provides functionality to compile some class in unique way.
 * This is useful to compile some classes that are not usual ones 
 * (e.g. root of objects hierarhy - {@link Object}).
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public interface SpecialCompiler {
	/**
	 * Writes class header information to output.
	 * @param name of class
	 * @param access flags
	 * @param name of superclass
	 * @param namespace to resolve types
	 * @param output to write to
	 */
	void visit(String name, int access, String superName,
			JavaTypesNamespace namespace, StringBuilder output);
	/**
	 * Writes class's method to output. 
	 * @param name of method
	 * @param access flags
	 * @param description of method
	 * @param output to write to
	 * @return MethodVisitor to process the method or <code>null</code> to not process it.
	 */
	MethodVisitor visitMethod(String name, int access, String desc, StringBuilder output);
	/**
	 * Writes class footer, concludes class definition.
	 * @param output
	 */
	void visitEnd(StringBuilder output);
}
