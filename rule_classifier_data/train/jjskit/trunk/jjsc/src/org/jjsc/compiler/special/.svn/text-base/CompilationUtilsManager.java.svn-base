package org.jjsc.compiler.special;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jjsc.compiler.namespace.JavaTypesNamespace;
import org.jjsc.utils.Log;
import org.jjsc.utils.Name;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * Provides useful utilities for compilers.
 *  
 * Also manages instances of {@link SpecialCompiler} (see) e.g. provides compilers 
 * for some special, unique classes. 
 * 
 * @author alex.bereznevatiy@gmail.com
 */
public final class CompilationUtilsManager {
private static Map<Integer, String> opcodes; 
	
	static {
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(NOP, "NOP");
		m.put(ACONST_NULL, "ACONST_NULL");
		m.put(ICONST_M1, "ICONST_M1");
		m.put(ICONST_0, "ICONST_0");
		m.put(ICONST_1, "ICONST_1");
		m.put(ICONST_2, "ICONST_2");
		m.put(ICONST_3, "ICONST_3");
		m.put(ICONST_4, "ICONST_4");
		m.put(ICONST_5, "ICONST_5");
		m.put(LCONST_0, "LCONST_0");
		m.put(LCONST_1, "LCONST_1");
		m.put(FCONST_0, "FCONST_0");
		m.put(FCONST_1, "FCONST_1");
		m.put(FCONST_2, "FCONST_2");
		m.put(DCONST_0, "DCONST_0");
		m.put(DCONST_1, "DCONST_1");
		m.put(BIPUSH, "BIPUSH");
		m.put(SIPUSH, "SIPUSH");
		m.put(LDC, "LDC");
		m.put(ILOAD, "ILOAD");
		m.put(LLOAD, "LLOAD");
		m.put(FLOAD, "FLOAD");
		m.put(DLOAD, "DLOAD");
		m.put(ALOAD, "ALOAD");
		m.put(IALOAD, "IALOAD");
		m.put(LALOAD, "LALOAD");
		m.put(FALOAD, "FALOAD");
		m.put(DALOAD, "DALOAD");
		m.put(AALOAD, "AALOAD");
		m.put(BALOAD, "BALOAD");
		m.put(CALOAD, "CALOAD");
		m.put(SALOAD, "SALOAD");
		m.put(ISTORE, "ISTORE");
		m.put(LSTORE, "LSTORE");
		m.put(FSTORE, "FSTORE");
		m.put(DSTORE, "DSTORE");
		m.put(ASTORE, "ASTORE");
		m.put(IASTORE, "IASTORE");
		m.put(LASTORE, "LASTORE");
		m.put(FASTORE, "FASTORE");
		m.put(DASTORE, "DASTORE");
		m.put(AASTORE, "AASTORE");
		m.put(BASTORE, "BASTORE");
		m.put(CASTORE, "CASTORE");
		m.put(SASTORE, "SASTORE");
		m.put(POP, "POP");
		m.put(POP2, "POP2");
		m.put(DUP, "DUP");
		m.put(DUP_X1, "DUP_X1");
		m.put(DUP_X2, "DUP_X2");
		m.put(DUP2, "DUP2");
		m.put(DUP2_X1, "DUP2_X1");
		m.put(DUP2_X2, "DUP2_X2");
		m.put(SWAP, "SWAP");
		m.put(IADD, "IADD");
		m.put(LADD, "LADD");
		m.put(FADD, "FADD");
		m.put(DADD, "DADD");
		m.put(ISUB, "ISUB");
		m.put(LSUB, "LSUB");
		m.put(FSUB, "FSUB");
		m.put(DSUB, "DSUB");
		m.put(IMUL, "IMUL");
		m.put(LMUL, "LMUL");
		m.put(FMUL, "FMUL");
		m.put(DMUL, "DMUL");
		m.put(IDIV, "IDIV");
		m.put(LDIV, "LDIV");
		m.put(FDIV, "FDIV");
		m.put(DDIV, "DDIV");
		m.put(IREM, "IREM");
		m.put(LREM, "LREM");
		m.put(FREM, "FREM");
		m.put(DREM, "DREM");
		m.put(INEG, "INEG");
		m.put(LNEG, "LNEG");
		m.put(FNEG, "FNEG");
		m.put(DNEG, "DNEG");
		m.put(ISHL, "ISHL");
		m.put(LSHL, "LSHL");
		m.put(ISHR, "ISHR");
		m.put(LSHR, "LSHR");
		m.put(IUSHR, "IUSHR");
		m.put(LUSHR, "LUSHR");
		m.put(IAND, "IAND");
		m.put(LAND, "LAND");
		m.put(IOR, "IOR");
		m.put(LOR, "LOR");
		m.put(IXOR, "IXOR");
		m.put(LXOR, "LXOR");
		m.put(IINC, "IINC");
		m.put(I2L, "I2L");
		m.put(I2F, "I2F");
		m.put(I2D, "I2D");
		m.put(L2I, "L2I");
		m.put(L2F, "L2F");
		m.put(L2D, "L2D");
		m.put(F2I, "F2I");
		m.put(F2L, "F2L");
		m.put(F2D, "F2D");
		m.put(D2I, "D2I");
		m.put(D2L, "D2L");
		m.put(D2F, "D2F");
		m.put(I2B, "I2B");
		m.put(I2C, "I2C");
		m.put(I2S, "I2S");
		m.put(LCMP, "LCMP");
		m.put(FCMPL, "FCMPL");
		m.put(FCMPG, "FCMPG");
		m.put(DCMPL, "DCMPL");
		m.put(DCMPG, "DCMPG");
		m.put(IFEQ, "IFEQ");
		m.put(IFNE, "IFNE");
		m.put(IFLT, "IFLT");
		m.put(IFGE, "IFGE");
		m.put(IFGT, "IFGT");
		m.put(IFLE, "IFLE");
		m.put(IF_ICMPEQ, "IF_ICMPEQ");
		m.put(IF_ICMPNE, "IF_ICMPNE");
		m.put(IF_ICMPLT, "IF_ICMPLT");
		m.put(IF_ICMPGE, "IF_ICMPGE");
		m.put(IF_ICMPGT, "IF_ICMPGT");
		m.put(IF_ICMPLE, "IF_ICMPLE");
		m.put(IF_ACMPEQ, "IF_ACMPEQ");
		m.put(IF_ACMPNE, "IF_ACMPNE");
		m.put(GOTO, "GOTO");
		m.put(JSR, "JSR");
		m.put(RET, "RET");
		m.put(TABLESWITCH, "TABLESWITCH");
		m.put(LOOKUPSWITCH, "LOOKUPSWITCH");
		m.put(IRETURN, "IRETURN");
		m.put(LRETURN, "LRETURN");
		m.put(FRETURN, "FRETURN");
		m.put(DRETURN, "DRETURN");
		m.put(ARETURN, "ARETURN");
		m.put(RETURN, "RETURN");
		m.put(GETSTATIC, "GETSTATIC");
		m.put(PUTSTATIC, "PUTSTATIC");
		m.put(GETFIELD, "GETFIELD");
		m.put(PUTFIELD, "PUTFIELD");
		m.put(INVOKEVIRTUAL, "INVOKEVIRTUAL");
		m.put(INVOKESPECIAL, "INVOKESPECIAL");
		m.put(INVOKESTATIC, "INVOKESTATIC");
		m.put(INVOKEINTERFACE, "INVOKEINTERFACE");
		m.put(INVOKEDYNAMIC, "INVOKEDYNAMIC");
		m.put(NEW, "NEW");
		m.put(NEWARRAY, "NEWARRAY");
		m.put(ANEWARRAY, "ANEWARRAY");
		m.put(ARRAYLENGTH, "ARRAYLENGTH");
		m.put(ATHROW, "ATHROW");
		m.put(CHECKCAST, "CHECKCAST");
		m.put(INSTANCEOF, "INSTANCEOF");
		m.put(MONITORENTER, "MONITORENTER");
		m.put(MONITOREXIT, "MONITOREXIT");
		m.put(MULTIANEWARRAY, "MULTIANEWARRAY");
		m.put(IFNULL, "IFNULL");
		m.put(IFNONNULL, "IFNONNULL");
		
		opcodes = Collections.unmodifiableMap(m);
	}
	
