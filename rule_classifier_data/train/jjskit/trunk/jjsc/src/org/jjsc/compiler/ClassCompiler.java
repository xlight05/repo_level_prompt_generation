package org.jjsc.compiler;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.jjsc.compiler.javascript.JavaScriptLang;
import org.jjsc.compiler.namespace.JavaTypesNamespace;
import org.jjsc.compiler.special.CompilationUtilsManager;
import org.jjsc.compiler.special.SpecialCompiler;
import org.jjsc.utils.Log;
import org.jjsc.utils.Name;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
/**
 * Compiles single class to javascript code.
 * Current implementation uses prototypes inheritance model.
 * Access checking is performed using build-in API.
 * Fields compilation is delegated to {@link FieldCompiler} class.
 * Methods compilation is delegated to {@link MethodCompiler} class.
 * 
 * @author alex.bereznevatiy@gmail.com
 */
final class ClassCompiler implements ClassVisitor {
	private SpecialCompiler special;
	private JavaTypesNamespace namespace;
	private Writer resultWriter;
	private StringBuilder compilationBuffer;
	private String className;
	private MethodCompiler currentMethod;
	private String debugSrc;
	
	public ClassCompiler(Namespace namespace, Writer resultWriter) {
		this.resultWriter = resultWriter;
		this.compilationBuffer = new StringBuilder();
		this.namespace = new JavaTypesNamespace(namespace);
	}

	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		if (version!=Opcodes.V1_5 && version!=Opcodes.V1_6 && version!=Opcodes.V1_7) {
			throw new CompilationError("Unsupported version of class file. Please use java 5, 6 or 7 to compile the source");
		}
		className = Name.fromInternalName(name);
		special = CompilationUtilsManager.getSpecialCompiler(className);
		if (special != null) {
			special.visit(className, access, superName, namespace, compilationBuffer);
			return;
		}
		superName = superName == null ? null : namespace.resolve(superName);
		JavaScriptLang.packageDeclaration(
				Name.getParentJavaName(className),compilationBuffer);
		JavaScriptLang.extend(className, superName, compilationBuffer);
		compilationBuffer.append(",function(){\n");
	}

	public void visitSource(String source, String debug) {
		debugSrc = source;
	}

	public void visitOuterClass(String owner, String name, String desc) {}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	public void visitAttribute(Attribute attr) {}

	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {}

	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		return new FieldCompiler(access,name,desc,
				signature,value,compilationBuffer);
	}

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		if (Log.isDebug()) {
			Log.debug("compiling "+Modifier.toString(access)+' '+className+'.'+name+desc);
		}
		if (special != null) {
			return special.visitMethod(name, access, desc, compilationBuffer);
		}
		if (Modifier.isAbstract(access) || Modifier.isNative(access)) {
			return null;
		}
		return currentMethod = new MethodCompiler(access,name,desc,compilationBuffer,namespace);
	}

	public void visitEnd() {
		if (special != null) {
			special.visitEnd(compilationBuffer);
		} else {
			compilationBuffer.append("});\n");
			JavaScriptLang.setSourceFile(className, debugSrc,compilationBuffer);
		}
		flush();
	}

	private void flush() {
		try {
			synchronized(resultWriter) {
				writeClass();
			}
		}
		catch(IOException ex){
			throw new CompilationError(ex);
		}
	}

	private void writeClass() throws IOException {
		resultWriter.write('\'');
		resultWriter.write(className);
		resultWriter.write("':function(){\n");
		writeImports();
		resultWriter.write(compilationBuffer.toString());
		resultWriter.write("},\n\n");
		resultWriter.flush();
	}

	private void writeImports() throws IOException {
		Map<String,String> imp = namespace.getImports();
		StringBuilder imports = new StringBuilder();
		for(String className : imp.keySet()){
			JavaScriptLang.importDeclaration(imp.get(className),
					Name.fromInternalName(className),imports);
		}
		resultWriter.write(imports.toString());
	}

	String getClassName() {
		return className;
	}

	String getMethodName() {
		if (currentMethod.isCompiled()) {
			return null;
		}
		return currentMethod.getName();
	}

	int getLineNumber() {
		return currentMethod.getLine();
	}

	String getFileName() {
		return debugSrc;
	}
}