	// hidden constructor - this class is not for instantiation
	private CompilationUtilsManager(){}
	
	/**
	 * Get name of the static constant on the class with value specified.
	 * If there are multiple constants exists the result is not determined.
	 * The performance of this operation is O(N).
	 * @param cls to process
	 * @param value to check
	 * @return name of the constant or empty string if not found.
	 */
	public static String getOpcodeName(int opcode) {
		return opcodes.get(opcode);
	}
	/**
	 * @param className to find special compiler for.
	 * @return special compiler for class with name specified or <code>null</code> if there aren't any.
	 */
	public static SpecialCompiler getSpecialCompiler(String className) {
		if (!Name.isJavaName(Name.getSimpleName(className))) {
			return new NotCompile();
		}
		return null;
	}
	/**
	 * Do not do any compilation work. This is necessary to skip some 
	 * documentation-related files (e.g. package-info.class).
	 * 
	 * @author alex.bereznevatiy@gmail.com
	 */
	private static class NotCompile implements SpecialCompiler {

		public void visit(String name, int access, String superName,
				JavaTypesNamespace namespace, StringBuilder output) {
			Log.warn("Skipping class "+name+" because its not a valid java indentifier");
		}

		public MethodVisitor visitMethod(String name, int access, String desc,
				StringBuilder output) {
			return null;
		}

		public void visitEnd(StringBuilder output) {
		}
	}
}
